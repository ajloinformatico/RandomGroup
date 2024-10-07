package es.lojo.randomgroup.commons.logger

import android.util.Log

private const val DEFAULT_PREFIX = "info"

object InfolojoLogger {
    fun log(
        ctx: String,
        message: String,
        prefix: String = DEFAULT_PREFIX,
        suffix: LoggerTypes = LoggerTypes.UNKNOWN
    ) {
        Log.d(ctx, "$prefix : $message || Type: $suffix")
    }
}
