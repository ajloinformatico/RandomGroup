package es.lojo.randomgroup.commons.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import es.lojo.randomgroup.R

/**
 * Launch intent to infolojo.es
 */
fun Activity.manageWebViewInfolojo() {
    startActivity(
        Intent(Intent.ACTION_VIEW).setData(
            Uri.parse(
                resources.getString(
                    R.string.infolojo_complete_url
                )
            )
        )
    )
}

fun Activity.hideVirtualKeyBoard(editText: EditText) {
    val inputMethodManager =
        this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
}
