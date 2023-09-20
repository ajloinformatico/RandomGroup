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
    private val event: (FinalPlayerConfigState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var item: ConfigurePlayersFinalGroupsModel? = null

    private val adapter = FinalPlayerConfigIndividualPlayerAdapter {
        doOnClick()
    }

    fun bind(
        item: ConfigurePlayersFinalGroupsModel,
    ) {
        this.item = item
        with(binding) {
            val groupNameText = "${root.resources.getString(R.string.Group)} ${item.groupNumber}"
            groupName.text = groupNameText
            recycler.adapter = adapter
            adapter.submitList(item.playersName)

            // set items colors if item is clicked or not
            cardView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    binding.root.context,
                    if (item.clicked) {
                        android.R.color.holo_green_dark
                    } else {
                        R.color.cardview_background
                    }
                )
            )
            groupName.setOnClickListener {
                doOnClick()
            }
        }
    }

    private fun doOnClick() {
        item?.let { safeItem ->
            event(
                FinalPlayerConfigState(
                    group = ConfigurePlayersFinalGroupsModel(
                        groupNumber = safeItem.groupNumber,
                        playersName = safeItem.playersName,
                        clicked = !safeItem.clicked
                    )
                )
            )
        }
    }
}
