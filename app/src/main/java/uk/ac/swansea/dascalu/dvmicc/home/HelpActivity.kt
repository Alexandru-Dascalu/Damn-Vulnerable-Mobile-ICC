package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar
import io.github.kbiakov.codeview.CodeView

import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge
import uk.ac.swansea.dascalu.dvmicc.home.model.SecurityLevel

import java.lang.IllegalStateException

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.extras != null) {
            val hasGuessedApps: Boolean = intent.extras!!.getBoolean("hasGuessedApps")

            if(true) {
                val challenge: Challenge = intent.extras!!.getSerializable("challenge") as Challenge

                setContentView(R.layout.activity_help)
                setSupportActionBar(findViewById<MaterialToolbar>(R.id.helpActivityToolbar))

                setupLowLevel(challenge)
                setupMediumLevel(challenge)
                setupHighLevel(challenge)
                setupVeryHighLevel(challenge)
                setupImpossibleLevel(challenge)
            } else {
                setContentView(R.layout.activity_help_locked)
                setSupportActionBar(findViewById<MaterialToolbar>(R.id.lockedHelpActivityToolbar))
            }

            title = getString(R.string.helpActivityTitle)
        } else {
            throw IllegalStateException("No extras in intent that started help activity!")
        }
    }

    private fun setupLowLevel(challenge: Challenge) {
        setupSecurityLevel(R.id.securityLowDescription, R.id.securityLowManifest,
                R.id.securityLowIntentCode, R.id.securityLowHeader, challenge.securityLevels["low"])
    }

    private fun setupMediumLevel(challenge: Challenge) {
        setupSecurityLevel(R.id.securityMediumDescription, R.id.securityMediumManifest,
                R.id.securityMediumIntentCode, R.id.securityMediumHeader, challenge.securityLevels["medium"])
    }

    private fun setupHighLevel(challenge: Challenge) {
        setupSecurityLevel(R.id.securityHighDescription, R.id.securityHighManifest,
                R.id.securityHighIntentCode, R.id.securityHighHeader, challenge.securityLevels["high"])
    }

    private fun setupVeryHighLevel(challenge: Challenge) {
        setupSecurityLevel(R.id.securityVeryHighDescription, R.id.securityVeryHighManifest,
                R.id.securityVeryHighIntentCode, R.id.securityVeryHighHeader, challenge.securityLevels["high"])
    }

    private fun setupImpossibleLevel(challenge: Challenge) {
        setupSecurityLevel(R.id.securityImpossibleDescription, R.id.securityImpossibleManifest,
                R.id.securityImpossibleIntentCode, R.id.securityImpossibleHeader, challenge.securityLevels["impossible"])
    }

    private fun setupSecurityLevel(textViewID: Int, manifestID: Int, intentCodeID: Int,
                                   headerTextViewID: Int, securityLevel: SecurityLevel?) {
        if(securityLevel != null) {
            val levelDescriptionTextView = findViewById<TextView>(textViewID)
            levelDescriptionTextView.text = resources.getString(securityLevel.explanationID)

            val securityImpossibleManifest = findViewById<CodeView>(manifestID)
            setCodeViewOptions(this, securityImpossibleManifest, "xml",
                    securityLevel.manifestID)

            val securityImpossibleIntentCode = findViewById<CodeView>(intentCodeID)
            setCodeViewOptions(this, securityImpossibleIntentCode, "java",
                    securityLevel.intentCodeID)
        } else {
            findViewById<TextView>(headerTextViewID).visibility = View.GONE
            findViewById<TextView>(textViewID).visibility = View.GONE
            findViewById<CodeView>(manifestID).visibility = View.GONE
            findViewById<CodeView>(intentCodeID).visibility = View.GONE
        }
    }
}