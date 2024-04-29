package es.lojo.randomgroup.ui.randomoption.states

import es.lojo.randomgroup.ui.randomoption.vo.RandomOptionVO

private const val UNKNOWN_ERROR_TEXT = "Something was wrong"
private const val EMPTY_ON_CONTINUE_ERROR_TEXT =  "There are one or more options empty"
private const val ALMOST_TWO_ERROR_TEXT = "We need almost two options"

sealed interface RandomOptionViewState {
    object Loading : RandomOptionViewState
    data class Render(
        val options: List<RandomOptionVO>
    ) : RandomOptionViewState
    data class Error(
        val error: RandomOptionViewStateErrors = RandomOptionViewStateErrors.UNKNOWN
    ) : RandomOptionViewState
        data class Continue(
        val winner: RandomOptionVO
    ) : RandomOptionViewState
}

enum class RandomOptionViewStateErrors(val message: String) {
    UNKNOWN(message = UNKNOWN_ERROR_TEXT),
    EMPTY_ON_CONTINUE(message = EMPTY_ON_CONTINUE_ERROR_TEXT),
    ALMOST_TWO(message = ALMOST_TWO_ERROR_TEXT)
}
