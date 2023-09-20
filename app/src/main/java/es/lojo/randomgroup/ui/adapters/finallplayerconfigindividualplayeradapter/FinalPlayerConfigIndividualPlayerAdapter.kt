package es.lojo.randomgroup.ui.adapters.finallplayerconfigindividualplayeradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.databinding.RowPlayersNameBinding

class FinalPlayerConfigIndividualPlayerAdapter(
    private val onClickListener: () -> Unit
) : ListAdapter<String, RecyclerView.ViewHolder>(
        FinalPlayerConfigIndividualPlayerDiffUtil
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FinalPlayerConfigIndividualPlayerHolder(
            binding = RowPlayersNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickListener = onClickListener
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FinalPlayerConfigIndividualPlayerHolder)?.bind(
            item = getItem(position),
            position = position
        )
    }
}
