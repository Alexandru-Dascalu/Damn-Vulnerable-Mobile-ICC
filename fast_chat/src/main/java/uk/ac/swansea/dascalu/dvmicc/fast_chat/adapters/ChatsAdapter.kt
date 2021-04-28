package uk.ac.swansea.dascalu.dvmicc.fast_chat.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import uk.ac.swansea.dascalu.dvmicc.fast_chat.ChatPreview
import uk.ac.swansea.dascalu.dvmicc.fast_chat.R
import uk.ac.swansea.dascalu.dvmicc.fast_chat.icc.MessagesProvider
import uk.ac.swansea.dascalu.dvmicc.fast_chat.icc.MessagesProviderHigh
import uk.ac.swansea.dascalu.dvmicc.fast_chat.loadSecuritySettingsFromFile

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.ArrayList

class ChatsAdapter(context: Context) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {
    private val data : ArrayList<ChatPreview> = ArrayList<ChatPreview>()
    init {
        loadData(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.chat_preview_layout,
            parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ChatsAdapter.ViewHolder, position: Int) {
        holder.userImageView.setImageResource(R.drawable.ic_user)

        holder.nameTextView.text = data[position].name
        holder.messageTextView.text = data[position].previewMessage

        holder.timeTextView.text = getMessageDate(position)
    }

    fun loadData(context: Context) {
        data.clear()

        val securityLevel: String = loadSecuritySettingsFromFile(context)
        val uri : Uri = if (securityLevel == "high") {
            MessagesProviderHigh.CHATS_URI
        } else {
            MessagesProvider.CHATS_URI
        }

        val cursor = context.contentResolver.query(uri,
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

    private fun getMessageDate(position: Int) : String {
        val utcDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val calendaristicDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val time : Long = (utcDateFormat.parse(data[position].date) as Date).time

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
        val userImageView = itemView.findViewById<ImageView>(R.id.userImagePhoto)
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val messageTextView = itemView.findViewById<TextView>(R.id.messagePreviewTextView)
        val timeTextView = itemView.findViewById<TextView>(R.id.timeTextView)
    }
}