package es.lojo.randomgroup.ui.secretcouples.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.commons.utils.generateCustomUniqueId
import es.lojo.randomgroup.data.models.EXPECTED
import es.lojo.randomgroup.data.models.EmailSenderModel
import es.lojo.randomgroup.features.emailsender.GMailSender
import es.lojo.randomgroup.ui.secretcouples.states.SecretCoupleError
import es.lojo.randomgroup.ui.secretcouples.states.SecretCoupleStates
import es.lojo.randomgroup.ui.secretcouples.utils.EmailHelper
import es.lojo.randomgroup.ui.secretcouples.vo.extensions.reorderListOfSingleCouples
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO
import es.lojo.randomgroup.ui.secretcouples.vo.model.SingleSecretCoupleVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val CLASS_NAME = "SecretCoupleViewModel"

class SecretCoupleViewModel : ViewModel() {
    // region attr
    private val gMailSender = GMailSender()
    private var couplesReordered: SecretCouplesVO? = null
    // endregion attr

    // region view states
    private val _viewState = MutableLiveData<SecretCoupleStates>()

    val viewState: LiveData<SecretCoupleStates> = _viewState
    private val _items = MutableLiveData<List<SingleSecretCoupleVO>>()

    val items: LiveData<List<SingleSecretCoupleVO>> = _items

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
        email = ""
    )


    private fun finish(sendEmail: Boolean) {
        couplesReordered ?: reorder()
        Log.d("TonyTest", "showCouples with $couplesReordered")
        couplesReordered?.let {
            setState(
                if (sendEmail) {
                    SecretCoupleStates.SendEmail(it)
                } else {
                    SecretCoupleStates.ShowCouples(it)
                }
            )
        }
    }

    private fun getModelEmailSenderModel(recipient: String, couple: String): EmailSenderModel {
        return EmailSenderModel(
            recipients = recipient,
            body = "Your secret couple is $couple",
            subject = "RANDOM GROUP secret couples"
        )
    }

    // reorder list
    private fun reorder() {
        _items.value?.takeIf { it.size % 2 == 0 }?.let { couples ->
            if (checkEmails(couples)) {
                couplesReordered = couples.reorderListOfSingleCouples()
            }
        } ?: setState(SecretCoupleStates.Error(SecretCoupleError.NOT_PAR))
    }

    private fun checkEmails(secretCouples: List<SingleSecretCoupleVO>): Boolean {
        return secretCouples.find { EmailHelper.checkEmail(it.email).not() }?.let {
            setState(
                SecretCoupleStates.Error(
                    SecretCoupleError.UNKNOWN.apply {
                        message = if (it.email.isEmpty()) {
                            EmailHelper.EMAIL_IS_EMPTY
                        } else {
                            "${it.email} ${EmailHelper.EMAIL_NOT_VALID_TEXT}"
                        }
                    }
                )
            )
            false
        } ?: true
    }
    // endregion private

    // region public
    fun addItem() {
        // If user update an item we remove private list
        couplesReordered = null
        val newItems = _items.value.clone()
        newItems.add(instanceNewSecretCoupleVO())
        _items.value = newItems
    }

    fun updateItem(item: SingleSecretCoupleVO) {
        // If user update an item we remove private list
        couplesReordered = null
        val newItems = _items.value.clone()
        newItems.toList().mapIndexed { index, currentItem ->
            if (currentItem.id == item.id) {
                newItems[index] = item
                _items.value = newItems
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

    fun showCouples() {
        finish(sendEmail = false)
    }

    fun sendEmail() {
        finish(sendEmail = true)
    }

    fun setUpSendEMail(secretCouples: SecretCouplesVO) {
        viewModelScope.launch(Dispatchers.IO) {
            secretCouples.couples.forEach {
                listOf(
                    getModelEmailSenderModel(
                        recipient = it.couple.first.email,
                        couple = it.couple.second.email
                    ),
                    getModelEmailSenderModel(
                        recipient = it.couple.second.email,
                        couple = it.couple.first.email
                    )
                ).forEach { emailSenderModel ->
                    if (gMailSender.sendMail(emailSenderModel) == EXPECTED.FAILURE) {
                        setState(SecretCoupleStates.Error(SecretCoupleError.EMAIL_COULD_NOT_BE_SENT))
                        return@launch
                    }
                }
            }
            setState(SecretCoupleStates.EmailSuccess())
        }
    }
    // endregion public
}
