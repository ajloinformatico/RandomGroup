package es.lojo.randomgroup.commons.objects

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

object InfolojoMessageMaker {
    /**
     * Show error using simple custom toast
     */
    fun showError(view: View?, text: String, timeDuration: Int = Snackbar.LENGTH_SHORT) {
        view ?: return
        Snackbar.make(view, text, timeDuration)
            .setBackgroundTint(Color.RED)
            .setTextColor(Color.WHITE).show()
    }

    /**
     * Show positive message message using simple custom toast
     */
    fun showPositiveMessage(view: View, text: String, timeDuration: Int = Snackbar.LENGTH_SHORT) =
        Snackbar.make(view, text, timeDuration)
            .setBackgroundTint(Color.GREEN)
            .setTextColor(Color.WHITE).show()


    /**
     * Show message with default SnackBarStyle
     */
    fun showMessage(view: View, text: String, timeDuration: Int = Snackbar.LENGTH_SHORT) =
        Snackbar.make(view, text, timeDuration).show()
}
