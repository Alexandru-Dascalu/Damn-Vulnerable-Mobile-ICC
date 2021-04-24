package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar
import io.github.kbiakov.codeview.CodeView

import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge
import uk.ac.swansea.dascalu.dvmicc.home.model.SecurityLevel
import uk.ac.swansea.dascalu.dvmicc.home.model.ChallengeViewModel
import uk.ac.swansea.dascalu.dvmicc.home.model.OperationMode

class SecurityLevelsExplanationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_levels)
        setSupportActionBar(findViewById<MaterialToolbar>(R.id.securityLevelsActivityToolbar))

        val challenge: Challenge = ChallengeViewModel.instance.challenge
        val operationMode : OperationMode = if (intent.getSerializableExtra("mode") != null) {
            intent.getSerializableExtra("mode") as OperationMode
        } else {
            OperationMode.BEGINNER
        }

        setupSecurityLevels(challenge, operationMode)

        title = getString(R.string.securityLevelsActivityTitle)
    }

    private fun setupSecurityLevels(challenge: Challenge, operationMode: OperationMode) {
        setupSecurityLevel(R.id.securityLowDescription, R.id.securityLowManifest,
                R.id.securityLowIntentCode, R.id.securityLowHeader, challenge.securityLevels["low"],
                operationMode)

        setupSecurityLevel(R.id.securityMediumDescription, R.id.securityMediumManifest,
                R.id.securityMediumIntentCode, R.id.securityMediumHeader, challenge.securityLevels["medium"],
                operationMode)

        setupSecurityLevel(R.id.securityHighDescription, R.id.securityHighManifest,
                R.id.securityHighIntentCode, R.id.securityHighHeader, challenge.securityLevels["high"],
                operationMode)

        setupSecurityLevel(R.id.securityVeryHighDescription, R.id.securityVeryHighManifest,
                R.id.securityVeryHighIntentCode, R.id.securityVeryHighHeader, challenge.securityLevels["very high"],
                operationMode)

        setupSecurityLevel(R.id.securityImpossibleDescription, R.id.securityImpossibleManifest,
                R.id.securityImpossibleIntentCode, R.id.securityImpossibleHeader, challenge.securityLevels["impossible"],
                operationMode)
    }

    private fun setupSecurityLevel(textViewID: Int, manifestID: Int, intentCodeID: Int,
                                   headerTextViewID: Int, securityLevel: SecurityLevel?,
                                   operationMode: OperationMode) {
        if(securityLevel != null) {
            val levelDescriptionTextView = findViewById<TextView>(textViewID)

            if (operationMode != OperationMode.EXPERIENCED) {
                levelDescriptionTextView.text = resources.getString(securityLevel.explanationID)
                levelDescriptionTextView.setTextIsSelectable(true)
            } else {
                levelDescriptionTextView.visibility = View.GONE
            }

            val manifestView = findViewById<CodeView>(manifestID)
            setCodeViewOptions(this, manifestView, "xml",
                    securityLevel.manifestID)

            val intentCodeView = findViewById<CodeView>(intentCodeID)
            setCodeViewOptions(this, intentCodeView, "java",
                    securityLevel.intentCodeID)

        } else {
            hideSecurityLevel(textViewID, manifestID, intentCodeID, headerTextViewID)
        }
    }

    private fun hideSecurityLevel(textViewID: Int, manifestID: Int, intentCodeID: Int,
                                  headerTextViewID: Int) {
        findViewById<TextView>(headerTextViewID).visibility = View.GONE
        findViewById<TextView>(textViewID).visibility = View.GONE
        findViewById<CodeView>(manifestID).visibility = View.GONE
        findViewById<CodeView>(intentCodeID).visibility = View.GONE
    }
}