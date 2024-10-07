package es.lojo.randomgroup.ui.secretcouples.vo.model

import java.io.Serializable

data class SecretCoupleVO(
    val couple: Pair<SingleSecretCoupleVO, SingleSecretCoupleVO>
) : Serializable
