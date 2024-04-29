package es.lojo.randomgroup.ui.randomoption.adapters

import android.icu.text.IDNA.Info
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.databinding.RowRandomOptionsSingleOptionBinding
import es.lojo.randomgroup.ui.randomoption.states.RandomOptionsActions
import es.lojo.randomgroup.ui.randomoption.vo.RandomOptionVO

private const val CLASS_NAME = "RandomOptionsFragmentAdapter"

class RandomOptionsFragmentAdapter(
    private val onAddText: (RandomOptionsActions) -> Unit
) : ListAdapter<RandomOptionVO, RandomOptionsFragmentAdapterHolder>(
    RandomOptionsFragmentAdapterDiffUtils()
) {
    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.LIST_ADAPTER
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RandomOptionsFragmentAdapterHolder = RandomOptionsFragmentAdapterHolder(
        RowRandomOptionsSingleOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItem(position: Int): RandomOptionVO? = currentList.getOrNull(position)

    override fun onBindViewHolder(
        holder: RandomOptionsFragmentAdapterHolder,
        position: Int
    ) {
        getItem(position)?.let { item ->
            holder.bind(
                item = item,
                event = onAddText
            )
        }
    }
}
