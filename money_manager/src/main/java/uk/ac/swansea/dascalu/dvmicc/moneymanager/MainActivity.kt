package uk.ac.swansea.dascalu.dvmicc.moneymanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted: Boolean ->

        if(granted) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topAppBar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        val navigationView = findViewById<NavigationView>(R.id.mainNavigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            if(menuItem.itemId == R.id.categories_button) {
                drawerLayout.close()

                val intent = Intent(this, CategoriesActivity::class.java)
                startActivity(intent)
                true
            } else {
                menuItem.isChecked = true
                true
            }
        }

        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}