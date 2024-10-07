package es.lojo.randomgroup.ui.secretcouples.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import es.lojo.randomgroup.R
import es.lojo.randomgroup.commons.extensions.takeSerializable
import es.lojo.randomgroup.commons.logger.InfolojoLogger
import es.lojo.randomgroup.commons.logger.LoggerTypes
import es.lojo.randomgroup.databinding.FragmentShowSecretCouplesListDialogBinding
import es.lojo.randomgroup.ui.secretcouples.adapters.showsecretcouples.ShowSecretCouplesAdapter
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO

private const val CLASS_NAME = "ShowSecretCouplesFragment"

class ShowSecretCouplesFragment : BottomSheetDialogFragment() {

    private var binding: FragmentShowSecretCouplesListDialogBinding? = null
    private val adapter = ShowSecretCouplesAdapter()
    private val secretCouplesVO: SecretCouplesVO? by lazy {
        arguments?.takeSerializable(SECRET_COUPLES)
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowSecretCouplesListDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
    }

    private fun initAdapter() {
        binding?.list?.adapter = adapter
        secretCouplesVO?.couples?.let(adapter::submitList) ?: onDestroyView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val SECRET_COUPLES = "SECRET_COUPLES"

        fun newInstance(secretCouplesVO: SecretCouplesVO): ShowSecretCouplesFragment =
            ShowSecretCouplesFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SECRET_COUPLES, secretCouplesVO)
                }
            }
    }
}
