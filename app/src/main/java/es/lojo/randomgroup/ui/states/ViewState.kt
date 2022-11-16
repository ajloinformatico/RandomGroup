package es.lojo.randomgroup.ui.states

import es.lojo.randomgroup.data.models.ConfigureOfPlayersModel
import es.lojo.randomgroup.data.models.ConfigurePlayersFinalModel

// region ConfigurePlayersViewState
sealed class ConfigurePlayersViewState {
    object Loading : ConfigurePlayersViewState()
    object Render : ConfigurePlayersViewState()
    object Unknown: ConfigurePlayersViewState()
    data class Error(val error: ConfigurePlayersErrors) : ConfigurePlayersViewState()
    data class OpenButtonSheetPlayersName(val configuration: ConfigureOfPlayersModel) :
        ConfigurePlayersViewState()
}

enum class ConfigurePlayersErrors(val message: String) {
    COMPETITION_NAME("Please check the competition name"),
    NUMBER_OF_PLAYERS("Please check number of players"),
    NUMBER_OF_PLAYERS_MINUS_THAN_1("We need almost two players"),
    NUMBER_OF_GROUPS_MORE_THAN_PLAYERS("Groups number is bigger than players WTF !!"),
    UNKNOWN("Please check all your inputs"),
}
// endregion

// region ConfigurePlayersNameGridViewState
sealed class ConfigurePlayersNameGridViewState {
    object Loading : ConfigurePlayersNameGridViewState()
    object Render : ConfigurePlayersNameGridViewState()
    object Error : ConfigurePlayersNameGridViewState()
    object Unknown: ConfigurePlayersNameGridViewState()
    data class Finish(val finalPlayersConfig: ConfigurePlayersFinalModel) :
        ConfigurePlayersNameGridViewState()
}

// TODO ERROR ENUM CLASS

// endregion

// region FinalPlayers
sealed class FinalPlayersConfigViewState {
    object Loading : FinalPlayersConfigViewState()
    object Render : FinalPlayersConfigViewState()
    object Error : FinalPlayersConfigViewState()
    object Unknown: FinalPlayersConfigViewState()
}
// endregion FinalPlayers