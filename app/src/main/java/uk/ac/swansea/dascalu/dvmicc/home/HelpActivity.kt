package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.kbiakov.codeview.CodeView
import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge
import java.lang.IllegalStateException

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_theft_help)

        if(intent.extras != null) {
            val challenge: Challenge? = intent.extras!!.getSerializable("challenge") as Challenge?

            if(challenge != null) {
                setCode(challenge)
            } else {
                throw IllegalStateException("No challenge in intent for help activity!")
            }
        } else {
            throw IllegalStateException("No extras in intent that started help activity!")
        }
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