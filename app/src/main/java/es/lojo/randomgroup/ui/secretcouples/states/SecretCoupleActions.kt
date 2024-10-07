package es.lojo.randomgroup.ui.secretcouples.states

import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO

sealed class SecretCoupleActions(
    open val item: SingleSecretCoupleVO
) {
    data class HideKeyBoard(
        override val item: SingleSecretCoupleVO
    ) : SecretCoupleActions(item = item)

    data class Update(
        override val item: SingleSecretCoupleVO
    ) : SecretCoupleActions(item = item)

    data class Remove(
        override val item: SingleSecretCoupleVO
    ) : SecretCoupleActions(item = item)
}
