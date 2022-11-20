package es.lojo.randomgroup.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.hide
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
        safeArgs.players?.let {
            viewModel.setFinalConfig(it)
        }
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
                is FinalPlayersConfigViewState.Unknown -> {
                    // no-op
                }
            }
        }
    }

    private fun initViews() {
        binding?.recycler?.apply {
            adapter = this@FinalPlayersConfigFragment.adapter
            viewModel.finalConfig.observe(viewLifecycleOwner) { finalConfig ->
                (adapter as? FinalPlayersConfigAdapter)?.let {
                    it.submitList(finalConfig.groups)
                    viewModel.setState(FinalPlayersConfigViewState.Render)
                } ?: viewModel.setState(FinalPlayersConfigViewState.Error)
            }
        } ?: viewModel.setState(FinalPlayersConfigViewState.Error)
    }

    private fun manageAdapterEvent(event: FinalPlayerConfigState) {
        viewModel.updateFinalConfig(event.group)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
