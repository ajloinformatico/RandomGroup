package es.lojo.randomgroup.ui.secretcouples.states

import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO
import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO

// region errors
private const val UNKNOWN_ERROR_TEXT = "Error: Something was wrong"
private const val NOT_PAR_COUPLES = "Error: Couples must be par"
private const val ERROR_LAUNCHING_EMAIL = "Error: The email could not be sent"
// endregion errors

// region ok
private const val EMAIL_SUCCESS = "Emails has been sent"
// endregion ok

sealed interface SecretCoupleStates {
    object Loading : SecretCoupleStates
    data class Render(
        val items: List<SingleSecretCoupleVO>
    ) : SecretCoupleStates
    data class Error(
        val error: SecretCoupleError
    ) : SecretCoupleStates
    data class ShowCouples(
        val result: SecretCouplesVO
    ) : SecretCoupleStates
    data class SendEmail(
        val result: SecretCouplesVO
    ) : SecretCoupleStates
    data class EmailSuccess(
        val message: String = EMAIL_SUCCESS
    ) : SecretCoupleStates
}

enum class SecretCoupleError(var message: String) {
    UNKNOWN(message = UNKNOWN_ERROR_TEXT),
    NOT_PAR(message = NOT_PAR_COUPLES),
    EMAIL_COULD_NOT_BE_SENT(message = ERROR_LAUNCHING_EMAIL)
}
