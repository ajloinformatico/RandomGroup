package es.lojo.randomgroup.ui.configureplayersandgroups.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.lojo.randomgroup.commons.utils.calculatePlayersPerGroup
import es.lojo.randomgroup.data.models.ConfigureOfPlayersModel
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalModel
import es.lojo.randomgroup.data.models.PlayerModel
import es.lojo.randomgroup.ui.configureplayersandgroups.states.ConfigurePlayersNameErrors
import es.lojo.randomgroup.ui.configureplayersandgroups.states.ConfigurePlayersNameGridViewState

class ConfigurePlayersNameViewModel : ViewModel() {

    // view states
    private val _viewState = MutableLiveData<ConfigurePlayersNameGridViewState>()
    val viewState: LiveData<ConfigurePlayersNameGridViewState> = _viewState

    fun setState(value: ConfigurePlayersNameGridViewState) {
        _viewState.postValue(value)
    }

    // players object
    private val _playersConfig = MutableLiveData<ConfigureOfPlayersModel>()
    val playersConfig: LiveData<ConfigureOfPlayersModel> = _playersConfig

    fun setPlayers(value: ConfigureOfPlayersModel) {
        _playersConfig.value = value
    }

    // final config
    private val _finalConfig = MutableLiveData<List<String>>()

    init {
        _viewState.postValue(ConfigurePlayersNameGridViewState.Loading)
    }

    /**
     * Method collect before go to title screen
     */
    fun continueClick() {
        val playersForOneGroup: Int = calculatePlayersPerGroup(
            numberOfPlayers = _playersConfig.value?.numberOfPlayers ?: 0,
            numberOfGroups = _playersConfig.value?.numberOfGroups ?: 0
        )

        val groupsPrepared = mutableListOf<ConfigurePlayersFinalGroupsModel>()
        _finalConfig.value.orEmpty().shuffled().chunked(playersForOneGroup)
            .forEachIndexed { groupIndex, players ->
                groupsPrepared.add(
                    ConfigurePlayersFinalGroupsModel(
                        groupNumber = groupIndex + 1,
                        playersName = players
                    )
                )
            }
        if (groupsPrepared.isNotEmpty() && groupsPrepared.size == _playersConfig.value?.numberOfGroups) {
            _viewState.postValue(
                ConfigurePlayersNameGridViewState.Finish(
                    ConfigurePlayersFinalModel(
                        competitionName = _playersConfig.value?.competitionName.orEmpty(),
                        groups = groupsPrepared
                    )
                )
            )
        } else {
            _viewState.postValue(
                ConfigurePlayersNameGridViewState.Error(
                    ConfigurePlayersNameErrors.UNKNOWN
                )
            )
        }

    }

    /**
     * Add players or get all players
     */
    fun firstConfigOfPlayers() {
        val playersList = mutableListOf<PlayerModel>()
        if (_playersConfig.value?.players.isNullOrEmpty().not()) {
            playersList.clear()
            playersList.addAll(_playersConfig.value?.players.orEmpty())
        } else {
            playersList.clear()
            (1..(_playersConfig.value?.numberOfPlayers ?: 1)).forEachIndexed { index, _ ->
                playersList.add(PlayerModel("Player ${index + 1}", ""))
            }
        }
        _finalConfig.value = playersList.map { it.name }
        _playersConfig.postValue(
            ConfigureOfPlayersModel(
                competitionName = _playersConfig.value?.competitionName.orEmpty(),
                numberOfPlayers = _playersConfig.value?.numberOfPlayers ?: 1,
                numberOfGroups = _playersConfig.value?.numberOfGroups ?: 1,
                players = playersList
            )
        )
    }

    fun updatePlayersConfig(playerName: String, position: Int) {
        val finalConfigCopy = _finalConfig.value.orEmpty().toMutableList()
        finalConfigCopy[position] = playerName
        _finalConfig.value = finalConfigCopy
    }
}