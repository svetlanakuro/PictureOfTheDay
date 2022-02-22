package com.svetlanakuro.pictureoftheday.ui.daily_image

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.*
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.data.entity.DailyImageResponse
import com.svetlanakuro.pictureoftheday.databinding.FragmentDailyImageBinding

class DailyImageFragment : Fragment(R.layout.fragment_daily_image) {

    private var _binding: FragmentDailyImageBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DailyImageViewModel>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
        viewModel.onViewIsReady(requireActivity().application)
    }

    private fun initViewModel() {
        viewModel.renderImageDataLiveData.observe(viewLifecycleOwner) { dailyImage ->
            renderImageData(dailyImage)
        }
        viewModel.bottomSheetStateLiveData.observe(viewLifecycleOwner) { currentState ->
            bottomSheetBehavior.state = currentState
        }
    }

    private fun initViews() {
        initBottomSheet()
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheetContainer)
        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                viewModel.onBottomSheetStateChanged(newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //do nothing
            }
        }
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    private fun renderImageData(dailyImage: DailyImageResponse) {
        val imageUrl = dailyImage.url
        Glide.with(requireContext()).load(imageUrl).into(binding.dailyImageView)
        binding.bottomSheet.titleTextView.text = dailyImage.title
        binding.bottomSheet.copyrightTextView.text = dailyImage.copyright
        binding.bottomSheet.imageDescriptionTextView.text = dailyImage.explanation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}