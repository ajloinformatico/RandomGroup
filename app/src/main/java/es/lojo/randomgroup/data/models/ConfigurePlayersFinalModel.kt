package es.lojo.randomgroup.data.models
import java.io.Serializable


data class ConfigurePlayersFinalModel(
    val groups: List<ConfigurePlayersFinalGroupsModel>,
): Serializable

/**
 * Clicked is used to see if group is winner in his round or not
 */
data class ConfigurePlayersFinalGroupsModel(
    val groupNumber: Int,
    val playersName: List<String>,
    val clicked: Boolean = false
): Serializable
