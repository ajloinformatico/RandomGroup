package es.lojo.randomgroup.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.*
import es.lojo.randomgroup.databinding.FragmentConfigurePlayersBinding
import es.lojo.randomgroup.ui.states.ConfigurePlayersViewState
import es.lojo.randomgroup.ui.viewmodel.ConfigurePlayersViewModel


private const val CLASS_NAME = "ConfigurePlayers"
const val PLAYERS_BUNDLE_NAME = "players"

class ConfigurePlayersFragment : Fragment() {

    private val viewModel: ConfigurePlayersViewModel by viewModels()
    private var binding: FragmentConfigurePlayersBinding? = null
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CustomLog.log(CLASS_NAME, "init", prefix = "fragment")
        binding = FragmentConfigurePlayersBinding.bind(
            inflater.inflate(
                R.layout.fragment_configure_players, container, false
            )
        )
        initViews()
        initViewModel()
        return binding?.root
    }

    private fun initViews() {
        hideKeyboard()
        // Competition name
        binding?.competitionName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setCompetitionName(
                    binding?.competitionName?.text.toString()
                )
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        // Number of groups
        binding?.numberOfGroupsInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setNumberOfGroups(
                    binding?.numberOfGroupsInput?.text.toString().toIntCustom()
                )
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        // Number of players
        binding?.numberOfPlayersInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setNumberOfPlayers(
                    binding?.numberOfPlayersInput?.text.toString().toIntCustom()
                )
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        viewModel.setState(ConfigurePlayersViewState.Render)
        binding?.continueBottom?.setOnClickListener {
            viewModel.checkToContinue()
        }
    }

    /**
     * Hide keyboard when user click in any way
     */
    private fun hideKeyboard() {
        binding?.let { bindView ->
            (requireActivity() as? AppCompatActivity)?.let { appCompatActivity ->
                bindView.root.setOnClickListener {
                    hideVirtualKeyBoard(
                        appCompatActivity,
                        bindView.competitionName
                    )
                    hideVirtualKeyBoard(
                        appCompatActivity,
                        bindView.competitionName
                    )
                    hideVirtualKeyBoard(
                        appCompatActivity,
                        bindView.competitionName
                    )
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel.setCompetitionName(
            binding?.competitionName?.text.toString()
        )

        viewModel.setNumberOfGroups(
            binding?.numberOfGroupsInput?.text.toString().toIntCustom()
        )

        viewModel.setNumberOfPlayers(
            binding?.numberOfPlayersInput?.text.toString().toIntCustom()
        )

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ConfigurePlayersViewState.Loading -> {
                    binding?.progressBar?.show()
                }

                is ConfigurePlayersViewState.Render -> {
                    binding?.progressBar?.hide()
                }

                is ConfigurePlayersViewState.Error -> {
                    binding?.root?.let { rootView ->
                        SnackBarMaker.showError(
                            rootView,
                            state.error.message
                        )
                    }
                }

                is ConfigurePlayersViewState.OpenButtonSheetPlayersName -> {
                    // Load players select
                    val bundle = Bundle().apply {
                        this.putSerializable(
                            PLAYERS_BUNDLE_NAME,
                            state.configuration
                        )
                    }
                    navController.navigate(
                        R.id.action_fragmentConfigurePlayers_to_fragmentConfigurePlayersName,
                        args = bundle
                    )
                    viewModel.setState(ConfigurePlayersViewState.Unknown)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

