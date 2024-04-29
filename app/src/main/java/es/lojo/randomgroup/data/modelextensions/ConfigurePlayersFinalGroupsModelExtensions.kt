package es.lojo.randomgroup.data.modelextensions

import es.lojo.randomgroup.data.models.ConfigurePlayersFinalGroupsModel

fun ConfigurePlayersFinalGroupsModel.isTheSameGroup(other: ConfigurePlayersFinalGroupsModel) =
    this.groupNumber == other.groupNumber && this.playersName == other.playersName
