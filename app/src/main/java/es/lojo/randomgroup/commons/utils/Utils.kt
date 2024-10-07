package es.lojo.randomgroup.commons.utils

import kotlin.random.Random

/**
 * Calculate players for one group
 */
fun calculatePlayersPerGroup(numberOfPlayers: Int, numberOfGroups: Int): Int {
    val playerPerGroup: Double = numberOfPlayers.toDouble() / numberOfGroups.toDouble()
    val dec: Float =
        playerPerGroup.toString().substring(playerPerGroup.toString().indexOf(".")).toFloat()
    return playerPerGroup.toInt() + if (dec >= .5f && dec < 1f) 1 else 0
}

/**
 * Generate unique id
 */
fun generateCustomUniqueId(): String {
    val timestamp = System.currentTimeMillis()
    val randomValue = Random.nextLong()
    val result = "${timestamp}_${randomValue}"
    return result
}
