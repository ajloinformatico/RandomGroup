package es.lojo.randomgroup.commons.logger

import androidx.fragment.app.Fragment

private const val ACTIVITY_VALUE = "activity"
private const val FRAGMENT_VALUE = "fragment"
private const val FRAGMENT_DIALOG = "fragmentDialog"
private const val VIEW_HOLDER_VALUE = "viewHolder"
private const val VIEW_MODEL_VALUE = "viewModel"
private const val DIFF_UTILS_VALUE = "diffUtils"
private const val LIST_ADAPTER_VALUE = "listAdapter"
private const val OBJECT_VALUE = "Object"
private const val RANDOM_APP = "randomGroupApp"

enum class LoggerTypes(val value: String) {
    ACTIVITY(ACTIVITY_VALUE),
    FRAGMENT(FRAGMENT_VALUE),
    FRAGMENT_DIALOG(FRAGMENT_VALUE),
    VIEW_HOLDER(VIEW_HOLDER_VALUE),
    VIEW_MODEL(VIEW_MODEL_VALUE),
    DIFF_UTILS(DIFF_UTILS_VALUE),
    LIST_ADAPTER(LIST_ADAPTER_VALUE),
    OBJECT(OBJECT_VALUE),
    UNKNOWN(RANDOM_APP)
}
