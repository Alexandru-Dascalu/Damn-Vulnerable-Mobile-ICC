package uk.ac.swansea.dascalu.dvmicc.whatsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import uk.ac.swansea.dascalu.dvmicc.whatsapp.ChatPreview
import uk.ac.swansea.dascalu.dvmicc.whatsapp.R
import uk.ac.swansea.dascalu.dvmicc.whatsapp.icc.MessagesProvider

import java.text.SimpleDateFormat
import java.util.Date

import kotlin.collections.ArrayList

class DeleteChatsAdapter(private val context: Context) : RecyclerView.Adapter<DeleteChatsAdapter.ViewHolder>() {
    private val data : ArrayList<ChatPreview> = ArrayList<ChatPreview>()
    init {
        val cursor = context.contentResolver.query(MessagesProvider.Contract.CHATS_URI,
                null, null, null,null)

        if(cursor != null) {
            while(cursor.moveToNext()) {
                val name = cursor.getString(0)
                val previewMessage = cursor.getString(1)
                val time = cursor.getString(2)

                data.add(ChatPreview(name, previewMessage, time))
            }

            cursor.close()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.delete_chat_preview_layout,
                parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userImageView.setImageResource(R.drawable.ic_user)

        holder.nameTextView.text = data[position].name
        holder.messageTextView.text = data[position].previewMessage

        holder.timeTextView.text = getMessageDate(position)
    }

    private fun getMessageDate(position: Int) : String {
        val utcDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val calendaristicDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val time : Long = (utcDateFormat.parse(data[position].time) as Date).time

        val messageDate : String = calendaristicDateFormat.format(time)
        val todayDate : String = calendaristicDateFormat.format(System.currentTimeMillis())

        if(messageDate == todayDate) {
            val clockDateFormat = SimpleDateFormat("HH:mm:ss'Z'")
            return clockDateFormat.format(time)
        } else {
            return messageDate
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImageView = itemView.findViewById<ImageView>(R.id.deleteUserImagePhoto)
        val nameTextView = itemView.findViewById<TextView>(R.id.deleteNameTextView)
        val messageTextView = itemView.findViewById<TextView>(R.id.deleteMessagePreviewTextView)
        val timeTextView = itemView.findViewById<TextView>(R.id.deleteTimeTextView)
        val chatCheckBox = itemView.findViewById<CheckBox>(R.id.deleteCheckBox)
    }
}