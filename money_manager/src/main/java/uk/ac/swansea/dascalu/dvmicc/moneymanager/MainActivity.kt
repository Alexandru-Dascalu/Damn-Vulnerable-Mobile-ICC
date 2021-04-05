package uk.ac.swansea.dascalu.dvmicc.moneymanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
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
    }
}