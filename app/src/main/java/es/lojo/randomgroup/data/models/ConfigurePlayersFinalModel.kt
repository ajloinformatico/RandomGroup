package es.lojo.randomgroup.data.models

import java.io.Serializable


data class ConfigurePlayersFinalModel(
    val competitionName: String,
    val groups: List<ConfigurePlayersFinalGroupsModel>,
) : Serializable
