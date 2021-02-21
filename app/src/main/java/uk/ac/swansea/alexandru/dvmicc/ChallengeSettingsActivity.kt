package uk.ac.swansea.alexandru.dvmicc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar
import uk.ac.swansea.alexandru.dvmicc.model.Challenge

class ChallengeSettingsActivity :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_settings)

        val materialToolbar = findViewById<MaterialToolbar>(R.id.settingsActivityToolbar)
        setSupportActionBar(materialToolbar)

        title = resources.getString(R.string.challengeSettingsActivityTitle)
    }

    fun onApplySettings(view: View) {
        if(intent.extras != null) {
            val challenge : Challenge = intent.extras!!.get("challenge") as Challenge
            val launchedFromChallengeActivity : Boolean = intent.extras!!.get("launchedFromChallengeActivity") as Boolean

            if(!launchedFromChallengeActivity) {
                val intent = Intent(this, ChallengeActivity::class.java)
                intent.putExtra("challenge", challenge)
                startActivity(intent)
            } else {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
            }
        }

        applySettings()
        finish()
    }

    private fun applySettings() {

    }

}