package es.lojo.randomgroup.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        manageWebViewBanner()
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
