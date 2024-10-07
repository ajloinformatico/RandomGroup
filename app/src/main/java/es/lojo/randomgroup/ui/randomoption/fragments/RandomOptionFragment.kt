package es.lojo.randomgroup.ui.randomoption.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.extensions.hide
import es.lojo.randomgroup.commons.extensions.hideVirtualKeyBoard
import es.lojo.randomgroup.commons.extensions.show
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.commons.objects.InfolojoMessageMaker
import es.lojo.randomgroup.databinding.FragmentRandomOptionBinding
import es.lojo.randomgroup.ui.randomoption.adapters.RandomOptionsFragmentAdapter
import es.lojo.randomgroup.ui.randomoption.states.RandomOptionViewState
import es.lojo.randomgroup.ui.randomoption.states.RandomOptionsActions
import es.lojo.randomgroup.ui.randomoption.viewmodels.RandomOptionViewModel
import es.lojo.randomgroup.ui.randomoption.vo.RandomOptionVO

private const val CLASS_NAME = "RandomOptionFragment"

class RandomOptionFragment : Fragment() {

    // region attr
    private val viewModel: RandomOptionViewModel by viewModels()
    private var binding: FragmentRandomOptionBinding? = null
    private val adapter = RandomOptionsFragmentAdapter(::manageEvent)
    private val navController: NavController by lazy { findNavController() }
    private val isWinnerVisible: Boolean
        get() = binding?.run {
            resultContainer.isVisible || overly.isVisible
        } ?: false
    // endregion attr

    // region override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.FRAGMENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomOptionBinding.bind(
            inflater.inflate(
                R.layout.fragment_random_option,
                container,
                false
            )
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
    // endregion override

    // region private
    private fun initViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RandomOptionViewState.Loading -> binding?.progressbar?.show()
                is RandomOptionViewState.Render -> {
                    launchRecycler()
                    binding?.progressbar?.hide()

                }

                is RandomOptionViewState.Continue -> {
                    showWinner(state.winner)
                    activity?.onBackPressedDispatcher?.addCallback(callBackOnBackPressed)
                }

                is RandomOptionViewState.Error -> {
                    binding?.apply {
                        progressbar.hide()
                        InfolojoMessageMaker.showError(
                            view = root,
                            text = state.error.message
                        )
                    }
                }
            }
        }
    }

    private fun initViews() {
        hideWinner()
        prepareOverly()
        prepareHideKeyBoard()
        prepareAddButton()
        prepareContinueButton()
    }

    private fun prepareHideKeyBoard() {
        binding?.apply {
            root.setOnClickListener { hideKeyboard() }
            recyclerViewContainer.setOnClickListener { hideKeyboard() }
        }
    }

    private fun prepareOverly() {
        binding?.overly?.setOnClickListener {
            if (isWinnerVisible) {
                hideWinner()
            }
        }
    }

    /**
     * Hide keyboard when user click in any way
     */
    private fun hideKeyboard() {
        (activity as? AppCompatActivity)?.let { appCompatActivity ->
            (0 until adapter.itemCount).forEachIndexed { i, _ ->
                binding?.recycler?.getChildAt(i)?.let { currentView ->
                    appCompatActivity.hideVirtualKeyBoard(
                        currentView.findViewById(R.id.text)
                    )
                }
            }
        }
    }

    private fun prepareAddButton() {
        binding?.addButton?.setOnClickListener {
            viewModel.addNewOption()
        }
    }

    private fun prepareContinueButton() {
        binding?.playButton?.setOnClickListener {
            hideKeyboard()
            viewModel.getWinner()
        }
    }

    private fun launchRecycler() {
        binding?.recycler?.adapter = this@RandomOptionFragment.adapter
        viewModel.options.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    /** Show [winner] from random choice with an animation from bottom to top. */
    private fun showWinner(winner: RandomOptionVO) {
        binding?.apply {
            winnerText.text = winner.text
            resultContainer.show()
            overly.show()
        }
    }

    /** Hide winner with an animation from to top to bottom. And init onClick event
     * over root to hide result container in the case that it was showed.
     */
    private fun hideWinner() {
        binding?.apply {
            winnerText.text = ""
            resultContainer.hide()
            overly.hide()
        }
    }

    private fun manageEvent(action: RandomOptionsActions) {
        when (action) {
            is RandomOptionsActions.Update -> viewModel.updateOption(action.option)
            is RandomOptionsActions.Remove -> {
                hideKeyboard()
                viewModel.removeOption(action.option)
            }
            is RandomOptionsActions.HideKeyBoard -> {
                hideKeyboard()
            }
        }
    }

    // Note: custom callback onBackPressed
    private val callBackOnBackPressed: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isWinnerVisible) hideWinner() else {
                    navController.navigate(R.id.action_randomOptionFragment_to_mainOptionsFragment)
                }
                this.remove()
            }
        }
    // endregion private
}
