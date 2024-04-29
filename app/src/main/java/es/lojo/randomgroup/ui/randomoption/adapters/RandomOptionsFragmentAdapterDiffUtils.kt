package es.lojo.randomgroup.ui.randomoption.adapters

import androidx.recyclerview.widget.DiffUtil
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.ui.randomoption.vo.RandomOptionVO

private const val CLASS_NAME = "RandomOptionsFragmentAdapterDiffUtils"

class RandomOptionsFragmentAdapterDiffUtils : DiffUtil.ItemCallback<RandomOptionVO>() {

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.DIFF_UTILS
        )
    }

    override fun areItemsTheSame(
        oldItem: RandomOptionVO,
        newItem: RandomOptionVO
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: RandomOptionVO,
        newItem: RandomOptionVO
    ): Boolean = oldItem == newItem
}
