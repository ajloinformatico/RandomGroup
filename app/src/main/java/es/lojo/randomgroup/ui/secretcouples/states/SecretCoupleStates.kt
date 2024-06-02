package es.lojo.randomgroup.ui.secretcouples.states

import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO
import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO

private const val UNKNOWN_ERROR_TEXT = "Error: Something was wrong"
private const val NOT_PAR_COUPLES = "Error: Couples must be par"

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
}

enum class SecretCoupleError(val message: String) {
    UNKNOWN(message = UNKNOWN_ERROR_TEXT),
    NOT_PAR(message = NOT_PAR_COUPLES),
}
