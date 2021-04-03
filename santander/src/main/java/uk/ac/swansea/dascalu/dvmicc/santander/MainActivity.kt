package uk.ac.swansea.dascalu.dvmicc.santander

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import uk.ac.swansea.dascalu.dvmicc.santander.fragments.HomeBankFragment
import uk.ac.swansea.dascalu.dvmicc.santander.fragments.PayFragment

class MainActivity : AppCompatActivity() {

    private val navigationBarItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home_button -> {
                replaceFragment(HomeBankFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.pay_button -> {
                replaceFragment(PayFragment())
                return@OnNavigationItemSelectedListener  true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        setSupportActionBar(appBar)

        val bottomBar = findViewById<BottomNavigationView>(R.id.mainBottomNavBar)
        bottomBar.setOnNavigationItemSelectedListener(navigationBarItemSelectedListener)
        bottomBar.selectedItemId = R.id.home_button
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.mainFrameLayout, newFragment)
        fragmentTransaction.commit()
    }
}