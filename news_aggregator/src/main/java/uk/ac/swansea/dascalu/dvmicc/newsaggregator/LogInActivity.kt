package uk.ac.swansea.dascalu.dvmicc.newsaggregator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.utils.hideKeyboard
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.utils.loadSecuritySettingsFromFile

class LogInActivity : AppCompatActivity() {

    private lateinit var authenticator : FirebaseAuth

    private val readRequestStoragePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Snackbar.make(findViewById(R.id.log_in_layout),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
            acquirePermissionsForReceiver()
        } else {
            Snackbar.make(findViewById(R.id.log_in_layout),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    private val readNewsPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Snackbar.make(findViewById(R.id.log_in_layout),
                    R.string.readNewsPermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(findViewById(R.id.log_in_layout),
                    R.string.readNewsPermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        authenticator = FirebaseAuth.getInstance()

        checkPermissions()
    }

    fun logIn(view: View) {
        val emailAddress = findViewById<EditText>(R.id.email_input).text.toString()
        val password = findViewById<EditText>(R.id.password_input).text.toString()

        if(emailAddress != "" && password != "") {
            val task = authenticator.signInWithEmailAndPassword(emailAddress, password)

            task.addOnCompleteListener(this) {logInTask -> checkLogIn(logInTask, view)}
        } else {
            Snackbar.make(view, getString(R.string.log_in_credentials_msg), Snackbar.LENGTH_LONG).show()
            hideKeyboard(view, this)
        }
    }

    fun signUp(view: View) {
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signUpIntent)
    }

    private fun checkLogIn(task: Task<AuthResult>, view: View) {
        if (task.isSuccessful) {
            val mainActivityIntent = Intent(this, LoadingActivity::class.java)
            startActivity(mainActivityIntent)
        } else {
            Snackbar.make(view, getString(R.string.wrong_login_msg), Snackbar.LENGTH_LONG).show()
            val currentView = this.currentFocus

            if(currentView != null) {
                hideKeyboard(currentView, this)
            }
        }
    }

    override fun onStop() {
        findViewById<EditText>(R.id.email_input).text.clear()
        findViewById<EditText>(R.id.password_input).text.clear()
        super.onStop()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            readRequestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            acquirePermissionsForReceiver()
        }
    }

    private fun acquirePermissionsForReceiver() {
        val securityLevel = loadSecuritySettingsFromFile(this)

        if(securityLevel == getString(R.string.highSecurityLevel).toLowerCase()) {
            if(ContextCompat.checkSelfPermission(this,
                            "uk.ac.swansea.dascalu.dvmicc.newsaggregator.permissions.READ_NEWS_B")
                    != PackageManager.PERMISSION_GRANTED) {

                readNewsPermissionLauncher.launch(
                        "uk.ac.swansea.dascalu.dvmicc.newsaggregator.permissions.READ_NEWS_B")
            }
        }
    }
}