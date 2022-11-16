package es.lojo.randomgroup.data.models

import java.io.Serializable

data class ConfigureOfPlayersModel(
    val competitionName: String,
    val numberOfPlayers: Int,
    val numberOfGroups: Int,
    val players: List<PlayerModel>
): Serializable

/**
 * Player model used in the app
 * the string group value is represent by "group 1", "group 2", "group 3" ...
 */
data class PlayerModel(
    val name: String,
    val group: String
): Serializable
