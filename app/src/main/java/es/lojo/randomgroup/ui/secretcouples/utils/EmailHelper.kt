package es.lojo.randomgroup.ui.secretcouples.utils

import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes

private const val CLASS_NAME = "EmailHelper"

object EmailHelper {

    private val invalidSymbols = listOf(",","*","+","-","[","]","^")

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.OBJECT
        )
    }

    private fun String.checkInvalidSymbols(): Boolean {
        invalidSymbols.map {
            if (this.contains(it)) return false
        }
        return true
    }

    fun checkEmail(email: String): Boolean {
        email.takeIf { it.contains("@") && it.checkInvalidSymbols() }?.split("@")?.takeIf { it.size == 2 }?.let {
            it.lastOrNull()?.takeIf { it.contains(".") }?.split(".").takeIf { it?.size == 2 }?.let {
                return true
            }
        }
        return false
    }
}