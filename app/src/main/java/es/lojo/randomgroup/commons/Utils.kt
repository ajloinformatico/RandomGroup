package es.lojo.randomgroup.commons

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

fun hideVirtualKeyBoard(activity: AppCompatActivity, editText: EditText) {
    val inputMethodManager =
        activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
}

/**
 * Calculate players for one group
 */
fun calculatePlayersPerGroup(numberOfPlayers: Int, numberOfGroups: Int): Int {
    val playerPerGroup: Double = numberOfPlayers.toDouble() / numberOfGroups.toDouble()
    val dec: Float =
        playerPerGroup.toString().substring(playerPerGroup.toString().indexOf(".")).toFloat()
    return playerPerGroup.toInt() + if (dec >= .5f && dec < 1f) 1 else 0
}
