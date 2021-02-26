package uk.ac.swansea.dascalu.newsaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

import uk.ac.swansea.dascalu.newsaggregator.fragments.BookmarksFragment
import uk.ac.swansea.dascalu.newsaggregator.fragments.CustomiseStreamsFragment
import uk.ac.swansea.dascalu.newsaggregator.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    private val homeFragment = HomeFragment()

    private val navigationBarItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home_button -> {
                replaceFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.customise_button -> {
                replaceFragment(CustomiseStreamsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.bookmarks_button -> {
                replaceFragment(BookmarksFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(appBar)

        findViewById<BottomNavigationView>(R.id.bottom_navigation_bar).
            setOnNavigationItemSelectedListener(navigationBarItemSelectedListener)

        authenticator.addAuthStateListener {
            if(authenticator.currentUser == null) {
                this.finish()
            }
        }

        replaceFragment(homeFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.log_out_button) {
            authenticator.signOut()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.main_content_frame, newFragment)
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        if(authenticator.currentUser != null) {
            authenticator.signOut()
        }

        super.onDestroy()
    }
}