package es.lojo.randomgroup.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalModel
import es.lojo.randomgroup.data.models.isTheSameGroup
import es.lojo.randomgroup.ui.states.FinalPlayersConfigViewState

class FinalPlayersConfigViewModel : ViewModel() {

    // view states
    private val _viewState = MutableLiveData<FinalPlayersConfigViewState>()
    val viewState: LiveData<FinalPlayersConfigViewState> = _viewState

    fun setState(value: FinalPlayersConfigViewState) {
        _viewState.postValue(value)
    }

    private val _finalConfig = MutableLiveData<ConfigurePlayersFinalModel>()
    val finalConfig: LiveData<ConfigurePlayersFinalModel> = _finalConfig

    fun setFinalConfig(value: ConfigurePlayersFinalModel) {
        _finalConfig.postValue(value)
    }

    fun updateFinalConfig(group: ConfigurePlayersFinalGroupsModel) {
        val finalConfigCopy = _finalConfig.value?.groups.orEmpty().toMutableList()
        var getPosition = 0
        finalConfigCopy.forEachIndexed { index, item ->
            if (item.isTheSameGroup(group)) getPosition = index
        }
        finalConfigCopy[getPosition] = group
        _finalConfig.value = ConfigurePlayersFinalModel(
            groups = finalConfigCopy
        )
    }

    init {
        _viewState.postValue(FinalPlayersConfigViewState.Loading)
    }
}