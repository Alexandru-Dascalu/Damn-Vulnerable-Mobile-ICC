package uk.ac.swansea.dascalu.dvmicc.moneymanager

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class LogInActivity : AppCompatActivity() {
    private var paymentURI : Uri? = null

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
    }

    private fun checkLogin(view: View, actionID: Int) : Boolean {
        if (actionID == EditorInfo.IME_ACTION_DONE) {
            val customerID = findViewById<EditText>(R.id.customerIDInput).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()

            if (customerID != "" && password != "") {
                val intent = Intent()
                intent.component = ComponentName("uk.ac.swansea.dascalu.dvmicc.santander",
                    "uk.ac.swansea.dascalu.dvmicc.santander.LogInActivity")
                startActivity(intent)

                finish()
            } else {
                Snackbar.make(view, "Log In details incorrect!", Snackbar.LENGTH_LONG).show()
                return false
            }
        }

        return false
    }
}