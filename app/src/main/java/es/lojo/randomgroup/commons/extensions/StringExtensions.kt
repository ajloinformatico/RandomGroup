package es.lojo.randomgroup.commons.extensions

import android.text.Editable

/**
 * Extension function to convert Strings type to Editable
 */
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun String?.toIntOrElse(default: Int = 0): Int = try {
    this.orEmpty().takeIf { it.isNotEmpty() }?.toInt() ?: default
} catch (e: Exception) {
    default
}

fun String.toCapitalize(): String {
    var result = ""
    this.forEachIndexed { index, c ->
        result += if (index == 0) {
            c.uppercaseChar()
        } else {
            c.lowercaseChar()
        }
    }
    return result
}
