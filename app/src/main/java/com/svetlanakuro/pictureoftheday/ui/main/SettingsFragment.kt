package com.svetlanakuro.pictureoftheday.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.domain.AppThemePreferenceDelegate
import org.jetbrains.annotations.NotNull

class SettingsFragment : Fragment() {

    private val appThemeSaved by lazy { AppThemePreferenceDelegate() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRadioButtons(view, requireActivity().applicationContext)
    }

    private fun initRadioButtons(
        @NotNull
        view: View, context: Context
    ) {
        val savedThemeNumber = appThemeSaved.getSavedTheme(context)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radio_buttons)

        when (savedThemeNumber) {
            1 -> radioGroup.check(radioGroup.getChildAt(0).id)
            2 -> radioGroup.check(radioGroup.getChildAt(1).id)
            else -> radioGroup.clearCheck()
        }

        view.findViewById<View>(R.id.radio_button_moon_theme).setOnClickListener {
            appThemeSaved.setMoonTheme(context)
            requireActivity().recreate()
        }
        view.findViewById<View>(R.id.radio_button_sun_theme).setOnClickListener {
            appThemeSaved.setSunTheme(context)
            requireActivity().recreate()
        }
    }
}