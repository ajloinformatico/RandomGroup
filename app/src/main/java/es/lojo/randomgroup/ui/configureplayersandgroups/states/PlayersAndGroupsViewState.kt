package es.lojo.randomgroup.ui.configureplayersandgroups.states

import es.lojo.randomgroup.R
import es.lojo.randomgroup.data.models.ConfigureOfPlayersModel
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalModel

// region ConfigurePlayersViewState
sealed class ConfigurePlayersViewState {
    object Loading : ConfigurePlayersViewState()
    object Render : ConfigurePlayersViewState()
    object Unknown : ConfigurePlayersViewState()
    data class Error(val error: ConfigurePlayersErrors) : ConfigurePlayersViewState()
    data class OpenButtonSheetPlayersName(val configuration: ConfigureOfPlayersModel) :
        ConfigurePlayersViewState()
}

enum class ConfigurePlayersErrors(val resource: Int) {
    COMPETITION_NAME(R.string.error_competition_name),
    NUMBER_OF_PLAYERS(R.string.error_number_of_players),
    NUMBER_OF_PLAYERS_MINUS_THAN_1(R.string.error_number_of_players_minus_than_1),
    NUMBER_OF_GROUPS_MORE_THAN_PLAYERS(R.string.error_number_of_groups_are_bigger_than_players),
    UNKNOWN(R.string.error_unknown),
}
// endregion

// region ConfigurePlayersNameGridViewState
sealed class ConfigurePlayersNameGridViewState {
    object Loading : ConfigurePlayersNameGridViewState()
    object Render : ConfigurePlayersNameGridViewState()
    data class Error(val error: ConfigurePlayersNameErrors) : ConfigurePlayersNameGridViewState()
    object Unknown : ConfigurePlayersNameGridViewState()
    data class Finish(val finalPlayersConfig: ConfigurePlayersFinalModel) :
        ConfigurePlayersNameGridViewState()
}

enum class ConfigurePlayersNameErrors(val message: String) {
    UNKNOWN("Please check all your inputs")
}

// Class that is passed to the player holder to get name and position after edit it
data class PlayerUpdate(val name: String, val position: Int)

// TODO ERROR ENUM CLASS

// endregion

// region FinalPlayers
sealed class FinalPlayersConfigViewState {
    object Loading : FinalPlayersConfigViewState()
    object Render : FinalPlayersConfigViewState()
    object Error : FinalPlayersConfigViewState()
    object Unknown : FinalPlayersConfigViewState()
    data class Finish(val finalPlayersConfig: ConfigurePlayersFinalModel) :
        FinalPlayersConfigViewState()

    data class ShouldShowContinueButton(
        val show: Boolean = false
    ) : FinalPlayersConfigViewState()

    data class CustomError(
        val message: String
    ) : FinalPlayersConfigViewState()
}

data class FinalPlayerConfigState(
    val group: ConfigurePlayersFinalGroupsModel
)
// endregion FinalPlayers
