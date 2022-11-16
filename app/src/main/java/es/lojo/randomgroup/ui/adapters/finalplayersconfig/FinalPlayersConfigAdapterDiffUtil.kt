package es.lojo.randomgroup.ui.adapters.finalplayersconfig

import androidx.recyclerview.widget.DiffUtil
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel

object FinalPlayersConfigAdapterDiffUtil :
    DiffUtil.ItemCallback<ConfigurePlayersFinalGroupsModel>() {
    override fun areItemsTheSame(
        oldItem: ConfigurePlayersFinalGroupsModel,
        newItem: ConfigurePlayersFinalGroupsModel
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ConfigurePlayersFinalGroupsModel,
        newItem: ConfigurePlayersFinalGroupsModel
    ): Boolean =
        oldItem.groupNumber == newItem.groupNumber
                && oldItem.playersName == newItem.playersName
}
