package uk.ac.swansea.dascalu.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import uk.ac.swansea.dascalu.newsaggregator.model.Database
import uk.ac.swansea.dascalu.newsaggregator.R
import uk.ac.swansea.dascalu.newsaggregator.adapters.NewsStreamTabAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val homeRootView = inflater.inflate(R.layout.home_fragment, container, false)

        return homeRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.stream_pager)

        val newsStreams = Database.instance.getUserCustomStreamNames()
        newsStreams.add(requireContext().resources.getString(R.string.recommended))

        viewPager.adapter = NewsStreamTabAdapter(activity as AppCompatActivity, newsStreams)
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = newsStreams[position]
            }).attach()
    }
}