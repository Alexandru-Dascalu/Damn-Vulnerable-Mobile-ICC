package uk.ac.swansea.alexandru.dvmicc.adapters.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

import uk.ac.swansea.alexandru.dvmicc.R

class ChallengeButtonAdapter(private val challengeList: Array<String>) : RecyclerView.Adapter<ChallengeButtonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val challengeButton = inflater.inflate(R.layout.challenge_button, parent, false)

        return ViewHolder(challengeButton)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.challengeButton.text = challengeList[position]
    }

    override fun getItemCount(): Int {
        return challengeList.size
    }

    inner class ViewHolder(challengeButtonView : View) : RecyclerView.ViewHolder(challengeButtonView) {
        val  challengeButton : MaterialButton = challengeButtonView.findViewById(R.id.challenge_button)
    }
}