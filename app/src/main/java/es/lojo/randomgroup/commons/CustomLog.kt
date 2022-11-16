package es.lojo.randomgroup.commons
import android.util.Log
class CustomLog {
    companion object {
        fun log(
            className: String,
            message: String,
            prefix: String = "info",
            suffix: String = ""
        ) {
            Log.d(className, "$prefix : $message $suffix")
        }
    }
}
