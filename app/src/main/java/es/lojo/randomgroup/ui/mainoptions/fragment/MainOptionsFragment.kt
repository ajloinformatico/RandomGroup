package es.lojo.randomgroup.ui.mainoptions.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.objects.InfolojoLogger
import es.lojo.randomgroup.commons.objects.InfolojoMessageMaker
import es.lojo.randomgroup.commons.objects.InfolojoThemeHelper
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

    /** Show [text] info about button in screen with a animation. */
    private fun showButtonInfo(text: String) {
        // TODO Animation to show info from button to top
        binding?.infoDescription?.apply {
            this.text = text
            isVisible = true
        }

        InfolojoThemeHelper.updateStatusBarColor(activity, R.color.back_ground_overly)
    }

    /** Hidde info about button in screen with a animation. */
    private fun hideButtonInfo() {
        // TODO Animation to hide info from top to button
        binding?.infoDescription?.apply {
            isVisible = false
            text = ""
        }
        InfolojoThemeHelper.resetBackGroundColor(activity)
    }

    private fun initView() {
        prepareOverlyInfo()
        preparePlayersAndGroupOption()
        prepareGetOneRandomButton()
        prepareSecretCouplesButton()
    }

    private fun prepareOverlyInfo() {
        binding?.infoDescription?.setOnClickListener {
            hideButtonInfo()
        }
    }

    private fun preparePlayersAndGroupOption() {
        binding?.apply {
            playersAndGroupsButton.setOnClickListener {
                navController.navigate(
                    R.id.action_mainOptionsFragment_to_fragmentConfigurePlayers,
                    null
                )
            }
            playersAndGroupsInfoButton.setOnClickListener {
                showButtonInfo(playersAndGroupsButton.contentDescription.toString())
            }
        }
    }

    private fun prepareGetOneRandomButton() {
        binding?.apply {
            getOneRandomButton.setOnClickListener {
                // TODO Navigate to this screen
                InfolojoMessageMaker.showMessage(root, "navigate to get one random")
            }
            getOneRandomInfoButton.setOnClickListener {
                showButtonInfo(getOneRandomButton.contentDescription.toString())
            }
        }
    }

    private fun prepareSecretCouplesButton() {
        binding?.apply {
            secretCouples.setOnClickListener {
                // TODO NAVIGATE TO this screen
                InfolojoMessageMaker.showMessage(root, "navigate to secret couples")
            }
            secretCouplesInfoButton.setOnClickListener {
                showButtonInfo(secretCouples.contentDescription.toString())
            }
        }
    }
}
