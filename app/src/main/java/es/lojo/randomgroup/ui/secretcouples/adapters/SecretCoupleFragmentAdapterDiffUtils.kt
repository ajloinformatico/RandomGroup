package es.lojo.randomgroup.ui.secretcouples.adapters

import androidx.recyclerview.widget.DiffUtil
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO

private const val CLASS_NAME = "SecretCoupleFragmentAdapterDiffUtils"

class SecretCoupleFragmentAdapterDiffUtils : DiffUtil.ItemCallback<SingleSecretCoupleVO>() {
    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.DIFF_UTILS
        )
    }

    override fun areItemsTheSame(
        oldItem: SingleSecretCoupleVO,
        newItem: SingleSecretCoupleVO
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: SingleSecretCoupleVO,
        newItem: SingleSecretCoupleVO
    ): Boolean = oldItem == newItem
}
