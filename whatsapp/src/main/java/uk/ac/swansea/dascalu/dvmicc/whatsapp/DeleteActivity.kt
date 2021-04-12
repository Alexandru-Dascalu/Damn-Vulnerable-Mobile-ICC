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

class DeleteActivity : AppCompatActivity() {
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
        val data = Intent()
        data.putExtra("chatNamesToDelete", chatNamesToDelete)
        setResult(RESULT_OK, data)

        finish()
    }
}