package es.lojo.randomgroup.ui.adapters.configureplayersname

import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.commons.toEditable
import es.lojo.randomgroup.data.models.PlayerModel
import es.lojo.randomgroup.databinding.RowConfigurePlayersNameBinding

/**
 * Holder to draw items in recycler
 */
class ConfigurePlayersNameHolder(
    private val binding: RowConfigurePlayersNameBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: PlayerModel,
        position: Int
    ) {
        val playerPosition = "Player ${position + 1}"
        binding.playerNumber.text = playerPosition
        binding.playerName.hint = item.name.toEditable()
    }
}
