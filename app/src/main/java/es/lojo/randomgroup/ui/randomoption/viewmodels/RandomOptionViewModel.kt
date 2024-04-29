package es.lojo.randomgroup.ui.randomoption.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.commons.utils.generateCustomUniqueId
import es.lojo.randomgroup.ui.randomoption.states.RandomOptionViewState
import es.lojo.randomgroup.ui.randomoption.states.RandomOptionViewStateErrors
import es.lojo.randomgroup.ui.randomoption.vo.RandomOptionVO
import kotlinx.coroutines.launch

private const val CLASS_NAME = "RandomOptionViewModel"

class RandomOptionViewModel : ViewModel() {
    // region view states
    private val _viewState = MutableLiveData<RandomOptionViewState>()
    val viewState: LiveData<RandomOptionViewState> = _viewState

    private val _options = MutableLiveData<List<RandomOptionVO>>()
    val options: LiveData<List<RandomOptionVO>> = _options

    fun setState(value: RandomOptionViewState) {
        _viewState.postValue(value)
    }

    // endregion view states

    init {
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.VIEW_MODEL
        )
        viewModelScope.launch {
            _viewState.value = RandomOptionViewState.Loading
            initialConfig()
        }
    }

    // region private
    private fun List<RandomOptionVO>?.clone(): MutableList<RandomOptionVO> =
        _options.value.orEmpty().toList().toMutableList()

    private fun initOptionsLiveData(newOptions: List<RandomOptionVO>) {
        _options.value = newOptions
        setState(RandomOptionViewState.Render(newOptions))
    }

    private fun initialConfig() {
        val initialOptions = listOf(
            RandomOptionVO(
                id = generateCustomUniqueId()
            ),
            RandomOptionVO(
                id = generateCustomUniqueId()
            )
        )
        initOptionsLiveData(initialOptions)
    }

    // endregion private

    // region public
    fun addNewOption() {
        val newOptions = _options.value.clone()
        newOptions.add(
            RandomOptionVO(
                id = generateCustomUniqueId()
            )
        )
        _options.value = newOptions.toList()
    }

    fun updateOption(optionVO: RandomOptionVO) {
        val newOptions = _options.value.clone()
        newOptions.toList().mapIndexed { index, current ->
            if (current.id == optionVO.id) {
                newOptions[index] = optionVO
                _options.value = newOptions.toList()
                return
            }
        }
    }

    /** Remove [optionVO] from options list and reorder positions in list. */
    fun removeOption(optionVO: RandomOptionVO) {
        val newOptions = _options.value.clone()
        newOptions.remove(optionVO)
        _options.value = newOptions
    }

    fun getWinner() {
        _options.value.orEmpty().takeUnless {
            it.any { optionVO ->
                optionVO.text.isBlank()
            }
        }?.let {
            it.takeUnless { it.size < 2 }?.let { finalOptions ->
                _viewState.value = RandomOptionViewState.Continue(
                    finalOptions.random()
                )
            } ?: run {
                _viewState.value = RandomOptionViewState.Error(
                    RandomOptionViewStateErrors.ALMOST_TWO
                )
            }
        } ?: run {
            _viewState.value = RandomOptionViewState.Error(
                RandomOptionViewStateErrors.EMPTY_ON_CONTINUE
            )
        }
    }
    // endregion public
}
