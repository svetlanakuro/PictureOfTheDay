package com.svetlanakuro.pictureoftheday.ui.daily_image

import android.os.Bundle
import android.transition.*
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.*
import com.bumptech.glide.Glide
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.data.entity.DailyImageResponse
import com.svetlanakuro.pictureoftheday.databinding.FragmentDailyImageStartBinding

class DailyImageFragment : Fragment(R.layout.fragment_daily_image_start) {

    private var _binding: FragmentDailyImageStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DailyImageViewModel>()

    private var show = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyImageStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dailyImageViewLoading.visibility = View.VISIBLE
        initViewModel()
        viewModel.onViewIsReady(requireActivity().application)
        binding.constraintContainer.setOnClickListener { if (show) hideComponents() else showComponents() }
    }

    private fun initViewModel() {
        viewModel.renderImageDataLiveData.observe(viewLifecycleOwner) { dailyImage ->
            renderImageData(dailyImage)
        }
    }

    private fun renderImageData(dailyImage: DailyImageResponse) {
        binding.dailyImageViewLoading.visibility = View.GONE
        val imageUrl = dailyImage.url
        Glide.with(requireContext()).load(imageUrl).into(binding.dailyImageView)
        binding.title.text = dailyImage.title
        binding.imageDescriptionTextView.text = dailyImage.explanation
    }

    private fun showComponents() {
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(this@DailyImageFragment.context, R.layout.fragment_daily_image_end)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
    }

    private fun hideComponents() {
        show = false

        val constraintSet = ConstraintSet()
        constraintSet.clone(this@DailyImageFragment.context, R.layout.fragment_daily_image_start)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}