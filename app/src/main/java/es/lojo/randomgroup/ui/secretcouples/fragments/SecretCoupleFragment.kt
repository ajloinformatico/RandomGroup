package es.lojo.randomgroup.ui.secretcouples.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import es.lojo.randomgroup.databinding.FragmentSecretCoupleBinding
import es.lojo.randomgroup.ui.secretcouples.adapters.SecretCoupleFragmentAdapter
import es.lojo.randomgroup.ui.secretcouples.states.SecretCoupleActions
import es.lojo.randomgroup.ui.secretcouples.states.SecretCoupleStates
import es.lojo.randomgroup.ui.secretcouples.viewmodels.SecretCoupleViewModel

private const val CLASS_NAME = "SecretCoupleFragment"

class SecretCoupleFragment : Fragment() {

    // region attr
    private val viewModel: SecretCoupleViewModel by viewModels()
    private var binding: FragmentSecretCoupleBinding? = null
    private val adapter = SecretCoupleFragmentAdapter(::manageEvent)
    private val navController: NavController by lazy { findNavController() }

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
        binding = FragmentSecretCoupleBinding.bind(
            inflater.inflate(
                R.layout.fragment_secret_couple,
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
                is SecretCoupleStates.Loading -> binding?.progressbar?.show()
                is SecretCoupleStates.Render -> {
                    render()
                }
                is SecretCoupleStates.Continue -> {
                    binding?.root?.let {
                        InfolojoMessageMaker.showPositiveMessage(it, "result = ${state.result}")
                    }
                }
                is SecretCoupleStates.Error -> {
                    binding?.root?.let {
                        InfolojoMessageMaker.showError(it, state.error.message)
                    }
                }
            }
        }
    }

    private fun initViews() {
        prepareAddButton()
        prepareContinueButton()
    }

    private fun prepareAddButton() {
        binding?.addButton?.setOnClickListener { viewModel.addItem() }
    }

    private fun prepareContinueButton() {
        binding?.playButton?.setOnClickListener {
            hideKeyBoard()
            viewModel.getCouples()
        }
    }

    private fun render() {
        binding?.apply {
            recycler.adapter = this@SecretCoupleFragment.adapter
            viewModel.items.observe(viewLifecycleOwner) { adapter.submitList(it) }
            progressbar.hide()
        }
    }

    private fun hideKeyBoard() {
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

    private fun manageEvent(action: SecretCoupleActions) {
       when (action) {
           is SecretCoupleActions.Update -> viewModel.updateItem(action.item)
           is SecretCoupleActions.Remove -> {
               hideKeyBoard()
               viewModel.removeItem(action.item)
           }
           is SecretCoupleActions.HideKeyBoard -> {
               hideKeyBoard()
           }
       }
    }
    // endregion private
}