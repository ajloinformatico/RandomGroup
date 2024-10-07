package es.lojo.randomgroup.ui.secretcouples.adapters.secretcouples

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.databinding.RowRandomOptionsSingleOptionBinding
import es.lojo.randomgroup.ui.secretcouples.states.SecretCoupleActions
import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO

private const val CLASS_NAME = "SecretCoupleFragmentAdapter"

class SecretCoupleFragmentAdapter(
    private val actions: (SecretCoupleActions) -> Unit
) : ListAdapter<SingleSecretCoupleVO, SecretCoupleFragmentAdapterHolder>(
    SecretCoupleFragmentAdapterDiffUtils()
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
    ): SecretCoupleFragmentAdapterHolder = SecretCoupleFragmentAdapterHolder(
        RowRandomOptionsSingleOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItem(position: Int): SingleSecretCoupleVO? = currentList.getOrNull(position)

    override fun onBindViewHolder(holder: SecretCoupleFragmentAdapterHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(
                item = item,
                event = actions
            )
        }
    }
}
