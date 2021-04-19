package uk.ac.swansea.dascalu.dvmicc.call_logger3.adapters.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import uk.ac.swansea.dascalu.dvmicc.call_logger3.R
import uk.ac.swansea.dascalu.dvmicc.call_logger3.model.Call

class LogFragmentAdapter(private val callList: List<Call>) : RecyclerView.Adapter<LogFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemRootView = inflater.inflate(R.layout.call_item_layout, parent, false)

        return ViewHolder(itemRootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val call = callList[position]

        if(call.name != null) {
            holder.nameTextView.text = call.name
        } else {
            holder.nameTextView.text = holder.nameTextView.context.resources.getString(
                    R.string.unknown_number)
        }

        holder.numberTextView.text = call.number
        holder.dateTextView.text = call.date
        holder.durationTextView.text = holder.nameTextView.context.resources.getString(
                R.string.duration, call.duration)
    }

    override fun getItemCount(): Int {
        return callList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val numberTextView = itemView.findViewById<TextView>(R.id.numberTextView)
        val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)
        val durationTextView = itemView.findViewById<TextView>(R.id.durationTextView)
    }
}