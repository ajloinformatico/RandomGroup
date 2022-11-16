package es.lojo.randomgroup.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.lojo.randomgroup.commons.toIntCustom
import es.lojo.randomgroup.data.models.ConfigureOfPlayersModel
import es.lojo.randomgroup.ui.states.ConfigurePlayersErrors
import es.lojo.randomgroup.ui.states.ConfigurePlayersViewState

class ConfigurePlayersViewModel : ViewModel() {

    // view states
    private val _viewState = MutableLiveData<ConfigurePlayersViewState>()
    val viewState: LiveData<ConfigurePlayersViewState> = _viewState

    fun setState(value: ConfigurePlayersViewState) {
        _viewState.postValue(value)
    }

    // Lives data to check inputs
    private val _competitionName = MutableLiveData<String>()

    fun setCompetitionName(value: String) {
        _competitionName.postValue(value)
    }

    private val _numberOfPlayers = MutableLiveData<Int>()

    fun setNumberOfPlayers(value: Int) {
        _numberOfPlayers.postValue(value)
    }

    private val _numberOfGroups = MutableLiveData<Int>()

    fun setNumberOfGroups(value: Int) {
        _numberOfGroups.postValue(value)
    }

    init {
        setState(ConfigurePlayersViewState.Loading)
    }

    private fun getConfigurationsPlayer(): ConfigureOfPlayersModel =
        ConfigureOfPlayersModel(
            competitionName = _competitionName.value.orEmpty(),
            numberOfPlayers = _numberOfPlayers.value ?: 1,
            numberOfGroups = _numberOfGroups.value ?: 1,
            players = emptyList()
        )

    fun checkToContinue() {
        when {
            _competitionName.value.isNullOrBlank() &&
                    _numberOfPlayers.value.toString().toIntCustom() == 0 -> {
                _viewState.postValue(ConfigurePlayersViewState.Error(ConfigurePlayersErrors.UNKNOWN))

            }
            _competitionName.value.isNullOrBlank() ->
                _viewState.postValue(ConfigurePlayersViewState.Error(ConfigurePlayersErrors.COMPETITION_NAME))

            _numberOfPlayers.value.toString().toIntCustom() < 0 ->
                _viewState.postValue(ConfigurePlayersViewState.Error(ConfigurePlayersErrors.NUMBER_OF_PLAYERS))

            _numberOfPlayers.value.toString().toIntCustom() < 1 ->
                _viewState.postValue(ConfigurePlayersViewState.Error(ConfigurePlayersErrors.NUMBER_OF_PLAYERS_MINUS_THAN_1))

            _numberOfGroups.value.toString().toIntCustom() > _numberOfPlayers.value.toString()
                .toIntCustom() ->
                _viewState.postValue(ConfigurePlayersViewState.Error(ConfigurePlayersErrors.NUMBER_OF_GROUPS_MORE_THAN_PLAYERS))

            else -> _viewState.postValue(
                ConfigurePlayersViewState.OpenButtonSheetPlayersName(
                    getConfigurationsPlayer()
                )
            )
        }
    }
}
