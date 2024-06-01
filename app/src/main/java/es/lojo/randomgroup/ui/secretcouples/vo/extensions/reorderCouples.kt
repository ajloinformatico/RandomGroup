package es.lojo.randomgroup.ui.secretcouples.vo.extensions

import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCoupleVO
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO
import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO

fun SecretCouplesVO.reorderCouples(): SecretCouplesVO = this.couples.reorderCouples()
fun List<SecretCoupleVO>.reorderCouples(): SecretCouplesVO {
    val couples: MutableList<SingleSecretCoupleVO> = mutableListOf()
    this.map { 
        couples.addAll(listOf(it.couple.first, it.couple.second))
    }
    return couples.toList().reorderListOfSingleCouples()
}

fun List<SingleSecretCoupleVO>.reorderListOfSingleCouples(): SecretCouplesVO {
    val reordered = this.shuffled().zipWithNext().map { SecretCoupleVO(it) }
    return SecretCouplesVO(reordered.toList())
}
