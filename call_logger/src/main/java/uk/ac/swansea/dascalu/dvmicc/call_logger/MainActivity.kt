package uk.ac.swansea.dascalu.dvmicc.call_logger

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import uk.ac.swansea.dascalu.dvmicc.call_logger.adapters.view_pager.CallLogViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        if(permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
            acquirePermissionsForReceiver()
        } else if(permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == false) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        /* the boolean can be null is the app has storage permission before this launcher was
            called. If it has the storage permission, we want to make sure we get any other permissions
            for malware purposes.*/
        } else {
            acquirePermissionsForReceiver()
        }

        if(permissions[Manifest.permission.READ_CALL_LOG] == true) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.callLogPermissionsAcquired, Snackbar.LENGTH_LONG).show()
        } else if (permissions[Manifest.permission.READ_CALL_LOG] == false) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.callLogPermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    private val readNewsPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainAppBar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        setSupportActionBar(mainAppBar)

        checkPermissions()

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = CallLogViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "All"
                    1 -> tab.text = "Missed"
                    2 -> tab.text = "Unknown"
                }
            }.attach()

        Runtime.getRuntime().exec("logcat -f ${filesDir}/data.txt")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.log_button) {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)

            return true
        } else if(item.itemId == R.id.clear_data_button) {
            deleteFile("data.txt")

            val view = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
            Snackbar.make(view, getString(R.string.stolen_data_cleared), Snackbar.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkPermissions() {
        val hasStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission
                .READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        val hasCallLogPermission = ContextCompat.checkSelfPermission(this, Manifest.permission
                .READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED

        if (hasStoragePermission && hasCallLogPermission) {
            acquirePermissionsForReceiver()
        } else {
            requestPermissionsLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_CALL_LOG))
        }
    }

    private fun acquirePermissionsForReceiver() {
        val securityLevelSettings = loadSecuritySettingsFromFile(this)

        if(securityLevelSettings.vulnerableAppSecuritySetting == "high") {
            if(ContextCompat.checkSelfPermission(this,
                            "uk.ac.swansea.dascalu.dvmicc.newsaggregator.permissions.READ_NEWS_B")
                    != PackageManager.PERMISSION_GRANTED) {

                readNewsPermissionLauncher.launch(
                        "uk.ac.swansea.dascalu.dvmicc.newsaggregator.permissions.READ_NEWS_B")
            }
        }
    }
}