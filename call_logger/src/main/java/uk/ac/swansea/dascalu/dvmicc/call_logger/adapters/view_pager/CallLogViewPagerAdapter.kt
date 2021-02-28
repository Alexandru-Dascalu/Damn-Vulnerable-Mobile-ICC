package uk.ac.swansea.dascalu.dvmicc.call_logger.adapters.view_pager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uk.ac.swansea.dascalu.dvmicc.call_logger.fragments.LogFragment

class CallLogViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return LogFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}