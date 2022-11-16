package es.lojo.randomgroup.ui.adapters.finalplayersconfig

import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel
import es.lojo.randomgroup.databinding.RowPlayersGroupBinding
import es.lojo.randomgroup.ui.adapters.finallplayerconfigindividualplayeradapter.FinalPlayerConfigIndividualPlayerAdapter

class FinalPlayersConfigHolder(
    private val binding: RowPlayersGroupBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter = FinalPlayerConfigIndividualPlayerAdapter()

    fun bind(
        item: ConfigurePlayersFinalGroupsModel,
        position: Int
    ) {
        val groupName = "Group ${position + 1}"
        binding.groupName.text = groupName
        binding.recycler.adapter = adapter
        adapter.submitList(item.playersName)
    }
}