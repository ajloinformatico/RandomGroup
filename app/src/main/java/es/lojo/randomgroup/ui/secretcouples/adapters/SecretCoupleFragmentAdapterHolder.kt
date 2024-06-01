package es.lojo.randomgroup.ui.secretcouples.adapters

import androidx.recyclerview.widget.RecyclerView
import es.lojo.randomgroup.commons.extensions.toEditable
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.commons.objects.InfolojoMessageMaker
import es.lojo.randomgroup.databinding.RowRandomOptionsSingleOptionBinding
import es.lojo.randomgroup.ui.secretcouples.states.SecretCoupleActions
import es.lojo.randomgroup.ui.secretcouples.utils.EmailHelper
import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO

private const val CLASS_NAME = "SecretCoupleFragmentAdapterHolder"
// TODO RESOURCE
private const val EMAIL_TEXT = "New email"

// TODO update binding with new row
class SecretCoupleFragmentAdapterHolder(
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
        item: SingleSecretCoupleVO,
        event: (SecretCoupleActions) -> Unit
    ) {
        binding.apply {
            root.setOnClickListener {
                event(SecretCoupleActions.HideKeyBoard(item))
            }
            with(text) {
                hint = EMAIL_TEXT.toEditable()
                text = item.email.toEditable()
                // TODO CHECK IT
                /*
                doOnTextChanged { textContent, _, _, _ ->
                    textContent?.toString()?.let {
                        item.email = it
                    }
                }
                */
                setOnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        if (EmailHelper.checkEmail(text.toString()).not()) {
                            InfolojoMessageMaker.showError(
                                view = binding.root,
                                text = "$text is not valid",
                            )
                        } else {
                            event(SecretCoupleActions.Update(item))
                        }
                    }
                }
            }
            removeIcon.setOnClickListener {
                event(SecretCoupleActions.Remove(item))
            }
        }
    }
}
