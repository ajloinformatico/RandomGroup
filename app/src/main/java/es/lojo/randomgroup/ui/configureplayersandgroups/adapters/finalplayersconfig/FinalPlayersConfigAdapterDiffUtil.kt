package es.lojo.randomgroup.ui.configureplayersandgroups.adapters.finalplayersconfig

import androidx.recyclerview.widget.DiffUtil
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel

class FinalPlayersConfigAdapterDiffUtil :
    DiffUtil.ItemCallback<ConfigurePlayersFinalGroupsModel>() {
    override fun areItemsTheSame(
        oldItem: ConfigurePlayersFinalGroupsModel,
        newItem: ConfigurePlayersFinalGroupsModel
    ): Boolean = oldItem === newItem

    override fun areContentsTheSame(
        oldItem: ConfigurePlayersFinalGroupsModel,
        newItem: ConfigurePlayersFinalGroupsModel
    ): Boolean = oldItem == newItem
}
