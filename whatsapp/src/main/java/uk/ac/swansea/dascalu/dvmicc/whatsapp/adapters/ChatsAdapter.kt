package uk.ac.swansea.dascalu.dvmicc.whatsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.swansea.dascalu.dvmicc.whatsapp.R

class ChatsAdapter : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.chat_preview_layout,
            parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImageView = itemView.findViewById<ImageView>(R.id.userImagePhoto)
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val messageTextView = itemView.findViewById<TextView>(R.id.messagePreviewTextView)
        val timeTextView = itemView.findViewById<TextView>(R.id.messageTimeTextView)
    }
}