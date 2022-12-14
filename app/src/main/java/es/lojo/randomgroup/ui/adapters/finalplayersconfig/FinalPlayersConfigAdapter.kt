package es.lojo.randomgroup.ui.adapters.finalplayersconfig

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel
import es.lojo.randomgroup.databinding.RowPlayersGroupBinding
import es.lojo.randomgroup.ui.states.FinalPlayerConfigState

class FinalPlayersConfigAdapter(
    private val event: (FinalPlayerConfigState) -> Unit
) : ListAdapter<ConfigurePlayersFinalGroupsModel, RecyclerView.ViewHolder>(
    FinalPlayersConfigAdapterDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FinalPlayersConfigHolder(
            RowPlayersGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FinalPlayersConfigHolder)?.bind(
            item = getItem(position),
            event = event
        )
    }
}
