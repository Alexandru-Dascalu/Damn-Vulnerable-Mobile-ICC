package uk.ac.swansea.dascalu.dvmicc.money_manager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import uk.ac.swansea.dascalu.dvmicc.money_manager.adapters.CategoriesTapAdapter

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val appBar = findViewById<MaterialToolbar>(R.id.categoriesActivityToolbar)
        title = resources.getString(R.string.categories)
        setSupportActionBar(appBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        appBar.setNavigationOnClickListener { navigationButton ->
            this.finish()
        }

        val viewPager = findViewById<ViewPager2>(R.id.categories_view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.categories_tab_layout)
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout.tabMode = TabLayout.MODE_FIXED

        viewPager.adapter = CategoriesTapAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.expenses)
                1 -> tab.text = resources.getString(R.string.income)
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.categories_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.log_button -> {
                val intent = Intent(this, LogActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}