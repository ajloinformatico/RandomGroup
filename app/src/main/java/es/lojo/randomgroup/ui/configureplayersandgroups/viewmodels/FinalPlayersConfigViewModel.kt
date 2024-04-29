package es.lojo.randomgroup.ui.configureplayersandgroups.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.lojo.randomgroup.data.modelextensions.isTheSameGroup
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalModel
import es.lojo.randomgroup.ui.configureplayersandgroups.states.FinalPlayersConfigViewState

private const val ALL_WINNER_GROUPS_ARE_SELECTED = "All winner groups are selected"

class FinalPlayersConfigViewModel : ViewModel() {

    // view states
    private val _viewState = MutableLiveData<FinalPlayersConfigViewState>()
    val viewState: LiveData<FinalPlayersConfigViewState> = _viewState

    fun setState(value: FinalPlayersConfigViewState) {
        _viewState.postValue(value)
    }

    private val _finalConfig = MutableLiveData<ConfigurePlayersFinalModel>()
    val finalConfig: LiveData<ConfigurePlayersFinalModel> = _finalConfig

    fun setFinalConfigAtTheFirstTime(value: ConfigurePlayersFinalModel) {
        _finalConfig.postValue(reset(value))
    }

    private fun reset(value: ConfigurePlayersFinalModel): ConfigurePlayersFinalModel {
        val copyList = mutableListOf<ConfigurePlayersFinalGroupsModel>()
        value.groups.map {
            copyList.add(
                ConfigurePlayersFinalGroupsModel(
                    groupNumber = it.groupNumber,
                    playersName = it.playersName,
                    clicked = false
                )
            )
        }
        return ConfigurePlayersFinalModel(
            competitionName = value.competitionName,
            groups = copyList
        )
    }

    fun updateFinalConfig(group: ConfigurePlayersFinalGroupsModel) {
        val finalConfigCopy = _finalConfig.value?.groups.orEmpty().toMutableList()
        var getPosition = 0
        finalConfigCopy.forEachIndexed { index, item ->
            if (item.isTheSameGroup(group)) getPosition = index
        }
        finalConfigCopy[getPosition] = group

        if (checkCanUpdateClicked().not() || !group.clicked) {
            _finalConfig.value = ConfigurePlayersFinalModel(
                competitionName = _finalConfig.value?.competitionName.orEmpty(),
                groups = finalConfigCopy
            )
            checkToShowContinueButton()
        } else {
            showClickError()
        }
    }

    private fun checkCanUpdateClicked(): Boolean {
        val groupsClicked = _finalConfig.value?.groups.orEmpty().count { it.clicked }
        return groupsClicked == _finalConfig.value?.groups.orEmpty().size / 2
    }

    private fun checkToShowContinueButton() {
        _viewState.value = FinalPlayersConfigViewState.ShouldShowContinueButton(
            checkCanUpdateClicked()
        )
    }

    /** Show an error that indicate all possible groups are clicked*/
    private fun showClickError() {
        _viewState.value = FinalPlayersConfigViewState.CustomError(ALL_WINNER_GROUPS_ARE_SELECTED)
    }

    fun continueClicked() {
        if (checkCanUpdateClicked()) {
            _viewState.value = FinalPlayersConfigViewState.Finish(
                ConfigurePlayersFinalModel(
                    competitionName = _finalConfig.value?.competitionName.orEmpty(),
                    groups = _finalConfig.value?.groups.orEmpty().filter { it.clicked }
                )
            )
        }
    }

    init {
        _viewState.postValue(FinalPlayersConfigViewState.Loading)
    }
}
