package es.lojo.randomgroup.commons
import android.util.Log

private const val DEFAULT_PREFIX = "info"

object InfolojoLogger {
    fun log(
        className: String,
        message: String,
        prefix: String = DEFAULT_PREFIX,
        suffix: String = ""
    ) {
        Log.d(className, "$prefix : $message $suffix")
    }
}
