package es.lojo.randomgroup.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.lojo.randomgroup.commons.toIntOrElse
import es.lojo.randomgroup.data.models.ConfigureOfPlayersModel
import es.lojo.randomgroup.ui.states.ConfigurePlayersErrors
import es.lojo.randomgroup.ui.states.ConfigurePlayersViewState

private const val RANDOM_CHAMPIONS = "Random Champions"

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
            competitionName = _competitionName.value.orEmpty().takeIf { it.isNotEmpty() } ?: RANDOM_CHAMPIONS,
            numberOfPlayers = _numberOfPlayers.value ?: 1,
            numberOfGroups = _numberOfGroups.value ?: 1,
            players = emptyList()
        )

    /**
     * Check all input before continue
     */
    fun checkToContinue() {
        when {
            _numberOfPlayers.value.toString().toIntOrElse() < 0 ->
                _viewState.postValue(ConfigurePlayersViewState.Error(ConfigurePlayersErrors.NUMBER_OF_PLAYERS))

            _numberOfPlayers.value.toString().toIntOrElse() < 1 ->
                _viewState.postValue(ConfigurePlayersViewState.Error(ConfigurePlayersErrors.NUMBER_OF_PLAYERS_MINUS_THAN_1))

            _numberOfGroups.value.toString().toIntOrElse() > _numberOfPlayers.value.toString()
                .toIntOrElse() ->
                _viewState.postValue(ConfigurePlayersViewState.Error(ConfigurePlayersErrors.NUMBER_OF_GROUPS_MORE_THAN_PLAYERS))

            else -> _viewState.postValue(
                ConfigurePlayersViewState.OpenButtonSheetPlayersName(
                    getConfigurationsPlayer()
                )
            )
        }
    }
}
