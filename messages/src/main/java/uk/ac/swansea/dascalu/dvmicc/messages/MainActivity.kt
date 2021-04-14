package uk.ac.swansea.dascalu.dvmicc.messages

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

import uk.ac.swansea.dascalu.dvmicc.messages.adapters.ChatsAdapter

class MainActivity : AppCompatActivity() {

    private var chatsAdapter : ChatsAdapter? = null
    private val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == false) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
            /* the boolean can be null is the app has storage permission before this launcher was
                called.*/
        }

        if (permissions[Manifest.permission.READ_SMS] == true) {
            Snackbar.make(findViewById<MaterialToolbar>(R.id.mainActivityToolbar),
                    "Read SMS permission acquired!", Snackbar.LENGTH_LONG).show()
            addAdapter()
        } else if (permissions[Manifest.permission.READ_SMS] == false) {
            Snackbar.make(findViewById<MaterialToolbar>(R.id.mainActivityToolbar),
                    "App needs permission to read SMS messages!", Snackbar.LENGTH_LONG).show()
        /* the boolean can be null is the app has storage permission before this launcher was
            called.*/
        } else {
            addAdapter()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appbar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        setSupportActionBar(appbar)

        val chatsView = findViewById<RecyclerView>(R.id.chats_recycler_view)
        chatsView.layoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(this,
                (chatsView.layoutManager as LinearLayoutManager).orientation)
        chatsView.addItemDecoration(dividerItemDecoration)

        if(!checkPermissions()) {
            val permissions : Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS, Manifest.permission.RECEIVE_WAP_PUSH)
            requestPermissionsLauncher.launch(permissions)
        } else {
            addAdapter()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.log_button -> {
                val intent = Intent(this, LogActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        chatsAdapter?.loadData(this)
        chatsAdapter?.notifyDataSetChanged()
    }

    private fun addAdapter() {
        val chatsView = findViewById<RecyclerView>(R.id.chats_recycler_view)
        chatsAdapter = ChatsAdapter(this)
        chatsView.adapter = chatsAdapter
    }

    private fun checkPermissions() : Boolean {
        val hasStoragePermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val hasReadSMSPermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
        val hasReceiveSMSPermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
        val hasSendSMSPermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
        val hasReceivePushPermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_WAP_PUSH) == PackageManager.PERMISSION_GRANTED

        return hasStoragePermission && hasReadSMSPermission && hasReceiveSMSPermission &&
                hasSendSMSPermission && hasReceivePushPermission
    }
}