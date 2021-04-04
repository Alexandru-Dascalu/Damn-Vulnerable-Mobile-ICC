package uk.ac.swansea.dascalu.dvmicc.santander

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import uk.ac.swansea.dascalu.dvmicc.santander.fragments.HomeBankFragment
import uk.ac.swansea.dascalu.dvmicc.santander.fragments.PayFragment

class MainActivity : AppCompatActivity() {
    private var paymentURI : Uri? = null

    private val navigationBarItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home_button -> {
                replaceFragment(HomeBankFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.pay_fragment_button -> {
                replaceFragment(PayFragment(paymentURI))
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

        paymentURI = intent.data

        val bottomBar = findViewById<BottomNavigationView>(R.id.mainBottomNavBar)
        bottomBar.setOnNavigationItemSelectedListener(navigationBarItemSelectedListener)
        bottomBar.selectedItemId = R.id.home_button

        if(paymentURI != null) {
            if(paymentURI!!.getQueryParameter("amount") != null ||
                    paymentURI!!.getQueryParameter("sortCode") != null ||
                    paymentURI!!.getQueryParameter("accountNum") != null) {
                bottomBar.selectedItemId = R.id.pay_fragment_button
            }
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.mainFrameLayout, newFragment)
        fragmentTransaction.commit()
    }
}