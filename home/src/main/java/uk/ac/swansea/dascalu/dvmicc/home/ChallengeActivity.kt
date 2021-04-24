package uk.ac.swansea.dascalu.dvmicc.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ChallengeInformationFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ChallengeInstructionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ManifestsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.ActivityIntentHijackFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftDOSQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftMITMQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.ProviderUriHijackQuestionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.model.ChallengeViewModel
import uk.ac.swansea.dascalu.dvmicc.home.model.OperationMode

import java.lang.IllegalStateException

class ChallengeActivity :  AppCompatActivity() {
    companion object {
        private val SETTINGS_REQUEST_CODE = 5
    }

    private var operationMode : OperationMode = OperationMode.BEGINNER

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
        
        title = resources.getStringArray(R.array.challenges)[
                ChallengeViewModel.instance.challenge.challengeNameIndex]

        setOperationMode(savedInstanceState, intent)
        setupBottomBar(savedInstanceState)
    }

    private fun setOperationMode(savedInstanceState: Bundle?, intent: Intent) {
        if(savedInstanceState != null) {
            if(savedInstanceState.getSerializable("mode") != null) {
                operationMode = savedInstanceState.getSerializable("mode") as OperationMode
            }
        } else {
            if(intent.extras!= null && intent.getSerializableExtra("mode") != null) {
                operationMode = intent.getSerializableExtra("mode") as OperationMode
            }
        }
    }

    private fun setupBottomBar(savedInstanceState: Bundle?) {
        val bottomBar = findViewById<BottomNavigationView>(R.id.challengeNavigationBar)
        setBottomBarLayout(bottomBar)
        bottomBar.setOnNavigationItemSelectedListener(navigationBarListener)

        if(savedInstanceState != null) {
            if(savedInstanceState.getInt("bottomBarItemID") != 0) {
                bottomBar.selectedItemId = savedInstanceState.getInt("bottomBarItemID")
            } else {
                setBottomBarSelectedItemToFirst(bottomBar)
            }
        } else {
            setBottomBarSelectedItemToFirst(bottomBar)
        }
    }

    private fun setBottomBarLayout(bottomBar: BottomNavigationView) {
        bottomBar.menu.clear()

        if(operationMode == OperationMode.BEGINNER) {
            bottomBar.inflateMenu(R.menu.challenge_beginner_navigation_bar_layout)
        } else if(operationMode == OperationMode.EXPERIENCED) {
            bottomBar.inflateMenu(R.menu.challenge_expert_navigation_bar_layout)
        }
    }

    private fun setBottomBarSelectedItemToFirst(bottomBar: BottomNavigationView) {
        if(operationMode == OperationMode.BEGINNER) {
            bottomBar.selectedItemId = R.id.challengeInformationButton
        } else if (operationMode == OperationMode.EXPERIENCED) {
            bottomBar.selectedItemId = R.id.manifestsButton
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.challenge_activity_app_bar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settingsButton) {
            val intent = Intent(this, ChallengeSettingsActivity::class.java)
            intent.putExtra("launchedFromChallengeActivity", true)
            intent.putExtra("mode", operationMode)

            startActivityForResult(intent, SETTINGS_REQUEST_CODE)
            return true
        } else if(item.itemId == R.id.helpButton) {
            if (ChallengeViewModel.instance.hasGuessedApps) {
                val intent = Intent(this, SecurityLevelsExplanationActivity::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(findViewById<BottomNavigationView>(R.id.challengeNavigationBar),
                        R.string.securityLevelInfoHidden, 4000).show()
            }
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
            val newOperationMode : OperationMode? = data?.getSerializableExtra("mode") as OperationMode?

            if (newOperationMode != null && newOperationMode != operationMode) {
                operationMode = newOperationMode

                val bottomBar = findViewById<BottomNavigationView>(R.id.challengeNavigationBar)
                setBottomBarLayout(bottomBar)
                setBottomBarSelectedItemToFirst(bottomBar)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("bottomBarItemID", findViewById<BottomNavigationView>(
                R.id.challengeNavigationBar).selectedItemId)
        outState.putSerializable("mode", operationMode)
    }

    override fun onDestroy() {
        if(supportFragmentManager.findFragmentByTag("questionsFragment") != null) {
            ChallengeViewModel.instance.questionsFragmentState = supportFragmentManager.saveFragmentInstanceState(
                    supportFragmentManager.findFragmentByTag("questionsFragment")!!)
        }
        super.onDestroy()
    }

    private fun getQuestionsFragmentName() : String {
        return when(ChallengeViewModel.instance.challenge.challengeNameIndex) {
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

        /*Only manually save fragment the questions fragment is replaced by another fragment. If
        you rotate screen while on questions fragment, the fragment state will have been already
        saved when the activity was destroyed, and trying to save its state a second time results
        in a crash.*/
        if(tag != "questionsFragment" && supportFragmentManager.findFragmentByTag("questionsFragment") != null) {
            ChallengeViewModel.instance.questionsFragmentState = supportFragmentManager.saveFragmentInstanceState(
                supportFragmentManager.findFragmentByTag("questionsFragment")!!)
        }

        fragmentTransaction.replace(R.id.challengeContentFrame, newFragment, tag)
        fragmentTransaction.commit()
    }
}