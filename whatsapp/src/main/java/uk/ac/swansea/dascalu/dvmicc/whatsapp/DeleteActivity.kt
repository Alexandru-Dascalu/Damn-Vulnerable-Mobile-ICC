package uk.ac.swansea.dascalu.dvmicc.whatsapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.appbar.MaterialToolbar

import uk.ac.swansea.dascalu.dvmicc.whatsapp.adapters.DeleteChatsAdapter
import uk.ac.swansea.dascalu.dvmicc.whatsapp.icc.MessagesProvider

class DeleteActivity : AppCompatActivity() {
    companion object {
        val DELETE_ACTION = "uk.ac.swansea.dascalu.dvmicc.whatsapp.intent.action.DELETE"
    }
    private lateinit var deleteAdapter : DeleteChatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val appbar = findViewById<MaterialToolbar>(R.id.deleteActivityToolbar)
        setSupportActionBar(appbar)

        val chatsView = findViewById<RecyclerView>(R.id.deleteChatsRecyclerView)
        chatsView.layoutManager = LinearLayoutManager(this)
        deleteAdapter = DeleteChatsAdapter(this)
        chatsView.adapter = deleteAdapter

        val dividerItemDecoration = DividerItemDecoration(this,
                (chatsView.layoutManager as LinearLayoutManager).orientation)
        chatsView.addItemDecoration(dividerItemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_activity_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete_chats_button -> {
                val chatsView = findViewById<RecyclerView>(R.id.deleteChatsRecyclerView)
                val chatNamesToDelete : HashSet<String> = HashSet<String>()

                for (i in 0 until deleteAdapter.itemCount) {
                    val viewHolder : DeleteChatsAdapter.ViewHolder = chatsView.
                        findViewHolderForAdapterPosition(i) as DeleteChatsAdapter.ViewHolder

                    if(viewHolder.chatCheckBox.isChecked) {
                        chatNamesToDelete.add(viewHolder.nameTextView.text.toString())
                    }
                }

                finish(chatNamesToDelete)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun finish(chatNamesToDelete: HashSet<String>) {
        val resultIntent = Intent()
        resultIntent.action = DELETE_ACTION
        resultIntent.addCategory(Intent.CATEGORY_APP_CONTACTS)

        resultIntent.putExtra("chatNamesToDelete", chatNamesToDelete)
        resultIntent.setDataAndType(MessagesProvider.CHATS_URI, "text/plain")
        resultIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        startService(resultIntent)
        finish()
    }
}