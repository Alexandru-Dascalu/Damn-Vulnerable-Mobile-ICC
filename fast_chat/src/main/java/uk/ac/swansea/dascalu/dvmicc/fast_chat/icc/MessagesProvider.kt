package uk.ac.swansea.dascalu.dvmicc.fast_chat.icc

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

import uk.ac.swansea.dascalu.dvmicc.fast_chat.Chat
import uk.ac.swansea.dascalu.dvmicc.fast_chat.Message

class MessagesProvider : ContentProvider() {
    private val data : ArrayList<Chat> = ArrayList<Chat>()
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("uk.ac.swansea.dascalu.dvmicc.fast_chat.provider", "chats", 1)
        addURI("uk.ac.swansea.dascalu.dvmicc.fast_chat.provider", "chat/*", 2)
    }

    companion object Contract {
        val CHATS_URI = Uri.parse("content://uk.ac.swansea.dascalu.dvmicc.fast_chat.provider/chats")
        val CHAT_URI = Uri.parse("content://uk.ac.swansea.dascalu.dvmicc.fast_chat.provider/chat/")

        val CHAT_NAME = "Name"
        val CHAT_PREVIEW_MSG = "previewMessage"
        val CHAT_TIME = "Time"

        val MESSAGE_SENDER = "Sender"
        val MESSAGE = "Message"
        val MESSAGE_TIME = "Time"
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

        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {

        when(uriMatcher.match(uri)) {
             1 -> {
                 val cursor = MatrixCursor(arrayOf(CHAT_NAME, CHAT_PREVIEW_MSG,
                         CHAT_TIME))
                 val nameSet : HashSet<String>? = selectionArgs?.toHashSet()

                 for(chat in data) {
                    if (nameSet != null ) {
                        if(chat.name in nameSet) {
                            cursor.addRow(arrayOf(chat.name, chat.messages.last().contents, chat.messages.last().time))
                        }
                    } else {
                        cursor.addRow(arrayOf(chat.name, chat.messages.last().contents, chat.messages.last().time))
                    }
                 }

                 return cursor
             }
            2 -> {
                val cursor = MatrixCursor(arrayOf(MESSAGE_SENDER, MESSAGE,
                        MESSAGE_TIME))
                val path = uri.toString()

                val chatName: String = path.substring(path.lastIndexOf('/') + 1)
                val chat : Chat? = data.find { it.name == chatName }

                if(chat != null) {
                    for(message in chat.messages) {
                        cursor.addRow(arrayOf(message.sender, message.contents, message.time))
                    }
                }

                return cursor
            }
            else -> return null
        }
    }

    override fun getType(uri: Uri): String? {
        when(uriMatcher.match(uri)) {
            1 -> return "vnd.android.cursor.dir/uk.ac.swansea.dascalu.dvmicc.fast_chat.provider.chats"
            2 -> return "vnd.android.cursor.dir/uk.ac.swansea.dascalu.dvmicc.fast_chat.provider.chat"
            else -> return null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(uriMatcher.match(uri)) {
            1 -> {
                if (values != null) {
                    data.add(Chat(values.getAsString("Name"), ArrayList<Message>()))
                }

                return Uri.parse(
                    "content://uk.ac.swansea.dascalu.dvmicc.fast_chat.provider/chat/${data.lastIndex}")
            }
            2 -> {
                val path = uri.toString()
                val chatName: String = path.substring(path.lastIndexOf('/') + 1)
                val chat : Chat? = data.find { it.name == chatName}

                if (chat != null) {
                    if(values != null) {
                        chat.messages.add(Message(values.getAsString("sender"), values.getAsString(
                                "message"), values.getAsString("time")))

                        return Uri.parse(
                                "content://uk.ac.swansea.dascalu.dvmicc.fast_chat.provider/chat/$chatName/${chat.messages.last()}")
                    }
                }
                return null
            }
            else -> return null
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) : Int {
        when(uriMatcher.match(uri)) {
            1 -> {
                if(selectionArgs != null && selectionArgs.isNotEmpty()) {
                    val namesToDeleteSet = selectionArgs.toHashSet()
                    var i : Int = 0
                    var noRemoved : Int = 0

                    while (i < data.size) {
                        if(data[i].name in namesToDeleteSet) {
                            data.removeAt(i)
                            noRemoved++
                        } else {
                            i++
                        }
                    }

                    return noRemoved
                } else {
                    return 0
                }
            }
            2 -> {
                val path = uri.toString()
                val chatName = path.substring(path.lastIndexOf('/') + 1)

                if(selectionArgs != null && selectionArgs.isNotEmpty()) {
                    val chat = data.find { it.name == chatName }
                    if(chat != null) {
                        chat.messages.removeAt(selectionArgs[0].toInt())
                        return 1
                    }
                }
                return 0
            }
            else -> return 0
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }
}