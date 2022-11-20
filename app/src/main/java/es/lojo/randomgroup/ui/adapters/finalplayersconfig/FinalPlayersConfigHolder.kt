package es.lojo.randomgroup.ui.adapters.finalplayersconfig

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.R
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel
import es.lojo.randomgroup.databinding.RowPlayersGroupBinding
import es.lojo.randomgroup.ui.adapters.finallplayerconfigindividualplayeradapter.FinalPlayerConfigIndividualPlayerAdapter
import es.lojo.randomgroup.ui.states.FinalPlayerConfigState

class FinalPlayersConfigHolder(
    private val binding: RowPlayersGroupBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter = FinalPlayerConfigIndividualPlayerAdapter()

    fun bind(
        item: ConfigurePlayersFinalGroupsModel,
        event: (FinalPlayerConfigState) -> Unit
    ) {
        val groupName = "Group ${item.groupNumber.toString()}"
        binding.groupName.text = groupName
        binding.recycler.adapter = adapter
        adapter.submitList(item.playersName)

        // set items colors if item is clicked or not
        binding.cardView.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                binding.root.context,
                if (item.clicked) {
                    android.R.color.holo_green_dark
                } else {
                    R.color.cardview_background
                }
            )
        )

        binding.infoContainer.setOnClickListener {
            doOnClick(event, item)
        }
        binding.root.setOnClickListener {
            doOnClick(event, item)
        }
        binding.cardView.setOnClickListener {
            doOnClick(event, item)
        }
        binding.groupName.setOnClickListener {
            doOnClick(event, item)
        }
        binding.recycler.setOnClickListener { doOnClick(event, item) }
    }

    private fun doOnClick(
        event: (FinalPlayerConfigState) -> Unit,
        item: ConfigurePlayersFinalGroupsModel
    ) {
        event(
            FinalPlayerConfigState(
                group = ConfigurePlayersFinalGroupsModel(
                    groupNumber = item.groupNumber,
                    playersName = item.playersName,
                    clicked = !item.clicked
                )
            )
        )
    }

}
