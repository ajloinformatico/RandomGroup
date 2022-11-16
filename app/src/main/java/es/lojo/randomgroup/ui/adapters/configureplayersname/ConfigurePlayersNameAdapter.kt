package es.lojo.randomgroup.ui.adapters.configureplayersname

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.data.models.PlayerModel
import es.lojo.randomgroup.databinding.RowConfigurePlayersNameBinding

class ConfigurePlayersNameAdapter :
    ListAdapter<PlayerModel, RecyclerView.ViewHolder>(ConfigurePlayersNameAdapterDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ConfigurePlayersNameHolder(
            RowConfigurePlayersNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ConfigurePlayersNameHolder?)?.bind(
            item = getItem(position),
            position = position
        )
    }
}
