package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar
import uk.ac.swansea.dascalu.dvmicc.home.model.ViewModel

class ChallengeConclusionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_conclusion)

        val appBar = findViewById<MaterialToolbar>(R.id.prizeActivityToolbar)
        title = resources.getString(R.string.challengeConclusion)
        setSupportActionBar(appBar)
        findViewById<TextView>(R.id.challengeConclusionTextView).setTextIsSelectable(true)

        val hasCompletedChallenge = ViewModel.instance.hasCompletedChallenge
        val explanationTextView = findViewById<TextView>(R.id.challengeConclusionTextView)

        if (hasCompletedChallenge) {
            val conclusionID = ViewModel.instance.challenge.scenarioConclusion
            if (conclusionID != 0) {
                explanationTextView.text = resources.getString(conclusionID)
            }
        } else {
            explanationTextView.text = resources.getString(R.string.prizeHidden)
        }
    }
}