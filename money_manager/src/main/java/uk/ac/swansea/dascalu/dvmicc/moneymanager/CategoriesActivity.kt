package uk.ac.swansea.dascalu.dvmicc.moneymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import uk.ac.swansea.dascalu.dvmicc.moneymanager.adapters.CategoriesTapAdapter

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
}