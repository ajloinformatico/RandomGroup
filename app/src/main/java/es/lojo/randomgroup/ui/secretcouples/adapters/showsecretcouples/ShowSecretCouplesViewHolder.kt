package es.lojo.randomgroup.ui.secretcouples.adapters.showsecretcouples

import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.databinding.FragmentShowSecretCouplesListDialogItemBinding
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCoupleVO

private const val CLASS_NAME = "ShowSecretCouplesViewHolder"

class ShowSecretCouplesViewHolder(
    private val binding: FragmentShowSecretCouplesListDialogItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.VIEW_HOLDER
        )
    }

    fun bind(item: SecretCoupleVO) {
        binding.email1.text = item.couple.first.email
        binding.email2.text = item.couple.second.email
    }
}
