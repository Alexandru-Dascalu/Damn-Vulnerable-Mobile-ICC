package uk.ac.swansea.dascalu.dvmicc.santander

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class LogInActivity : AppCompatActivity() {
    private var paymentURI : Uri? = null

    private val readPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted: Boolean ->

        if(granted) {
            Snackbar.make(findViewById(R.id.loginActivityToolbar),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(findViewById(R.id.loginActivityToolbar),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val toolbar = findViewById<MaterialToolbar>(R.id.loginActivityToolbar)
        setSupportActionBar(toolbar)

        paymentURI = intent.data

        val customerIDEditText = findViewById<EditText>(R.id.customerIDInput)
        customerIDEditText.setOnEditorActionListener { view , actionId, _ ->
            return@setOnEditorActionListener checkLogin(view, actionId)
        }

        findViewById<EditText>(R.id.passwordInput).setOnEditorActionListener { view, actionId, _ ->
            return@setOnEditorActionListener checkLogin(view, actionId)
        }

        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            readPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun checkLogin(view: View, actionID: Int) : Boolean {
        if(actionID == EditorInfo.IME_ACTION_DONE) {
            val customerIDInput = findViewById<EditText>(R.id.customerIDInput)
            val passwordInput = findViewById<EditText>(R.id.passwordInput)

            val customerID = customerIDInput.text.toString()
            val password = passwordInput.text.toString()

            if(customerID == "4621989436" && password == "98421") {
                val intent = Intent(this, MainActivity::class.java)
                intent.setDataAndType(paymentURI, "text/plain")
                startActivity(intent)

                customerIDInput.text.clear()
                passwordInput.text.clear()

                return true
            } else {
                Snackbar.make(view, "Log In details incorrect!", Snackbar.LENGTH_LONG).show()
                return false
            }
        }

        return false
    }
}