package es.lojo.randomgroup.ui.activities

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.databinding.ActivityWebViewBinding

private const val CLASS_NAME = "WebViewActivity"

class WebViewActivity: AppCompatActivity() {

    private var binding: ActivityWebViewBinding? = null
    private val webViewName: String by lazy {
        intent.getStringExtra(WEB_VIEW_NAME).orEmpty()
    }
    private val webViewUrl: String by lazy {
        intent.getStringExtra(WEB_VIEW_URL).orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = webViewName,
            suffix = LoggerTypes.ACTIVITY
        )
        // Prepare status bar to update in other fragments
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setTheme(es.lojo.randomgroup.R.style.NoActionBar)
        setContentView(binding?.root)
        configureNavigationBottomBarView()
        loadWebView()
    }

    /** Update system navigate bottom. */
    private fun configureNavigationBottomBarView() {
        window.navigationBarColor = ContextCompat.getColor(
            this,
            if (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
                es.lojo.randomgroup.R.color.white
            } else {
                es.lojo.randomgroup.R.color.black
            }
        )
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView() {
        binding?.webView?.apply {
            setWebViewClient(WebViewClient())
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            loadUrl(webViewUrl)
        }
    }

    companion object {
        const val WEB_VIEW_NAME = "WEB_VIEW_NAME"
        const val WEB_VIEW_URL = "WEB_VIEW_URL"
    }
}
