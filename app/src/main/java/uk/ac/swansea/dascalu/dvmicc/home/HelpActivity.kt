package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.kbiakov.codeview.CodeView
import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge
import java.lang.IllegalStateException

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        if(intent.extras != null) {
            val challenge: Challenge? = intent.extras!!.getSerializable("challenge") as Challenge?

            if(challenge != null) {
                setCode(challenge)
                setLevelDescriptions(challenge)
            } else {
                throw IllegalStateException("No challenge in intent for help activity!")
            }
        } else {
            throw IllegalStateException("No extras in intent that started help activity!")
        }
    }

    private fun setLevelDescriptions(challenge: Challenge) {
        val securityLowTextView = findViewById<TextView>(R.id.securityLowDescription)
        securityLowTextView.text = getString(challenge.securityLevels["low"]!!.explanationID)

        val securityMediumTextView = findViewById<TextView>(R.id.securityMediumDescription)
        securityMediumTextView.text = getString(challenge.securityLevels["medium"]!!.explanationID)

        val securityHighTextView = findViewById<TextView>(R.id.securityHighDescription)
        securityHighTextView.text = getString(challenge.securityLevels["high"]!!.explanationID)

        val securityVeryHighTextView = findViewById<TextView>(R.id.securityVeryHighDescription)
        securityVeryHighTextView.text = getString(challenge.securityLevels["very high"]!!.explanationID)

        val securityImpossibleTextView = findViewById<TextView>(R.id.securityImpossibleDescription)
        securityImpossibleTextView.text = getString(challenge.securityLevels["impossible"]!!.explanationID)
    }

    private fun setCode(challenge: Challenge) {
        val securityLowManifest = findViewById<CodeView>(R.id.securityLowManifest)
        setCodeViewOptions(this, securityLowManifest, "xml", challenge.securityLevels["low"]!!.manifestID)

        val securityMediumManifest = findViewById<CodeView>(R.id.securityMediumManifest)
        setCodeViewOptions(this, securityMediumManifest, "xml", challenge.securityLevels["medium"]!!.manifestID)

        val securityHighManifest = findViewById<CodeView>(R.id.securityHighManifest)
        setCodeViewOptions(this, securityHighManifest, "xml", challenge.securityLevels["high"]!!.manifestID)

        val securityVeryHighManifest = findViewById<CodeView>(R.id.securityVeryHighManifest)
        setCodeViewOptions(this, securityVeryHighManifest, "xml", challenge.securityLevels["very high"]!!.manifestID)

        val securityImpossibleManifest = findViewById<CodeView>(R.id.securityImpossibleManifest)
        setCodeViewOptions(this, securityImpossibleManifest, "xml", challenge.securityLevels["impossible"]!!.manifestID)
    }
}