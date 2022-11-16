package es.lojo.randomgroup.commons

import android.text.Editable

private const val CLASS_NAME = "StringExtensions"

/**
 * Extension function to convert Strings type to Editable
 */
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun String?.toIntCustom(): Int = if (this.orEmpty().isEmpty()) {
    0
} else {
    try {
        this.orEmpty().toInt()
    } catch (e: Exception) {
        CustomLog.log(CLASS_NAME, e.toString())
        0
    }
}
