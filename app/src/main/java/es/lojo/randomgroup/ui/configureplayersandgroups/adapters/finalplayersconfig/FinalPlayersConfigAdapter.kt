package es.lojo.randomgroup.ui.configureplayersandgroups.adapters.finalplayersconfig

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel
import es.lojo.randomgroup.databinding.RowPlayersGroupBinding
import es.lojo.randomgroup.ui.configureplayersandgroups.states.FinalPlayerConfigState

class FinalPlayersConfigAdapter(
    private val event: (FinalPlayerConfigState) -> Unit
) : ListAdapter<ConfigurePlayersFinalGroupsModel, FinalPlayersConfigHolder>(
    FinalPlayersConfigAdapterDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalPlayersConfigHolder =
        FinalPlayersConfigHolder(
            binding = RowPlayersGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            event = event
        )

    override fun onBindViewHolder(holder: FinalPlayersConfigHolder, position: Int) {
        holder.bind(
            item = getItem(position)
        )
    }
}
