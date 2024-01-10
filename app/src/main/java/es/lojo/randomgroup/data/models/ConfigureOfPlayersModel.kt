package es.lojo.randomgroup.data.models

import java.io.Serializable

data class ConfigureOfPlayersModel(
    val competitionName: String,
    val numberOfPlayers: Int,
    val numberOfGroups: Int,
    val players: List<PlayerModel>
) : Serializable
