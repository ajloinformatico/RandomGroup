package es.lojo.randomgroup.ui.randomoption.adapters

import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.commons.extensions.toEditable
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.databinding.RowRandomOptionsSingleOptionBinding
import es.lojo.randomgroup.ui.randomoption.states.RandomOptionsActions
import es.lojo.randomgroup.ui.randomoption.vo.RandomOptionVO

// TODO RESOURCE
private const val OPTION_TEXT = "New Option"
private const val CLASS_NAME = "RandomOptionsFragmentAdapterHolder"

class RandomOptionsFragmentAdapterHolder(
    private val binding: RowRandomOptionsSingleOptionBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.VIEW_HOLDER
        )
    }

    fun bind(
        item: RandomOptionVO,
        event: (RandomOptionsActions) -> Unit
    ) {
        binding.apply {
            root.setOnClickListener {
                event(RandomOptionsActions.HideKeyBoard(item))
            }
            text.hint = OPTION_TEXT.toEditable()
            text.text = item.text.toEditable()
            text.doOnTextChanged { text, _, _, _ ->
                text?.toString()?.let {
                    item.text = it
                    event(RandomOptionsActions.Update(item))
                }
            }
            removeIcon.setOnClickListener {
                event(RandomOptionsActions.Remove(item))
            }
        }
    }
}
