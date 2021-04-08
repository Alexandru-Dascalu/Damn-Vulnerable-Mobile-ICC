package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class ChallengePrizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_prize)

        val appBar = findViewById<MaterialToolbar>(R.id.prizeActivityToolbar)
        title = "Challenge Prize"
        setSupportActionBar(appBar)

        if(intent.extras != null) {
            val hasCompletedApps = intent.extras!!.getBoolean("hasCompletedChallenge")
            val explanationTextView = findViewById<TextView>(R.id.challengePrizeTextView)

            if (hasCompletedApps) {
                val explanatioID = intent.extras!!.getInt("explanationID")
                if (explanatioID != 0) {
                    explanationTextView.text = resources.getString(explanatioID)
                }
            } else {
                explanationTextView.text = resources.getString(R.string.prizeHidden)
            }
        }
    }
}