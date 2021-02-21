package uk.ac.swansea.alexandru.dvmicc

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import uk.ac.swansea.alexandru.dvmicc.fragments.ChallengeInformationFragment
import uk.ac.swansea.alexandru.dvmicc.fragments.ManifestsFragment
import uk.ac.swansea.alexandru.dvmicc.fragments.questions.BroadcastTheftQuestionsFragment
import uk.ac.swansea.alexandru.dvmicc.model.Challenge

class ChallengeActivity :  AppCompatActivity() {
    private lateinit var challengeModel : Challenge

    private val navigationBarListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.challengeInformationButton -> {
                replaceFragment(ChallengeInformationFragment(challengeModel.challengeNameIndex,
                        challengeModel.attackExplanation))

                return@OnNavigationItemSelectedListener true
            }
            R.id.manifestsButton -> {
                replaceFragment(ManifestsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.questionsButton -> {
                replaceFragment(BroadcastTheftQuestionsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        //get challenge enum that contains challenge specific data from intent
        challengeModel = intent.extras!!.get("challenge") as Challenge

        val appBar : MaterialToolbar = findViewById<MaterialToolbar>(R.id.challengeActivityToolbar)
        setSupportActionBar(appBar)
        
        title = resources.getStringArray(R.array.challenges)[challengeModel.challengeNameIndex]

        val bottomBar = findViewById<BottomNavigationView>(R.id.challengeNavigationBar)
        bottomBar.setOnNavigationItemSelectedListener(navigationBarListener)
        bottomBar.selectedItemId = R.id.challengeInformationButton
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.challenge_activity_app_bar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settingsButton) {
            val intent = Intent(this, ChallengeSettingsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.challengeContentFrame, newFragment)
        fragmentTransaction.commit()
    }
}