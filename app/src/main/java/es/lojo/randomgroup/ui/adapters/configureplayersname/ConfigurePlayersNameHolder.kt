package es.lojo.randomgroup.ui.adapters.configureplayersname

import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.commons.toEditable
import es.lojo.randomgroup.data.models.PlayerModel
import es.lojo.randomgroup.databinding.RowConfigurePlayersNameBinding
import es.lojo.randomgroup.ui.states.PlayerUpdate

/**
 * Holder to draw items in recycler
 */
class ConfigurePlayersNameHolder(
    private val binding: RowConfigurePlayersNameBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: PlayerModel,
        position: Int,
        event: (PlayerUpdate) -> Unit
    ) {
        val playerPosition = "Player ${position + 1}"
        binding.apply {
            playerNumber.text = playerPosition
            playerName.hint = item.name.toEditable()
            playerName.doOnTextChanged { text, start, before, count ->
                event(
                    PlayerUpdate(
                        position = position,
                        name = text.toString()
                    )
                )
            }
        }
    }
}
