package uk.ac.swansea.dascalu.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.ac.swansea.dascalu.newsaggregator.R
import uk.ac.swansea.dascalu.newsaggregator.adapters.KeywordButtonAdapter

class CustomiseKeywordFragment(private val streamName: String) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val customiseRootView = inflater.inflate(R.layout.customise_keywords_fragment, container, false)

        return customiseRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.keyword_buttons_recycler_view)

        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.adapter = KeywordButtonAdapter(streamName, this)
    }
}