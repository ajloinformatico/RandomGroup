package es.lojo.randomgroup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.*
import es.lojo.randomgroup.databinding.FragmentConfigurePlayersNameBinding
import es.lojo.randomgroup.ui.adapters.configureplayersname.ConfigurePlayersNameAdapter
import es.lojo.randomgroup.ui.states.ConfigurePlayersNameErrors
import es.lojo.randomgroup.ui.states.ConfigurePlayersNameGridViewState
import es.lojo.randomgroup.ui.states.PlayerUpdate
import es.lojo.randomgroup.ui.viewmodel.ConfigurePlayersNameViewModel


private const val CLASS_NAME = "ConfigurePlayersNameFragment"

class ConfigurePlayersNameFragment : Fragment() {

    private var binding: FragmentConfigurePlayersNameBinding? = null
    private val adapter: ConfigurePlayersNameAdapter =
        ConfigurePlayersNameAdapter(::manageAdapterEvents)
    private val viewModel: ConfigurePlayersNameViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }
    private val safeArgs: ConfigurePlayersNameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CustomLog.log(CLASS_NAME, "init", "fragment")
        binding = FragmentConfigurePlayersNameBinding.bind(
            inflater.inflate(
                R.layout.fragment_configure_players_name,
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

    private fun initViews() {

        binding?.recycler?.apply {
            adapter = this@ConfigurePlayersNameFragment.adapter
            viewModel.playersConfig.observe(viewLifecycleOwner) { playersConfiguration ->
                // Note: Update competition name
                playersConfiguration.competitionName.takeIf { it.isNotEmpty() }?.let {
                    binding?.competitionName?.text = it
                }

                // Note: Update recyclerView
                (adapter as? ConfigurePlayersNameAdapter)?.let {
                    it.submitList(playersConfiguration.players)
                    viewModel.setState(ConfigurePlayersNameGridViewState.Render)
                } ?: viewModel.setState(
                    ConfigurePlayersNameGridViewState.Error(
                        ConfigurePlayersNameErrors.UNKNOWN
                    )
                )
            }
        } ?: viewModel.setState(
            ConfigurePlayersNameGridViewState.Error(
                ConfigurePlayersNameErrors.UNKNOWN
            )
        )

        // Get all text and navigate to the next screen
        binding?.playButton?.setOnClickListener {
            viewModel.continueClick()
        }
        // Hide keyboard
        hideKeyboard()
    }

    private fun initViewModel() {
        safeArgs.players?.let {
            viewModel.setPlayers(it)
        }
        viewModel.firstConfigOfPlayers()
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ConfigurePlayersNameGridViewState.Loading -> {
                    binding?.progressbar?.show()
                }
                is ConfigurePlayersNameGridViewState.Render -> {
                    binding?.progressbar?.hide()
                }
                is ConfigurePlayersNameGridViewState.Error -> {
                    binding?.root?.let { rootView ->
                        hideKeyboard()
                        SnackBarMaker.showError(
                            rootView,
                            state.error.message
                        )
                    }
                }
                is ConfigurePlayersNameGridViewState.Finish -> {
                    // Load players select
                    val bundle = Bundle().apply {
                        this.putSerializable(
                            PLAYERS_BUNDLE_NAME,
                            state.finalPlayersConfig
                        )
                    }
                    navController.navigate(
                        R.id.action_fragmentConfigurePlayersName_to_fragmentFinalPlayersConfig,
                        args = bundle
                    )
                    viewModel.setState(ConfigurePlayersNameGridViewState.Unknown)
                }
                is ConfigurePlayersNameGridViewState.Unknown -> { /* no-op */
                }
            }
        }
    }

    /**
     * Hide keyboard when user click in any way
     */
    private fun hideKeyboard() {
        binding?.let { bindView ->
            (requireActivity() as? AppCompatActivity)?.let { appCompatActivity ->
                bindView.root.setOnClickListener {
                    (0 until adapter.itemCount).forEachIndexed { i, _ ->
                        binding?.recycler?.getChildAt(i)?.apply {
                            appCompatActivity.hideVirtualKeyBoard(this.findViewById(R.id.playerName))
                        }
                    }
                }
            }
        }
    }

    private fun manageAdapterEvents(state: PlayerUpdate) {
        viewModel.updatePlayersConfig(state.name, state.position)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
