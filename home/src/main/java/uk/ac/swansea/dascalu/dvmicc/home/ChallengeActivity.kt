package uk.ac.swansea.dascalu.dvmicc.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ChallengeInformationFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ChallengeInstructionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ManifestsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.ActivityIntentHijackFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftDOSQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftMITMQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.ProviderUriHijackQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.model.ViewModel

import java.lang.IllegalStateException

class ChallengeActivity :  AppCompatActivity() {
    companion object {
        private val SETTINGS_REQUEST_CODE = 5
    }

    init {
        supportFragmentManager.fragmentFactory = ChallengeFragmentFactory()
    }

    private val navigationBarListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.challengeInformationButton -> {
                replaceFragment(ChallengeInformationFragment::class.java.name, "infoFragment")

                return@OnNavigationItemSelectedListener true
            }
            R.id.manifestsButton -> {
                replaceFragment(ManifestsFragment::class.java.name, "manifestFragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.questionsButton -> {
                val fragmentName : String = getQuestionsFragmentName()

                replaceFragment(fragmentName, "questionsFragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.instructionsButton -> {
                replaceFragment(ChallengeInstructionsFragment::class.java.name, "instructionsFragment")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        val appBar : MaterialToolbar = findViewById<MaterialToolbar>(R.id.challengeActivityToolbar)
        setSupportActionBar(appBar)
        
        title = resources.getStringArray(R.array.challenges)[ViewModel.instance.challenge.challengeNameIndex]

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
            intent.putExtra("launchedFromChallengeActivity", true)

            startActivityForResult(intent, SETTINGS_REQUEST_CODE)
            return true
        } else if(item.itemId == R.id.helpButton) {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)

            return true
        } else if (item.itemId == R.id.trophyButton) {
            val intent = Intent(this, ChallengeConclusionActivity::class.java)

            startActivity(intent)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == SETTINGS_REQUEST_CODE) {

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun getQuestionsFragmentName() : String {
        return when(ViewModel.instance.challenge.challengeNameIndex) {
            0 -> BroadcastTheftQuestionsFragment::class.java.name
            1 -> BroadcastTheftDOSQuestionsFragment::class.java.name
            2 -> BroadcastTheftMITMQuestionsFragment::class.java.name
            3 -> ActivityIntentHijackFragment::class.java.name
            4 -> ProviderUriHijackQuestionsFragment::class.java.name
            else -> throw IllegalStateException("Questions fragment type unknown!")
        }
    }

    private fun replaceFragment(newFragmentName: String, tag: String) {
        val newFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, newFragmentName)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if(supportFragmentManager.findFragmentByTag("questionsFragment") != null) {
            ViewModel.instance.questionsFragmentState = supportFragmentManager.saveFragmentInstanceState(
                supportFragmentManager.findFragmentByTag("questionsFragment")!!)
        }

        fragmentTransaction.replace(R.id.challengeContentFrame, newFragment, tag)
        fragmentTransaction.commit()
    }
}