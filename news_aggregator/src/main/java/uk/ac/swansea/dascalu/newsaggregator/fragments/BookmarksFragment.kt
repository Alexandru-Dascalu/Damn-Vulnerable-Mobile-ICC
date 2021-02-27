package uk.ac.swansea.dascalu.newsaggregator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.ac.swansea.dascalu.newsaggregator.model.Database
import uk.ac.swansea.dascalu.newsaggregator.R
import uk.ac.swansea.dascalu.newsaggregator.adapters.BookmarkCardAdapter

class BookmarksFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bookmarksRootView = inflater.inflate(R.layout.bookmarks_fragment_layout, container, false)

        return bookmarksRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.bookmark_recycler_view)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = BookmarkCardAdapter(Database.instance.getBookmarks(), this)
    }
}