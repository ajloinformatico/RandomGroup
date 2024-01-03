package es.lojo.randomgroup.ui.mainoptions.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.objects.InfolojoLogger
import es.lojo.randomgroup.commons.objects.InfolojoMessageMaker
import es.lojo.randomgroup.databinding.FragmentMainOptionsBinding

private const val CLASS_NAME = "MainOptionsFragment"

class MainOptionsFragment : Fragment() {

    private var binding: FragmentMainOptionsBinding? = null
    private val navController: NavController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InfolojoLogger.log(CLASS_NAME, "init", prefix = "fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainOptionsBinding.bind(
            inflater.inflate(
                R.layout.fragment_main_options,
                container,
                false
            )
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun showButtonInfo(text: String) {
        // TODO Animation to show background with alpha 1.2 and text with info over background
        // If user touch any it will be gone with another animation
        binding?.root?.let {
            InfolojoMessageMaker.showMessage(it, text)
        }
    }

    private fun initView() {
        binding?.apply {
            playersAndGroupsButton.setOnClickListener {
                // TODO Navigate to this screen
                InfolojoMessageMaker.showMessage(
                    root,
                    "navigate to players and groups screen feature"
                )
            }
            playersAndGroupsInfoButton.setOnClickListener {
                showButtonInfo(it.contentDescription.toString())
            }
            getOneRandomButton.setOnClickListener {
                // TODO Navigate to this screen
                InfolojoMessageMaker.showMessage(root, "navigate to get one random")
            }
            getOneRandomInfoButton.setOnClickListener {
                showButtonInfo(it.contentDescription.toString())
            }
            secretCouples.setOnClickListener {
                // TODO NAVIGATE TO this screen
                InfolojoMessageMaker.showMessage(root, "navigate to secret couples")
            }
            secretCouplesInfoButton.setOnClickListener {
                showButtonInfo(it.contentDescription.toString())
            }
        }
    }
}
