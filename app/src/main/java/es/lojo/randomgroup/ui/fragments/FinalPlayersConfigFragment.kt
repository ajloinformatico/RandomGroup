package es.lojo.randomgroup.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.SnackBarMaker
import es.lojo.randomgroup.commons.hide
import es.lojo.randomgroup.commons.makeOneShot
import es.lojo.randomgroup.commons.show
import es.lojo.randomgroup.databinding.FragmentFinalPlayersConfigBinding
import es.lojo.randomgroup.ui.adapters.finalplayersconfig.FinalPlayersConfigAdapter
import es.lojo.randomgroup.ui.states.FinalPlayerConfigState
import es.lojo.randomgroup.ui.states.FinalPlayersConfigViewState
import es.lojo.randomgroup.ui.viewmodel.FinalPlayersConfigViewModel

class FinalPlayersConfigFragment : Fragment() {

    private val viewModel: FinalPlayersConfigViewModel by viewModels()
    private val adapter: FinalPlayersConfigAdapter = FinalPlayersConfigAdapter(::manageAdapterEvent)
    private var binding: FragmentFinalPlayersConfigBinding? = null
    private val navController: NavController by lazy { findNavController() }
    private val safeArgs: FinalPlayersConfigFragmentArgs by navArgs()

    // Note: custom callback onBackPressed
    private val callBackOnBackPressed: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext()).setTitle(R.string.close_game)
                    .setPositiveButton(R.string.yes) { _, _ -> activity?.finish() }
                    .setNegativeButton(R.string.re_run_game_plase) { _, _ ->
                        navController.navigate(
                            R.id.action_fragmentFinalPlayersConfig_to_fragmentMain,
                        )
                    }
                    .create().show()
                this.remove()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinalPlayersConfigBinding.bind(
            inflater.inflate(
                R.layout.fragment_final_players_config,
                container,
                false
            )
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        restart()
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FinalPlayersConfigViewState.Loading -> {
                    binding?.progressbar?.show()
                }
                is FinalPlayersConfigViewState.Render -> {
                    binding?.progressbar?.hide()
                }
                is FinalPlayersConfigViewState.Error -> {
                    navController.popBackStack()
                }
                is FinalPlayersConfigViewState.ShouldShowContinueButton -> {
                    binding?.playButton?.isVisible = state.show
                }
                is FinalPlayersConfigViewState.Finish -> {
                    restart()
                    val bundle = Bundle().apply {
                        this.putSerializable(
                            PLAYERS_BUNDLE_NAME,
                            state.finalPlayersConfig
                        )
                    }
                    navController.navigate(
                        R.id.action_fragmentFinalPlayersConfig_to_fragmentFinalPlayersConfig,
                        args = bundle
                    )
                    viewModel.setState(FinalPlayersConfigViewState.Unknown)
                }
                is FinalPlayersConfigViewState.CustomError -> {
                    SnackBarMaker.showError(binding?.root, state.message)
                }
                is FinalPlayersConfigViewState.Unknown -> {
                    // no-op
                }
            }
        }
    }

    /**
     * Restart viewModel data
     */
    private fun restart() {
        safeArgs.players?.let {
            viewModel.setFinalConfigAtTheFirstTime(it)
        }
    }

    private fun initViews() {
        binding?.recycler?.apply {
            adapter = this@FinalPlayersConfigFragment.adapter
            viewModel.finalConfig.observe(viewLifecycleOwner) { finalConfig ->
                // Note: update competition name
                finalConfig.competitionName.takeIf { it.isNotEmpty() }?.let {
                    binding?.competitionName?.text = it
                }
                // Note: Update recycler
                (adapter as? FinalPlayersConfigAdapter)?.let {
                    it.submitList(finalConfig.groups)
                    viewModel.setState(FinalPlayersConfigViewState.Render)
                } ?: viewModel.setState(FinalPlayersConfigViewState.Error)
                if (finalConfig.groups.size == 1) {
                    showWinner()
                    requireActivity().onBackPressedDispatcher.addCallback(callBackOnBackPressed)
                }
            }
        } ?: viewModel.setState(FinalPlayersConfigViewState.Error)

        binding?.playButton?.setOnClickListener {
            viewModel.continueClicked()
        }
    }

    private fun showWinner() {
        with(Gravity.CENTER) {
            binding?.competitionName?.gravity = this
            binding?.groupWinner?.gravity = this
        }
        val previousTitle = binding?.competitionName?.text.toString()
        binding?.competitionName?.text = resources.getString(R.string.finish_upper)
        binding?.groupWinner?.visibility = View.VISIBLE
        binding?.groupWinner?.text = resources.getString(R.string.winner_message, previousTitle)
        binding?.konfettiView?.makeOneShot()
    }

    private fun manageAdapterEvent(event: FinalPlayerConfigState) {
        viewModel.updateFinalConfig(event.group)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * Implement custom onBackPressed
     */
    private fun applyCustomOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            navController.navigate( R.id.action_fragmentFinalPlayersConfig_to_fragmentMain,)
            this.remove()
        }
    }
}
