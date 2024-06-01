package es.lojo.randomgroup.ui.secretcouples.states

import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO

sealed interface FinalSecretCouplesStates {
    data class Render(
        val couples: SecretCouplesVO
    ) : FinalSecretCouplesStates

    object Loading: FinalSecretCouplesStates
    object Error: FinalSecretCouplesStates
}
