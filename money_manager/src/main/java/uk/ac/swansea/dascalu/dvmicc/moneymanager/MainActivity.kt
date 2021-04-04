package uk.ac.swansea.dascalu.dvmicc.moneymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar

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
    }
}