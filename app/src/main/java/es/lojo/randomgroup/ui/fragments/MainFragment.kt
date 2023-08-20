package es.lojo.randomgroup.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding?.playButton?.setOnClickListener {
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
