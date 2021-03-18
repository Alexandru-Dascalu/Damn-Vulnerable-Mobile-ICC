package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import io.github.kbiakov.codeview.CodeView
import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge
import java.lang.IllegalStateException

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.extras != null) {
            val hasGuessedApps: Boolean = intent.extras!!.getBoolean("hasGuessedApps")

            if(hasGuessedApps) {
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
        if(challenge.securityLevels["low"] != null) {
            val securityLowTextView = findViewById<TextView>(R.id.securityLowDescription)
            securityLowTextView.movementMethod = LinkMovementMethod.getInstance()
            securityLowTextView.text = resources.getString(challenge.securityLevels["low"]!!.explanationID)

            val securityLowManifest = findViewById<CodeView>(R.id.securityLowManifest)
            setCodeViewOptions(this, securityLowManifest, "xml", challenge.securityLevels["low"]!!.manifestID)

            val securityLowIntentCode = findViewById<CodeView>(R.id.securityLowIntentCode)
            setCodeViewOptions(this, securityLowIntentCode, "java", challenge.securityLevels["low"]!!.intentCodeID)
        }
    }

    private fun setupMediumLevel(challenge: Challenge) {
        if(challenge.securityLevels["medium"] != null) {
            val securityMediumTextView = findViewById<TextView>(R.id.securityMediumDescription)
            securityMediumTextView.text = getString(challenge.securityLevels["medium"]!!.explanationID)

            val securityMediumManifest = findViewById<CodeView>(R.id.securityMediumManifest)
            setCodeViewOptions(this, securityMediumManifest, "xml",
                    challenge.securityLevels["medium"]!!.manifestID)

            val securityMediumIntentCode = findViewById<CodeView>(R.id.securityMediumIntentCode)
            setCodeViewOptions(this, securityMediumIntentCode, "java",
                    challenge.securityLevels["medium"]!!.intentCodeID)
        }
    }

    private fun setupHighLevel(challenge: Challenge) {
        if(challenge.securityLevels["high"] != null) {
            val securityHighTextView = findViewById<TextView>(R.id.securityHighDescription)
            securityHighTextView.text = getString(challenge.securityLevels["high"]!!.explanationID)

            val securityHighManifest = findViewById<CodeView>(R.id.securityHighManifest)
            setCodeViewOptions(this, securityHighManifest, "xml",
                    challenge.securityLevels["high"]!!.manifestID)

            val securityHighIntentCode = findViewById<CodeView>(R.id.securityHighIntentCode)
            setCodeViewOptions(this, securityHighIntentCode, "java",
                    challenge.securityLevels["high"]!!.intentCodeID)
        }
    }

    private fun setupVeryHighLevel(challenge: Challenge) {
        if(challenge.securityLevels["very high"] != null) {
            val securityVeryHighTextView = findViewById<TextView>(R.id.securityVeryHighDescription)
            securityVeryHighTextView.text = getString(challenge.securityLevels["very high"]!!.explanationID)

            val securityVeryHighManifest = findViewById<CodeView>(R.id.securityVeryHighManifest)
            setCodeViewOptions(this, securityVeryHighManifest, "xml",
                    challenge.securityLevels["very high"]!!.manifestID)

            val securityVeryHighIntentCode = findViewById<CodeView>(R.id.securityVeryHighIntentCode)
            setCodeViewOptions(this, securityVeryHighIntentCode, "java",
                    challenge.securityLevels["very high"]!!.intentCodeID)
        }
    }

    private fun setupImpossibleLevel(challenge: Challenge) {
        if(challenge.securityLevels["impossible"] != null) {
            val securityImpossibleTextView = findViewById<TextView>(R.id.securityImpossibleDescription)
            securityImpossibleTextView.text = getString(challenge.securityLevels["impossible"]!!.explanationID)

            val securityImpossibleManifest = findViewById<CodeView>(R.id.securityImpossibleManifest)
            setCodeViewOptions(this, securityImpossibleManifest, "xml",
                    challenge.securityLevels["impossible"]!!.manifestID)

            val securityImpossibleIntentCode = findViewById<CodeView>(R.id.securityImpossibleIntentCode)
            setCodeViewOptions(this, securityImpossibleIntentCode, "java",
                    challenge.securityLevels["impossible"]!!.intentCodeID)
        }
    }
}