package es.lojo.randomgroup.commons.utils

/**
 * Calculate players for one group
 */
fun calculatePlayersPerGroup(numberOfPlayers: Int, numberOfGroups: Int): Int {
    val playerPerGroup: Double = numberOfPlayers.toDouble() / numberOfGroups.toDouble()
    val dec: Float =
        playerPerGroup.toString().substring(playerPerGroup.toString().indexOf(".")).toFloat()
    return playerPerGroup.toInt() + if (dec >= .5f && dec < 1f) 1 else 0
}
