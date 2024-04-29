package es.lojo.randomgroup.ui.configureplayersandgroups.adapters.configureplayersname

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import es.lojo.randomgroup.data.models.PlayerModel
import es.lojo.randomgroup.databinding.RowConfigurePlayersNameBinding
import es.lojo.randomgroup.ui.configureplayersandgroups.states.PlayerUpdate

class ConfigurePlayersNameAdapter(
    private val event: (PlayerUpdate) -> Unit
) : ListAdapter<PlayerModel, ConfigurePlayersNameHolder>(ConfigurePlayersNameAdapterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfigurePlayersNameHolder =
        ConfigurePlayersNameHolder(
            RowConfigurePlayersNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ConfigurePlayersNameHolder, position: Int) {
        holder.bind(
            item = getItem(position),
            position = position,
            event = event
        )
    }
}
