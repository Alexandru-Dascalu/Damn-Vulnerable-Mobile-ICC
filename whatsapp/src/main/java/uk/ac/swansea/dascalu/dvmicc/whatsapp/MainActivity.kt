package uk.ac.swansea.dascalu.dvmicc.whatsapp

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

import uk.ac.swansea.dascalu.dvmicc.whatsapp.adapters.ChatsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var chatsAdapter : ChatsAdapter
    private val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        if(permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else if(permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == false) {
            Snackbar.make(findViewById(R.id.mainActivityToolbar),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
            /* the boolean can be null is the app has storage permission before this launcher was
                called.*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appbar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        setSupportActionBar(appbar)

        val chatsView = findViewById<RecyclerView>(R.id.chats_recycler_view)
        chatsView.layoutManager = LinearLayoutManager(this)
        chatsAdapter = ChatsAdapter(this)
        chatsView.adapter = chatsAdapter

        val dividerItemDecoration = DividerItemDecoration(this,
                (chatsView.layoutManager as LinearLayoutManager).orientation)
        chatsView.addItemDecoration(dividerItemDecoration)

        if(!checkPermissions()) {
            val permissions : Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissionsLauncher.launch(permissions)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete_activity_button -> {
                val intent = Intent(this, DeleteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        chatsAdapter.loadData(this)
        chatsAdapter.notifyDataSetChanged()
    }

    private fun checkPermissions() : Boolean {
        val hasStoragePermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        val hasCameraPermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val hasMicrophonePermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        val hasContactsPermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED
        val hasLocationPermission : Boolean = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        return hasStoragePermission && hasCameraPermission && hasMicrophonePermission &&
                hasContactsPermission && hasLocationPermission
    }
}