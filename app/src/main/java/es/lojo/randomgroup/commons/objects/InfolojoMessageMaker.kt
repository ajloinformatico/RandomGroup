package es.lojo.randomgroup.commons.objects

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.ui.messageanimated.fragment.MessageAnimatedBottomSheet
import es.lojo.randomgroup.commons.ui.messageanimated.vo.MessageAnimatedVO
import es.lojo.randomgroup.ui.secretcouples.bottomsheet.ShowSecretCouplesFragment
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO

object InfolojoMessageMaker {

    /** Load a custom bottomSheetDialogFragment with a message */
    fun showAnimatedMessage(text: String, fragmentManager: FragmentManager) {
        MessageAnimatedBottomSheet.newInstance(MessageAnimatedVO(text)).show(fragmentManager, MessageAnimatedBottomSheet::class.java.simpleName)
    }

    /** Load a custom bottomSheetDialogFragment with secret couples */
    fun showSecretCouples(couples: SecretCouplesVO, fragmentManager: FragmentManager) {
        ShowSecretCouplesFragment.newInstance(couples).show(fragmentManager, ShowSecretCouplesFragment::class.java.simpleName)
    }


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
