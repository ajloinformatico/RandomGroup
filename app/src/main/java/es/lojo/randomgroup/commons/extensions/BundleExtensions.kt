package es.lojo.randomgroup.commons.extensions

import android.os.Build
import android.os.Bundle
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import java.io.Serializable

object BundleExtensions {
    const val CLASS_NAME = "BundleExtensions"
}

inline fun <reified T : Serializable> Bundle.takeSerializable(key: String): T? =
    try {
        if (Build.VERSION.SDK_INT >= 33) {
            getSerializable(key, T::class.java)
        } else {
            getSerializable(key) as? T
        }
    } catch (e: Exception) {
        InfolojoLogger.log(BundleExtensions.CLASS_NAME, e.message.orEmpty())
        null
    }

inline fun <reified T : Serializable> Bundle.takeSerializable(key: String, clazz: Class<T>): T? =
    try {
        if (Build.VERSION.SDK_INT >= 33) {
            getSerializable(key, clazz)
        } else {
            getSerializable(key) as? T
        }
    } catch (e: Exception) {
        InfolojoLogger.log(BundleExtensions.CLASS_NAME, e.message.orEmpty())
        null
    }

