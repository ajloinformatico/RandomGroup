package es.lojo.randomgroup.commons.extensions

import android.app.Activity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.objects.InfolojoThemeHelper
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit

private const val DEFAULT_VIEW_DURATION = 500L
private const val CLASS_NAME = "ViewExtensions"

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun KonfettiView.makeOneShot() =
    this.start(
        Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3)
        )
    )

fun View.showAnimationFromBottomToTop(
    activity: Activity? = null,
    duration: Long = DEFAULT_VIEW_DURATION,
    endActionEvent: () -> Unit = {}
) {
    isVisible = true
    animate()
        .translationY(0f)
        .scaleY(1f)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .setDuration(duration)
        .withEndAction {
            InfolojoThemeHelper.updateStatusBarColor(
                context = activity,
                color = R.color.back_ground_overly
            )
            endActionEvent()
        }.start()
}

fun View.showAnimationFromTopToBottom(
    activity: Activity? = null,
    duration: Long = DEFAULT_VIEW_DURATION,
    endActionEvent: () -> Unit = {}
) {
    InfolojoThemeHelper.resetStatusBarBackGroundColor(activity)
    animate()
        .translationY(height.toFloat())
        .scaleY(0f)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .setDuration(duration)
        .withEndAction {
            isVisible = false
            endActionEvent()
        }.start()
}
