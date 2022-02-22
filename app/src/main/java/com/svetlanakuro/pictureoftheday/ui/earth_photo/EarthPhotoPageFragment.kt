package com.svetlanakuro.pictureoftheday.ui.earth_photo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.databinding.FragmentEarthPhotoPageBinding

private const val IMAGE_LINK_PART_KEY = "IMAGE_LINK_PART_KEY"
private const val DATE_KEY = "DATE_KEY"

class EarthPhotoPageFragment : Fragment(R.layout.fragment_earth_photo_page) {

    private var _binding: FragmentEarthPhotoPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthPhotoPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { args ->
            val imageLinkPart = args.getString(IMAGE_LINK_PART_KEY)
                ?: throw IllegalStateException("Image link should be provided")
            val date = args.getString(DATE_KEY)
            setImage(imageLinkPart)
            binding.earthPhotoDateTextView.text = date
        }
    }

    private fun setImage(imageLinkPart: String) {
        val photoUrl = getEarthPhotoUrl(imageLinkPart)
        Glide.with(requireContext()).load(photoUrl).into(binding.earthPhotoImageView)
    }

    private fun getEarthPhotoUrl(imageLinkPart: String): String {
        val datePart = imageLinkPart.split("_")[2]
        val year = datePart.substring(0, 4)
        val month = datePart.substring(4, 6)
        val day = datePart.substring(6, 8)
        return "https://epic.gsfc.nasa.gov/archive/natural/$year/$month/$day/png/$imageLinkPart.png"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(imageLinkPart: String, date: String): EarthPhotoPageFragment {
            val args = Bundle().apply {
                putString(IMAGE_LINK_PART_KEY, imageLinkPart)
                putString(DATE_KEY, date)
            }
            val fragment = EarthPhotoPageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}