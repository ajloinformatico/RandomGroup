package es.lojo.randomgroup.ui.secretcouples.adapters.showsecretcouples

import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.databinding.FragmentShowSecretCouplesListDialogItemBinding
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCoupleVO
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO

private const val CLASS_NAME = "ShowSecretCouplesAdapter"

class ShowSecretCouplesAdapter() : ListAdapter<SecretCoupleVO, ShowSecretCouplesViewHolder>(
    ShowSecretCoupleFragmentAdapterDiffUtils()
) {
    //  TODO CONTINUE HERE
}




class ShowSecretCouplesViewHolder(
    private val binding: FragmentShowSecretCouplesListDialogItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SecretCoupleVO) {
        // TODO()
    }
}

class ShowSecretCoupleFragmentAdapterDiffUtils : DiffUtil.ItemCallback<SecretCoupleVO>() {
    init {
        // TODO InfolojoLogger.log
    }

    override fun areItemsTheSame(oldItem: SecretCoupleVO, newItem: SecretCoupleVO): Boolean =
        oldItem.couple.first.id == newItem.couple.first.id &&
                oldItem.couple.second.id == oldItem.couple.second.id


    override fun areContentsTheSame(oldItem: SecretCoupleVO, newItem: SecretCoupleVO): Boolean =
        oldItem == newItem
}