package es.lojo.randomgroup.ui.configureplayersandgroups.adapters.configureplayersname

import androidx.recyclerview.widget.DiffUtil
import es.lojo.randomgroup.data.models.PlayerModel

class ConfigurePlayersNameAdapterDiffUtil : DiffUtil.ItemCallback<PlayerModel>() {
    override fun areItemsTheSame(oldItem: PlayerModel, newItem: PlayerModel): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: PlayerModel, newItem: PlayerModel): Boolean =
        oldItem == newItem
}
