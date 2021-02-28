package uk.ac.swansea.dascalu.dvmicc.newsaggregator.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.fragments.NewsStreamFragment

class NewsStreamTabAdapter (activity: AppCompatActivity, private val streams: List<String>) : FragmentStateAdapter(activity) {
    private val streamFragments: MutableList<NewsStreamFragment> = mutableListOf<NewsStreamFragment>()

    init {
        for(streamName in streams) {
            streamFragments.add(NewsStreamFragment(streamName))
        }
    }

    override fun createFragment(position: Int): Fragment {
        return streamFragments[position]
    }

    override fun getItemCount(): Int {
        return streamFragments.size
    }
}