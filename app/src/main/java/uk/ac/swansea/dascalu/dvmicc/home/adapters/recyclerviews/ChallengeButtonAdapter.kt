package uk.ac.swansea.dascalu.dvmicc.home.adapters.recyclerviews

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

import uk.ac.swansea.dascalu.dvmicc.home.ChallengeSettingsActivity
import uk.ac.swansea.dascalu.dvmicc.home.R
import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge

class ChallengeButtonAdapter(private val challengeList: Array<String>) : RecyclerView.Adapter<ChallengeButtonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val challengeButton = inflater.inflate(R.layout.challenge_button, parent, false)

        return ViewHolder(challengeButton)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(position) {
            0 -> holder.challenge = Challenge.BROADCAST_THEFT
            1 -> holder.challenge = Challenge.BROADCAST_THEFT_DOS
            2 -> holder.challenge = Challenge.BROADCAST_THEFT_MITM
            3 -> holder.challenge = Challenge.ACTIVITY_INTENT_HIJACK
        }
        holder.challengeButton.text = challengeList[position]
    }

    override fun getItemCount(): Int {
        return challengeList.size
    }

    inner class ViewHolder(challengeButtonView : View) : RecyclerView.ViewHolder(challengeButtonView) {
        val challengeButton : MaterialButton = challengeButtonView.findViewById(R.id.challenge_button)
        lateinit var challenge : Challenge

        init {
            challengeButton.setOnClickListener { item ->
                val intent = Intent(item.context, ChallengeSettingsActivity::class.java)
                intent.putExtra("challenge", challenge)
                intent.putExtra("launchedFromChallengeActivity", false)
                
                item.context.startActivity(intent)
            }
        }
    }
}