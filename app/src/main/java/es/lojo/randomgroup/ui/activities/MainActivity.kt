package es.lojo.randomgroup.ui.activities

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ActivityNavigator
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.InfolojoLogger
import es.lojo.randomgroup.commons.manageWebViewInfolojo
import es.lojo.randomgroup.databinding.ActivityMainBinding

const val CLASS_NAME = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InfolojoLogger.log(CLASS_NAME, resources.getString(R.string.init_app))
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

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}
