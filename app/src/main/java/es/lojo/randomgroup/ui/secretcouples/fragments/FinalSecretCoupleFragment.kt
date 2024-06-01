package es.lojo.randomgroup.ui.secretcouples.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.extensions.hide
import es.lojo.randomgroup.commons.extensions.show
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.databinding.FragmentFinalSecretCoupleBinding
import es.lojo.randomgroup.ui.secretcouples.states.FinalSecretCouplesStates
import es.lojo.randomgroup.ui.secretcouples.viewmodels.FinalSecretCoupleViewModel

private const val CLASS_NAME = "FinalSecretCoupleFragment"
class FinalSecretCoupleFragment : Fragment() {

    private var binding: FragmentFinalSecretCoupleBinding? = null
    private val navController: NavController by lazy { findNavController() }
    val viewModel: FinalSecretCoupleViewModel by viewModels()

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
        binding = FragmentFinalSecretCoupleBinding.bind(
            inflater.inflate(
                R.layout.fragment_final_secret_couple,
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

    private fun initViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FinalSecretCouplesStates.Loading -> {
                    binding?.progressbar?.show()
                }

                is FinalSecretCouplesStates.Render -> {
                    // TODO
                   // render()
                }
            }
        }
    }

    private fun initViews() {

    }
}