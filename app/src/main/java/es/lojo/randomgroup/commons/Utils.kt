package es.lojo.randomgroup.commons

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

fun hideVirtualKeyBoard(activity: AppCompatActivity, editText: EditText) {
    val inputMethodManager =
        activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
}
