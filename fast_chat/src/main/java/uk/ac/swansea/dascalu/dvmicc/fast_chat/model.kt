package uk.ac.swansea.dascalu.dvmicc.fast_chat

data class Chat(val name: String, val messages: ArrayList<Message>)

data class ChatPreview(val name: String, val previewMessage: String, val date: String)

data class Message(val sender: String, val contents: String, val time: String)