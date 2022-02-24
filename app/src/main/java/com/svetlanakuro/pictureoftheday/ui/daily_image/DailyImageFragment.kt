package com.svetlanakuro.pictureoftheday.ui.daily_image

import android.os.Bundle
import android.view.*
import androidx.fragment.app.*
import com.bumptech.glide.Glide
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.data.entity.DailyImageResponse
import com.svetlanakuro.pictureoftheday.databinding.FragmentDailyImageBinding

class DailyImageFragment : Fragment(R.layout.fragment_daily_image) {

    private var _binding: FragmentDailyImageBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DailyImageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        viewModel.onViewIsReady(requireActivity().application)
    }

    private fun initViewModel() {
        viewModel.renderImageDataLiveData.observe(viewLifecycleOwner) { dailyImage ->
            renderImageData(dailyImage)
        }
    }

    private fun renderImageData(dailyImage: DailyImageResponse) {
        val imageUrl = dailyImage.url
        Glide.with(requireContext()).load(imageUrl).into(binding.dailyImageView)
        binding.mainToolbar.title = dailyImage.title
        binding.imageDescriptionTextView.text = dailyImage.explanation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}