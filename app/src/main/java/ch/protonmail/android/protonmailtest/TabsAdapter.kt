package ch.protonmail.android.protonmailtest

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by ProtonMail on 2/25/19. */
class TabsAdapter(val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return HottestFragment()
        }
        return UpcomingFragment()
    }

    override fun getCount(): Int {
        return 2
    }
}