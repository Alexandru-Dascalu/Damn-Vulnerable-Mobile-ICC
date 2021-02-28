package uk.ac.swansea.dascalu.dvmicc.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import uk.ac.swansea.dascalu.dvmicc.home.R
import uk.ac.swansea.dascalu.dvmicc.home.adapters.recyclerviews.ChallengeButtonAdapter

class ChallengesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val challengesRootView = inflater.inflate(R.layout.challenges_fragment, container, false)

        return challengesRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.challenges_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ChallengeButtonAdapter(resources.getStringArray(R.array.challenges))
    }
}