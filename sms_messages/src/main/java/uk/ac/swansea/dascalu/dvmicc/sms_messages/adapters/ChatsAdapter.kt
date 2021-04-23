package uk.ac.swansea.dascalu.dvmicc.sms_messages.adapters

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import uk.ac.swansea.dascalu.dvmicc.sms_messages.Chat
import uk.ac.swansea.dascalu.dvmicc.sms_messages.R
import uk.ac.swansea.dascalu.dvmicc.sms_messages.SMS

import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class ChatsAdapter(context: Context) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {
    private val data : ArrayList<Chat> = ArrayList<Chat>()
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
        holder.userImageView.setImageResource(R.drawable.ic_profile)

        holder.nameTextView.text = data[position].name
        holder.messageTextView.text = trimMessage(data[position].message)

        holder.timeTextView.text = getMessageDate(position)
    }

    fun loadData(context: Context) {
        data.clear()
        val cursor : Cursor? = context.contentResolver.query(Uri.parse("content://sms/"),
                null, null, null, null)

        if(cursor != null) {
            while(cursor.moveToNext()) {
                val sms = SMS()
                sms.ID = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                sms.address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                sms.message = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                sms.readState = cursor.getString(cursor.getColumnIndexOrThrow("read"))
                sms.time = cursor.getString(cursor.getColumnIndexOrThrow("date")).toLongOrNull()

                if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")) {
                    sms.type = "inbox"
                } else {
                    sms.type = "sent"
                }

                val chat : Chat? =  data.find { it.belongsToChat(sms) }

                if(chat != null) {
                    chat.messages.add(chat.messages.lastIndex, sms)
                } else {
                    data.add(Chat(sms.address, arrayListOf(sms)))
                }
            }

            cursor.close()
        }
    }

    private fun trimMessage(message: String) : String {
        if (message.length > 30) {
            return message.substring(0, 30) + "..."
        } else {
            return message
        }
    }

    private fun getMessageDate(position: Int) : String {
        if(data[position].time != null) {
            val calendaristicDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val time : Long = data[position].time!!

            val messageDate : String = calendaristicDateFormat.format(time)
            val todayDate : String = calendaristicDateFormat.format(System.currentTimeMillis())

            if(messageDate == todayDate) {
                val clockDateFormat = SimpleDateFormat("HH:mm:ss")
                return clockDateFormat.format(time)
            } else {
                return messageDate
            }
        } else {
            return ""
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImageView = itemView.findViewById<ImageView>(R.id.userImagePhoto)
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val messageTextView = itemView.findViewById<TextView>(R.id.messagePreviewTextView)
        val timeTextView = itemView.findViewById<TextView>(R.id.timeTextView)
    }
}