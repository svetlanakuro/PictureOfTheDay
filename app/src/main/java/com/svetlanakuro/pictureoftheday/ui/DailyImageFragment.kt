package com.svetlanakuro.pictureoftheday.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.*
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.*
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.domain.DailyImage

class DailyImageFragment : Fragment() {

    private val viewModel by viewModels<DailyImageViewModel>()

    private lateinit var dailyImageView: ImageView

    private lateinit var wikitextInputLayout: TextInputLayout
    private lateinit var wikitextEditText: TextInputEditText

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetDescriptionHeader: TextView
    private lateinit var bottomSheetDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getImageData().observe(this, { dailyImage -> renderData(dailyImage) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailyImageView = view.findViewById(R.id.image_view_daily_image)

        wikitextInputLayout = view.findViewById(R.id.input_layout_wiki)
        wikitextEditText = view.findViewById(R.id.input_edit_text_wiki)

        wikitextInputLayout.setEndIconOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://en.wikipedia.org/wiki/${wikitextEditText.text.toString()}"
            val uri = Uri.parse(url)
            intent.data = uri

            startActivity(intent)
        }

        bottomSheetDescriptionHeader = view.findViewById(R.id.text_view_bottom_sheet_description_header)
        bottomSheetDescription = view.findViewById(R.id.text_view_bottom_sheet_description)

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(dailyImage: DailyImage) {
        when (dailyImage) {
            is DailyImage.Success -> {
                val serverResponseData = dailyImage.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    Toast.makeText(activity, "Unable to display empty link", Toast.LENGTH_SHORT).show()
                } else {
                    dailyImageView.load(url) {
                        lifecycle(this@DailyImageFragment)
                        error(R.drawable.ic_image_error)
                        placeholder(R.drawable.ic_empty_image)
                    }
                    bottomSheetDescriptionHeader.text = serverResponseData.title
                    bottomSheetDescription.text = serverResponseData.explanation
                }
            }
            is DailyImage.Loading -> {
                Toast.makeText(activity, "Loading...", Toast.LENGTH_SHORT).show()
            }
            is DailyImage.Error -> {
                Toast.makeText(activity, "Unknown error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}