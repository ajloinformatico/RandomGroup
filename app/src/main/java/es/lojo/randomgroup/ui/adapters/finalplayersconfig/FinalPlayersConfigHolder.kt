package es.lojo.randomgroup.ui.adapters.finalplayersconfig

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.R
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

        // set items colors if item is clicked or not
        binding.cardView.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                binding.root.context,
                if (!item.clicked) {
                    android.R.color.holo_green_dark
                } else {
                    R.color.cardview_background
                }
            )
        )
    }
}
