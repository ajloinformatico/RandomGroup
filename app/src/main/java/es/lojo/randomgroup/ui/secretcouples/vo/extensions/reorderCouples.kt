package es.lojo.randomgroup.ui.secretcouples.vo.extensions

import android.util.Log
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
    Log.d("TonyTest", "reorder list with couples ${this.size}: $this")
    val listOfPairs: MutableList<Pair<SingleSecretCoupleVO, SingleSecretCoupleVO>> = mutableListOf()
    val reordered = this.shuffled()
    reordered.forEachIndexed { index, singleSecretCoupleVO ->
        if (index % 2 == 0) {
            listOfPairs.add(
                Pair(
                    singleSecretCoupleVO,
                    reordered[index + 1]
                )
            )
        }
    }
    return SecretCouplesVO(listOfPairs.map { SecretCoupleVO(it) })
}
