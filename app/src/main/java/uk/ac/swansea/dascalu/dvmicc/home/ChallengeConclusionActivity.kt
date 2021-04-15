package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar

class ChallengeConclusionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_conclusion)

        val appBar = findViewById<MaterialToolbar>(R.id.prizeActivityToolbar)
        title = resources.getString(R.string.challengeConclusion)
        setSupportActionBar(appBar)
        findViewById<TextView>(R.id.challengeConclusionTextView).setTextIsSelectable(true)

        if(intent.extras != null) {
            val hasCompletedApps = intent.extras!!.getBoolean("hasCompletedChallenge")
            val explanationTextView = findViewById<TextView>(R.id.challengeConclusionTextView)

            if (hasCompletedApps) {
                val challengeCommentID = intent.extras!!.getInt("explanationID")
                if (challengeCommentID != 0) {
                    explanationTextView.text = resources.getString(challengeCommentID)
                }
            } else {
                explanationTextView.text = resources.getString(R.string.prizeHidden)
            }
        }
    }
}