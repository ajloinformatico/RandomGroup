package es.lojo.randomgroup.data.models
import java.io.Serializable


data class ConfigurePlayersFinalModel(
    val groups: List<ConfigurePlayersFinalGroupsModel>,
): Serializable

data class ConfigurePlayersFinalGroupsModel(
    val groupNumber: Int,
    val playersName: List<String>
): Serializable
