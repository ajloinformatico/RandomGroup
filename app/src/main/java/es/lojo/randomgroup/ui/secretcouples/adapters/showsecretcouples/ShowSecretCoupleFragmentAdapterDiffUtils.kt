package es.lojo.randomgroup.ui.secretcouples.adapters.showsecretcouples

import androidx.recyclerview.widget.DiffUtil
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCoupleVO

private const val CLASS_NAME = "ShowSecretCoupleFragmentAdapterDiffUtils"

class ShowSecretCoupleFragmentAdapterDiffUtils : DiffUtil.ItemCallback<SecretCoupleVO>() {

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.DIFF_UTILS
        )
    }

    override fun areItemsTheSame(oldItem: SecretCoupleVO, newItem: SecretCoupleVO): Boolean =
        oldItem.couple.first.id == newItem.couple.first.id &&
                oldItem.couple.second.id == oldItem.couple.second.id


    override fun areContentsTheSame(oldItem: SecretCoupleVO, newItem: SecretCoupleVO): Boolean =
        oldItem == newItem
}
