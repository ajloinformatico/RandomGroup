package es.lojo.randomgroup.ui.secretcouples.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.ui.secretcouples.states.FinalSecretCouplesStates
import es.lojo.randomgroup.ui.secretcouples.vo.extensions.reorderCouples
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO

private const val CLASS_NAME = "FinalSecretCoupleViewModel"

class FinalSecretCoupleViewModel : ViewModel() {
    // region view states
    private val _viewState = MutableLiveData<FinalSecretCouplesStates>()
    val viewState: LiveData<FinalSecretCouplesStates> = _viewState

    private val _secretCouplesVO = MutableLiveData<SecretCouplesVO>()
    val secretCouplesVO: LiveData<SecretCouplesVO> = _secretCouplesVO
    // endregion view states

    // region init
    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.VIEW_MODEL
        )
        _viewState.value = FinalSecretCouplesStates.Loading
    }
    // endregion init

    private fun render() {
        secretCouplesVO.value?.let { couplesVO ->
            _viewState.value = FinalSecretCouplesStates.Render(couplesVO)
        }
    }

    // region public
    fun addCouples(couplesVO: SecretCouplesVO) {
        _secretCouplesVO.value = couplesVO
        render()
    }

    /**
     * Reorder the couples and update recyclerView
     */
    fun reorderCouples() {
        _secretCouplesVO.value = _secretCouplesVO.value?.reorderCouples()
        render()
    }

    /**
     * Send email
     */
    fun sendEmail() {
        // TODO implement sendEmail
    }
    // endregion public
}
