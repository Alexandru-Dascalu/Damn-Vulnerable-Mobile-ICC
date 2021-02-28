package uk.ac.swansea.dascalu.dvmicc.newsaggregator

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.fragments.BookmarksFragment
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.fragments.CustomiseStreamsFragment
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.fragments.HomeFragment
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.icc.NewsBroadcastReceiver
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.icc.NewsService

class MainActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    private var receiverIntentFilter : IntentFilter? = null
    var newsBroadcastReceiver : NewsBroadcastReceiver? = null
        private set

    private val navigationBarItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home_button -> {
                replaceFragment(HomeFragment())
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

        newsBroadcastReceiver = NewsBroadcastReceiver()
        receiverIntentFilter = IntentFilter()
        receiverIntentFilter!!.addAction(NewsService.NEWS_RESULT_BROADCAST_ACTION)

        replaceFragment(HomeFragment())
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

    override fun onResume() {
        super.onResume()
        registerReceiver(newsBroadcastReceiver, receiverIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(newsBroadcastReceiver)
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