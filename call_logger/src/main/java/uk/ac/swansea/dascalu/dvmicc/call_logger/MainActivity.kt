package uk.ac.swansea.dascalu.dvmicc.call_logger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import uk.ac.swansea.dascalu.dvmicc.call_logger.adapters.view_pager.CallLogViewPagerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainAppBar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        setSupportActionBar(mainAppBar)
        acquirePermissionsForReceiver()

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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.malware_zone_button) {
            val intent = Intent(this, BroadcastTheftActivity::class.java)
            startActivity(intent)

            return true
        } else if(item.itemId == R.id.clear_data_button) {
            deleteFile("data.txt")

            val view = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
            Snackbar.make(view, getString(R.string.stolen_data_cleared), Snackbar.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun acquirePermissionsForReceiver() {
        val securityLevel = loadSecuritySettingsFromFile(this)

        if(securityLevel == getString(R.string.highSecurityLevel).toLowerCase()) {

        } else if(securityLevel == getString(R.string.veryHighSecurityLevel).toLowerCase()) {

        }
    }
}