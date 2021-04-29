package uk.ac.swansea.dascalu.dvmicc.money_manager.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uk.ac.swansea.dascalu.dvmicc.money_manager.fragments.CategoriesFragment

class CategoriesTapAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return CategoriesFragment(position)
    }

    override fun getItemCount(): Int {
        return 2
    }
}