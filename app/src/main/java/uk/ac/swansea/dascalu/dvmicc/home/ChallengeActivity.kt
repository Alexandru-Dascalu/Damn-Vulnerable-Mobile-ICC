package uk.ac.swansea.dascalu.dvmicc.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ChallengeInformationFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ChallengeInstructionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ManifestsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.AbstractFullQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge
import java.lang.IllegalStateException

class ChallengeActivity :  AppCompatActivity() {
    companion object {
        private val SETTINGS_REQUEST_CODE = 5
    }

    private lateinit var challengeModel : Challenge
    var hasGuessedApps: Boolean = false

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
                replaceFragment(getQuestionsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.instructionsButton -> {
                replaceFragment(ChallengeInstructionsFragment(hasGuessedApps, challengeModel.scenarioInstructions))
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
            intent.putExtra("challenge", challengeModel)
            intent.putExtra("launchedFromChallengeActivity", true)

            startActivityForResult(intent, SETTINGS_REQUEST_CODE)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == SETTINGS_REQUEST_CODE) {

        }
    }

    private fun getQuestionsFragment() : AbstractFullQuestionsFragment {
        return when(challengeModel.questionsFragment) {
            BroadcastTheftQuestionsFragment::class -> BroadcastTheftQuestionsFragment()
            else -> throw IllegalStateException("Questions fragment type unknown!")
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.challengeContentFrame, newFragment)
        fragmentTransaction.commit()
    }
}