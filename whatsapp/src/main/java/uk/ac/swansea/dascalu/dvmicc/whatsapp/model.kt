package uk.ac.swansea.dascalu.dvmicc.whatsapp

data class Chat(val name: String, val messages: ArrayList<Message>)

data class Message(val sender: String, val contents: String, val time: String)