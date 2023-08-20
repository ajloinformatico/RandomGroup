package es.lojo.randomgroup.ui.adapters.finallplayerconfigindividualplayeradapter

import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.commons.toCapitalize
import es.lojo.randomgroup.databinding.RowPlayersNameBinding
import java.util.*

class FinalPlayerConfigIndividualPlayerHolder(
    private val binding: RowPlayersNameBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: String,
        position: Int
    ) {
        val playerName = "${position + 1} ${item.toCapitalize()}"
        binding.playerName.text = playerName
    }
}
