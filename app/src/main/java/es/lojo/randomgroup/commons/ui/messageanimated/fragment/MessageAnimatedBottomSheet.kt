package es.lojo.randomgroup.commons.ui.messageanimated.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.extensions.takeSerializable
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.commons.ui.messageanimated.vo.MessageAnimatedVO
import es.lojo.randomgroup.databinding.FragmentMessageAnimatedBinding

private const val CLASS_NAME = "MessageAnimatedBottomSheet"

class MessageAnimatedBottomSheet : BottomSheetDialogFragment() {

    private var binding: FragmentMessageAnimatedBinding? = null

    private val message: String by lazy {
        (arguments?.takeSerializable(MESSAGE_ANIMATED) as? MessageAnimatedVO)?.text.orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InfolojoLogger.log(
            ctx = CLASS_NAME,
            message = "init",
            suffix = LoggerTypes.FRAGMENT_DIALOG
        )
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageAnimatedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding?.text?.text = message
    }

    companion object {
        const val MESSAGE_ANIMATED = "MESSAGE_ANIMATED"

        fun newInstance(messageAnimatedVO: MessageAnimatedVO): MessageAnimatedBottomSheet =
            MessageAnimatedBottomSheet().apply {
                arguments = Bundle().apply {
                    putSerializable(MESSAGE_ANIMATED, messageAnimatedVO)
                }
            }
    }
}