package com.svetlanakuro.pictureoftheday.domain

import com.svetlanakuro.pictureoftheday.R

enum class Screens(val menuId: Int) {
    DAILY_IMAGE(R.id.daily_image_fragment_menu),
    EARTH_PHOTO(R.id.earth_photo_fragment_menu),
    GST_INFO(
    R.id.geomagnetic_storm_fragment_menu
);

    companion object {

        fun getById(id: Int): Screens {
            for (screen in values()) {
                if (screen.menuId == id) {
                    return screen
                }
            }
            throw IllegalStateException("No such menu id")
        }
    }
}