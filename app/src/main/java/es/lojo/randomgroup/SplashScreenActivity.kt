package es.lojo.randomgroup

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.lojo.randomgroup.commons.InfolojoLogger
import es.lojo.randomgroup.ui.activities.MainActivity
import kotlinx.coroutines.*

private const val CLASS_NAME = "SplashScreenActivity"

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        InfolojoLogger.log(CLASS_NAME, "init app")
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        MainScope().launch {
            delay(1000)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            this@SplashScreenActivity.finish()
        }
    }
}
