package uk.ac.swansea.alexandru.dvmicc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import uk.ac.swansea.alexandru.dvmicc.model.Challenge

class ChallengeActivity :  AppCompatActivity() {
    private lateinit var challengeModel : Challenge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        //get challenge enum that contains challenge specific data from intent
        challengeModel = intent.extras!!.get("challenge") as Challenge

        val appBar : MaterialToolbar = findViewById<MaterialToolbar>(R.id.challengeActivityToolbar)
        setSupportActionBar(appBar)
        
        title = resources.getStringArray(R.array.challenges)[challengeModel.challengeNameIndex]
    }
}