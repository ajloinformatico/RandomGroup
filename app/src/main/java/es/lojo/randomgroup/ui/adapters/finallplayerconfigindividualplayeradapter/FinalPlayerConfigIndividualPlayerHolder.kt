package es.lojo.randomgroup.ui.adapters.finallplayerconfigindividualplayeradapter

import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.commons.toCapitalize
import es.lojo.randomgroup.databinding.RowPlayersNameBinding

class FinalPlayerConfigIndividualPlayerHolder(
    private val binding: RowPlayersNameBinding,
    private val onClickListener: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: String,
        position: Int
    ) {
        val playerNameText = "${position + 1} ${item.toCapitalize()}"
        binding.apply {
            playerName.text = playerNameText
            root.setOnClickListener {
                onClickListener()
            }
        }
    }
}
