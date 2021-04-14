package uk.ac.swansea.dascalu.dvmicc.messages

data class Chat(val name: String, val messages: ArrayList<SMS>) {
    var time : Long? = 0
        private set
        get() {
            if (messages.isNotEmpty()) {
                return messages.last().time
            } else {
                return 0
            }
        }

    var message : String = ""
        private set
        get() {
            if (messages.isNotEmpty()) {
                return messages.last().message
            } else {
                return ""
            }
        }

    fun belongsToChat(message: SMS) : Boolean {
        return message.address == this.name
    }
}

data class SMS(var ID: String = "", var address: String = "", var message: String = "",
               var readState: String = "", var time : Long? = 0, var type: String = "")

data class ChatPreview(val name: String, val previewMessage: String, val date: String)