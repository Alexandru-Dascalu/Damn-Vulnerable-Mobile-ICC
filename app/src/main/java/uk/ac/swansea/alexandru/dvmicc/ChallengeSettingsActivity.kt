package uk.ac.swansea.alexandru.dvmicc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar

class ChallengeSettingsActivity :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_settings)

        val materialToolbar = findViewById<MaterialToolbar>(R.id.settingsActivityToolbar)
        setSupportActionBar(materialToolbar)

        title = resources.getString(R.string.challengeSettingsActivityTitle)
    }

    fun onApplySettings(view: View) {
        finish()
    }

}