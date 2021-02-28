package uk.ac.swansea.dascalu.dvmicc.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import uk.ac.swansea.dascalu.dvmicc.home.fragments.ChallengesFragment
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
                replaceFragment(ChallengesFragment())
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

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.mainContentFrame, newFragment)
        fragmentTransaction.commit()
    }
}
