package es.lojo.randomgroup.ui.secretcouples.adapters.showsecretcouples

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.databinding.FragmentShowSecretCouplesListDialogItemBinding
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCoupleVO

private const val CLASS_NAME = "ShowSecretCouplesAdapter"

class ShowSecretCouplesAdapter() : ListAdapter<SecretCoupleVO, ShowSecretCouplesViewHolder>(
    ShowSecretCoupleFragmentAdapterDiffUtils()
) {

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.LIST_ADAPTER
        )
    }

    override fun getItem(position: Int): SecretCoupleVO? = currentList.getOrNull(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowSecretCouplesViewHolder =
        ShowSecretCouplesViewHolder(
            FragmentShowSecretCouplesListDialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ShowSecretCouplesViewHolder, position: Int) {
        holder.bind(
            item = getItem(position) ?: return
        )
    }
}
