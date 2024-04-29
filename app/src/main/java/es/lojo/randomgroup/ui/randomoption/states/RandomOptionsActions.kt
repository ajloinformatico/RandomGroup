package es.lojo.randomgroup.ui.randomoption.states

import es.lojo.randomgroup.ui.randomoption.vo.RandomOptionVO

sealed class RandomOptionsActions(
    open val option: RandomOptionVO
) {
    data class HideKeyBoard(
        override val option: RandomOptionVO
    ) : RandomOptionsActions(option = option)

    data class Update(
        override val option: RandomOptionVO
    ) : RandomOptionsActions(option = option)

    data class Remove(
        override val option: RandomOptionVO
    ) : RandomOptionsActions(option = option)
}
