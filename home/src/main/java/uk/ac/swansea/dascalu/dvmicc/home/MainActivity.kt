package uk.ac.swansea.dascalu.dvmicc.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import uk.ac.swansea.dascalu.dvmicc.home.fragments.ChallengeListFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.InformationFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.IntroductionFragment

class MainActivity : AppCompatActivity() {

    private val navigationBarListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.introductionButton -> {
                replaceFragment(IntroductionFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.informationButton -> {
                replaceFragment(InformationFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.challengesButton -> {
                replaceFragment(ChallengeListFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBar : MaterialToolbar = findViewById(R.id.mainActivityToolbar)
        setSupportActionBar(appBar)

        val navigationBar : BottomNavigationView = findViewById(R.id.mainNavigationBar)
        navigationBar.setOnNavigationItemSelectedListener(navigationBarListener)
        navigationBar.selectedItemId = R.id.introductionButton
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.acknowledgements_button) {
            val intent = Intent(this, AcknowledgementsActivity::class.java)
            startActivity(intent)

            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.mainContentFrame, newFragment)
        fragmentTransaction.commit()
    }
}
