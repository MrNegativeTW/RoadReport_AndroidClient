package com.txwstudio.app.roadreport.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.txwstudio.app.roadreport.ui.LocalStoreFragment
import com.txwstudio.app.roadreport.ui.livecam.LiveCamFragment
import com.txwstudio.app.roadreport.ui.roadevent.RoadEventFragment
import com.txwstudio.app.roadreport.ui.weather.WeatherFragment

const val ROAD_EVENT_INDEX = 0
const val WEATHER_INDEX = 1
const val LIVE_CAM_INDEX = 2
const val LOCAL_STORE_INDEX = 3

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        ROAD_EVENT_INDEX to { RoadEventFragment() },
        WEATHER_INDEX to { WeatherFragment() },
        LIVE_CAM_INDEX to { LiveCamFragment() },
        LOCAL_STORE_INDEX to { LocalStoreFragment() }
    )

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    override fun getItemCount(): Int {
        return tabFragmentsCreators.size
    }
}