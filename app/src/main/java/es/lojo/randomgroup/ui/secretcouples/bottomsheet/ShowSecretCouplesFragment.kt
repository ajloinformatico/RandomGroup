package es.lojo.randomgroup.ui.secretcouples.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import es.lojo.randomgroup.commons.extensions.takeSerializable
import es.lojo.randomgroup.databinding.FragmentShowSecretCouplesListDialogBinding
import es.lojo.randomgroup.ui.secretcouples.vo.model.SecretCouplesVO

class ShowSecretCouplesFragment : BottomSheetDialogFragment() {

    private var binding: FragmentShowSecretCouplesListDialogBinding? = null
    private val secretCouplesVO: SecretCouplesVO? by lazy {
        arguments?.takeSerializable(SECRET_COUPLES)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowSecretCouplesListDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {

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