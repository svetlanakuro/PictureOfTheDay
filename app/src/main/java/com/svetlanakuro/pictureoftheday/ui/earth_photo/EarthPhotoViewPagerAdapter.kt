package com.svetlanakuro.pictureoftheday.ui.earth_photo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.svetlanakuro.pictureoftheday.data.entity.EarthPhotoResponse

class EarthPhotoViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    lateinit var items: List<EarthPhotoResponse>

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        val item = items[position]
        return EarthPhotoPageFragment.newInstance(item.image, item.date)
    }
}