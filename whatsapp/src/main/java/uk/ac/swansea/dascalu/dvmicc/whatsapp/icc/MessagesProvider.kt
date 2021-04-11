package uk.ac.swansea.dascalu.dvmicc.whatsapp.icc

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

import uk.ac.swansea.dascalu.dvmicc.whatsapp.Chat
import uk.ac.swansea.dascalu.dvmicc.whatsapp.Message

class MessagesProvider : ContentProvider() {
    private val data : ArrayList<Chat> = ArrayList<Chat>()
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("uk.ac.swansea.dascalu.dvmicc.whatsapp.provider", "chats", 1)
        addURI("uk.ac.swansea.dascalu.dvmicc.whatsapp.provider", "chat/#", 2)
    }

    override fun onCreate(): Boolean {
        val firstMessages = ArrayList<Message>()
        firstMessages.add(Message("William", "Wanna go to the beach?", "2021-03-23T13:46:19Z"))
        firstMessages.add(Message("Alex", "Sure mate!", "2021-03-23T13:48:02Z"))
        firstMessages.add(Message("Alex", "When do we meet?", "2021-03-23T14:04:42Z"))
        firstMessages.add(Message("William", "At half past 4.", "2021-03-23T14:09:28Z"))
        data.add(Chat("William", firstMessages))

        val secondMessages = ArrayList<Message>()
        secondMessages.add(Message("Mom", "How are you?", "2021-03-27T08:25:03Z"))
        secondMessages.add(Message("Alex", "I am fine. I slept well and work is going well. I have a lecture in a couple minutes", "2021-03-27T08:51:28Z"))
        secondMessages.add(Message("Mom", "Stay safe!", "2021-03-27T08:57:49Z"))
        secondMessages.add(Message("Mom", "We have just gotten vaccinated.", "2021-03-27T08:58:07Z"))
        data.add(Chat("Mom", secondMessages))

        val thirdMessages = ArrayList<Message>()
        thirdMessages.add(Message("John", "When are we doing the coursework?", "2021-04-05T04:12:23Z"))
        thirdMessages.add(Message("Me", "Tomorrow", "2021-04-05T04:16:12Z"))
        thirdMessages.add(Message("John", "Ok", "2021-04-05T04:16:49Z"))
        thirdMessages.add(Message("Me", "We should do it all in one go.", "2021-04-05T04:38:10Z"))
        data.add(Chat("John", thirdMessages))
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {

        when(uriMatcher.match(uri)) {
             1 -> {
                 val cursor = MatrixCursor(arrayOf("Name", "previewMessage", "Time"))

                 cursor.addRow(arrayOf("William", data[0].messages.last(), data[0].messages.last().time))
                 cursor.addRow(arrayOf("Mom", data[1].messages.last(), data[1].messages.last().time))
                 cursor.addRow(arrayOf("Me", data[2].messages.last(), data[2].messages.last().time))

                 return cursor
             }
            2 -> {
                val cursor = MatrixCursor(arrayOf("Name", "Message"))
                val path = uri.toString()
                val chatIndex = path.substring(path.lastIndexOf('/') + 1).toInt()

                for(message in data[chatIndex].messages) {
                    cursor.addRow(arrayOf(message.sender, message.contents, message.time))
                }

                return cursor
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(uriMatcher.match(uri)) {
            1 -> {
                if (values != null) {
                    data.add(Chat(values.getAsString("Name"), ArrayList<Message>()))
                }

                return Uri.parse(
                    "content://uk.ac.swansea.dascalu.dvmicc.whatsapp.provider/chat/${data.lastIndex}")
            }
            2 -> {
                val path = uri.toString()
                val chatIndex = path.substring(path.lastIndexOf('/') + 1).toInt()

                if(values != null) {
                    val chat = data[chatIndex]
                    chat.messages.add(Message(values.getAsString("sender"), values.getAsString("message"), values.getAsString("time")))
                }

                return Uri.parse(
                    "content://uk.ac.swansea.dascalu.dvmicc.whatsapp.provider/chat/$chatIndex/${data[chatIndex].messages.last()}")
            }
        }
    }
}