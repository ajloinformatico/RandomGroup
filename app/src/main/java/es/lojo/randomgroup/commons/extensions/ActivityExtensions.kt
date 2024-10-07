package es.lojo.randomgroup.commons.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.R
import es.lojo.randomgroup.ui.activities.WebViewActivity

/**
 * Launch intent to infolojo.es
 */
fun Activity.manageWebViewInfolojo() {
    startActivity(Intent(this, WebViewActivity::class.java).apply {
        putExtra(WebViewActivity.WEB_VIEW_NAME, resources.getString(R.string.infolojo_title))
        putExtra(WebViewActivity.WEB_VIEW_URL, resources.getString(R.string.infolojo_complete_url))
    })
}

fun Activity.hideVirtualKeyBoard(editText: EditText) {
    val inputMethodManager =
        this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
}
