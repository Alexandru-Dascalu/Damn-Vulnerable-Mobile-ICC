package uk.ac.swansea.dascalu.dvmicc.battery_booster

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

import com.google.android.material.snackbar.Snackbar
import uk.ac.swansea.dascalu.dvmicc.battery_booster.fragments.ChargeFragment
import uk.ac.swansea.dascalu.dvmicc.battery_booster.fragments.HomeFragment
import uk.ac.swansea.dascalu.dvmicc.battery_booster.fragments.UsageFragment

class MainActivity : AppCompatActivity() {

    private val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted: Boolean ->

        if(granted) {
            Snackbar.make(findViewById(R.id.optimiseTextView),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(findViewById(R.id.optimiseTextView),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    private val navigationBarListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.home_button -> {
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.charge_button -> {
                replaceFragment(ChargeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.usage_button -> {
                replaceFragment(UsageFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainNavigationBar)
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationBarListener)
        bottomNavigationView.selectedItemId = R.id.home_button

        getStoragePermission()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.log_button -> {
                val intent = Intent(this, LogActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.clear_log_button -> {
                deleteFile("data.txt")

                val view = findViewById<MaterialButton>(R.id.optimiseButton)
                Snackbar.make(view, getString(R.string.log_cleared), Snackbar.LENGTH_LONG).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun getStoragePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.mainContentFrame, newFragment)
        fragmentTransaction.commit()
    }
}