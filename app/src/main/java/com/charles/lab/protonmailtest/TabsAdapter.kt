package com.charles.lab.protonmailtest

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by ProtonMail on 2/25/19. */
class TabsAdapter(val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return HottestFragment()
        }
        return UpcomingFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) context.getString(R.string.hottest) else context.getString(R.string.upcoming)
    }

    override fun getCount(): Int {
        return 2
    }
}