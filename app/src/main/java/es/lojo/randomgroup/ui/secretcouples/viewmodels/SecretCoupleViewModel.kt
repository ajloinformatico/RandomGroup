package es.lojo.randomgroup.ui.secretcouples.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.commons.utils.generateCustomUniqueId
import es.lojo.randomgroup.ui.secretcouples.states.SecretCoupleError
import es.lojo.randomgroup.ui.secretcouples.states.SecretCoupleStates
import es.lojo.randomgroup.ui.secretcouples.vo.extensions.reorderListOfSingleCouples
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO
import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO
import kotlinx.coroutines.launch

private const val CLASS_NAME = "SecretCoupleViewModel"
private const val EXAMPLE_EMAIL = "example@example.example"

class SecretCoupleViewModel : ViewModel() {
    // region view states
    private val _viewState = MutableLiveData<SecretCoupleStates>()
    val viewState: LiveData<SecretCoupleStates> = _viewState

    private val _items = MutableLiveData<List<SingleSecretCoupleVO>>()
    val items: LiveData<List<SingleSecretCoupleVO>> = _items

    private var couplesReordered: SecretCouplesVO? = null

    fun setState(value: SecretCoupleStates) {
        _viewState.postValue(value)
    }
    // endregion view states

    // region init
    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.VIEW_MODEL
        )
        viewModelScope.launch {
            _viewState.value = SecretCoupleStates.Loading
            loadInitialItems()
        }
    }
    // endregion init

    // region private
    private fun List<SingleSecretCoupleVO>?.clone(): MutableList<SingleSecretCoupleVO> =
        this.orEmpty().toList().toMutableList()

    private fun loadInitialItems() {
        _items.value = listOf(instanceNewSecretCoupleVO(), instanceNewSecretCoupleVO()).also {
            setState(SecretCoupleStates.Render(it))
        }
    }

    private fun instanceNewSecretCoupleVO(): SingleSecretCoupleVO = SingleSecretCoupleVO(
        id = generateCustomUniqueId(),
        email = EXAMPLE_EMAIL
    )


    private fun finish(sendEmail: Boolean) {
        _items.value?.takeIf { it.size % 2 == 0 }?.let { couples ->
            couplesReordered ?: reorder()
            setState(
                couplesReordered?.let {
                    if (sendEmail) {
                        SecretCoupleStates.SendEmail(it)
                    } else {
                        SecretCoupleStates.ShowCouples(it)
                    }
                } ?: SecretCoupleStates.Error(SecretCoupleError.NOT_PAR)
            )
        } ?: setState(SecretCoupleStates.Error(SecretCoupleError.NOT_PAR))
    }

    // endregion private

    // region public
    fun addItem() {
        // If user update an item we remove private list
        couplesReordered = null
        val newItems = _items.value.clone()
        newItems.add(instanceNewSecretCoupleVO())
        _items.value = newItems.toList()
    }

    fun updateItem(item: SingleSecretCoupleVO) {
        // If user update an item we remove private list
        couplesReordered = null
        val newItems = _items.value.clone()
        newItems.toList().mapIndexed { index, currentItem ->
            if (currentItem.id == item.id) {
                newItems[index] = item
                _items.value = newItems.toList()
                return
            }
        }
    }

    fun removeItem(item: SingleSecretCoupleVO) {
        // If user remove an item we remove private list
        couplesReordered = null
        val newItems = _items.value.clone()
        newItems.remove(item)
        _items.value = newItems
    }

    // reorder list
    private fun reorder() {
        _items.value?.takeIf { it.size % 2 == 0 }?.let { couples ->
            couplesReordered = couples.reorderListOfSingleCouples()
        } ?: setState(SecretCoupleStates.Error(SecretCoupleError.NOT_PAR))
    }

    fun showCouples() {
        finish(sendEmail = false)
    }

    fun sendEmail() {
        finish(sendEmail = true)
    }
    // endregion public
}
