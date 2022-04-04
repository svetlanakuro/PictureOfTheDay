package com.svetlanakuro.pictureoftheday.ui.gst

import android.os.Bundle
import android.text.*
import android.text.style.UnderlineSpan
import android.view.*
import androidx.fragment.app.*
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.databinding.FragmentGstBinding

class GstFragment : Fragment(R.layout.fragment_gst) {

    private var _binding: FragmentGstBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<GstViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spannableText = SpannableStringBuilder(binding.gstListDescriptionTextView.text)
        spannableText.setSpan(UnderlineSpan(), 18, 35, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.gstListDescriptionTextView.text = spannableText

        viewModel.onViewCreated(requireActivity().application)
        viewModel.setGstDataLiveData.observe(viewLifecycleOwner) { gstList ->
            binding.gstListTextView.text = gstList
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}