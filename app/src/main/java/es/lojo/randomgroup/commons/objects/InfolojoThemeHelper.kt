package es.lojo.randomgroup.commons.objects

import android.app.Activity
import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object InfolojoThemeHelper {

    private var statusBarColor: Int? = null

    @JvmStatic
    fun getColor(context: Context, @ColorRes color: Int): Int = ContextCompat.getColor(
        context,
        color
    )

    fun updateStatusBarColor(context: Activity?, @ColorRes color: Int) {
        context ?: return
        statusBarColor = context.window.statusBarColor
        context.window.statusBarColor = getColor(context, color)
    }

    fun resetStatusBarBackGroundColor(context: Activity?) {
        context ?: return
        statusBarColor?.let(context.window::setStatusBarColor)
    }
}
