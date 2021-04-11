package uk.ac.swansea.dascalu.dvmicc.whatsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.appbar.MaterialToolbar
import uk.ac.swansea.dascalu.dvmicc.whatsapp.adapters.ChatsAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appbar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        setSupportActionBar(appbar)

        val chatsView = findViewById<RecyclerView>(R.id.chats_recycler_view)
        chatsView.layoutManager = LinearLayoutManager(this)
        chatsView.adapter = ChatsAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }
}