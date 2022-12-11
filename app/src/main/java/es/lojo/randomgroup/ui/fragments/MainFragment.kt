package es.lojo.randomgroup.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.CustomLog
import es.lojo.randomgroup.databinding.FragmentMainBinding

private const val CLASS_NAME = "MainFragment"

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val navController: NavController by lazy { findNavController() }

    // Note: custom callback onBackPressed
    private val callBackOnBackPressed: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext()).setTitle(R.string.close_game)
                    .setPositiveButton(R.string.yes) { _, _ -> activity?.finish() }
                    .setNegativeButton("no please") { _, _ -> /* no-op */ }
                    .create().show()
                this.remove()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CustomLog.log(CLASS_NAME, "init", prefix = "fragment")
        binding = FragmentMainBinding.bind(
            inflater.inflate(
                R.layout.fragment_main,
                container,
                false
            )
        )
        initViews()
        return binding?.root
    }

    private fun initViews() {
        // Note: add onBackPressed callback
        requireActivity().onBackPressedDispatcher.addCallback(callBackOnBackPressed)
        binding?.playButton?.setOnClickListener {
            // Note: remove onBackPressed callback
            callBackOnBackPressed.remove()
            navController.navigate(
                R.id.action_fragmentMain_to_fragmentConfigurePlayers,
                null,
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
