package uk.ac.swansea.dascalu.dvmicc.moneymanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.swansea.dascalu.dvmicc.moneymanager.R

class CategoriesRecyclerViewAdapter(private val tabPosition: Int) :
    RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = inflater.inflate(R.layout.category_layout, parent, false)

        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryNameTextView.text = holder.categories[position]
    }

    override fun getItemCount(): Int {
        if (tabPosition == 0) {
            return 13
        } else if (tabPosition == 1) {
            return 6
        } else {
            return 0
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryNameTextView = itemView.findViewById<TextView>(R.id.categoryNameTextView)
        val categories = if(tabPosition == 0) {
            itemView.context.resources.getStringArray(R.array.expenseCategories)
        } else {
            itemView.context.resources.getStringArray(R.array.incomeCategories)
        }
    }
}