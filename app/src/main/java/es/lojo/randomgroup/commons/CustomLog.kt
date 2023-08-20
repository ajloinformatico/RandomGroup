package es.lojo.randomgroup.commons
import android.util.Log
object CustomLog {
    fun log(
        className: String,
        message: String,
        prefix: String = "info",
        suffix: String = ""
    ) {
        Log.d(className, "$prefix : $message $suffix")
    }
}
