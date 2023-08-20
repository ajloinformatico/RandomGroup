package es.lojo.randomgroup.ui.activities

import android.app.AlertDialog
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ActivityNavigator
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.CustomLog
import es.lojo.randomgroup.commons.manageWebViewInfolojo
import es.lojo.randomgroup.databinding.ActivityMainBinding

const val CLASS_NAME = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomLog.log(CLASS_NAME, "Init main app")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.NoActionBar)
        setContentView(binding?.root)
        configureNavigationBottomBarView()
        manageWebViewBanner()
    }

    private fun configureNavigationBottomBarView() {
        window.navigationBarColor = ContextCompat.getColor(
            this,
            if (resources.configuration.uiMode.and(UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES) {
                R.color.white
            } else {
                R.color.black
            }
        )
    }

    private fun manageWebViewBanner() {
        binding?.infolojoIncludeBanner?.root?.setOnClickListener {
            this.manageWebViewInfolojo()
        }
    }

    // TODO USE NAC GRAPH destionations to set actions in onBacks
    private fun customOnBackPressed() {
        onBackPressedDispatcher.addCallback {
            AlertDialog.Builder(this@MainActivity).setTitle(R.string.close_game)
                .setPositiveButton(R.string.yes) { _, _ -> finish() }
                .setNegativeButton(R.string.no) { _, _ -> }
                .create().show()

        }
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
