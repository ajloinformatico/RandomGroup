package es.lojo.randomgroup.ui.adapters.finallplayerconfigindividualplayeradapter

import androidx.recyclerview.widget.DiffUtil

object FinalPlayerConfigIndividualPlayerDiffUtil :
    DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean = oldItem == newItem
}