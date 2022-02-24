package com.svetlanakuro.pictureoftheday.ui.earth_photo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.*
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.data.entity.EarthPhotoResponse
import com.svetlanakuro.pictureoftheday.databinding.FragmentEarthPhotoBinding

class EarthPhotoFragment : Fragment(R.layout.fragment_earth_photo) {

    private val viewModel by viewModels<EarthPhotoViewModel>()

    private var _binding: FragmentEarthPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        viewModel.onViewIsReady(requireActivity().application)
    }

    private fun initViewModel() {
        viewModel.initViewPagerLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { earthPhotos ->
                initViewPager(earthPhotos)
            }
        }
    }

    private fun initViewPager(earthPhotos: List<EarthPhotoResponse>) {
        val viewPagerAdapter = EarthPhotoViewPagerAdapter(this)
        viewPagerAdapter.items = earthPhotos
        binding.earthPhotosViewPager.adapter = viewPagerAdapter
        binding.earthPhotosViewPagerCircleIndicator.setViewPager(binding.earthPhotosViewPager)
        binding.earthPhotosViewPager.offscreenPageLimit = 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}