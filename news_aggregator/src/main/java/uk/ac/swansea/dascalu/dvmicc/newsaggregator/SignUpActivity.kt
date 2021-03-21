package uk.ac.swansea.dascalu.dvmicc.newsaggregator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.model.NewsStream
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.model.User

class SignUpActivity : AppCompatActivity() {

    private lateinit var authenticator : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        authenticator = FirebaseAuth.getInstance()
    }

    fun createAccount(view: View) {
        val emailAddress = findViewById<EditText>(R.id.email_input).text.toString()
        val password = findViewById<EditText>(R.id.password_input).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.confirm_password_input).text.toString()

        if(emailAddress != "" && password != "") {
            if(password == confirmPassword)
            {
                val task: Task<AuthResult> = authenticator.createUserWithEmailAndPassword(
                    emailAddress, password)
                task.addOnCompleteListener(this) {signUpTask -> checkSignUp(view, signUpTask)}
            } else {
                Snackbar.make(view, getString(R.string.password_confirmation_msg), Snackbar.LENGTH_LONG).show()
                hideKeyboard(view, this)
            }
        }
    }

    private fun checkSignUp(view: View, signUpTask: Task<AuthResult>) {
        if(signUpTask.isSuccessful) {
            addNewUserToRealtimeDatabase()

            val emailAddress = findViewById<EditText>(R.id.email_input).text.toString()
            val password = findViewById<EditText>(R.id.password_input).text.toString()

            val task = authenticator.signInWithEmailAndPassword(emailAddress, password)
            task.addOnCompleteListener { signInTask ->  onLogInAfterSignUp(signInTask)}
        } else {
            try {
                throw signUpTask.exception!!
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Snackbar.make(view, getString(R.string.invalid_email_msg), Snackbar.LENGTH_LONG).show()
                Log.e("error", "$e.message")
                hideKeyboard(view, this)
            }
            catch (e: FirebaseAuthWeakPasswordException) {
                Snackbar.make(view, "${getString(R.string.weak_password_msg)}!$e.getReason()", Snackbar.LENGTH_LONG).show()
                Log.e("error", "${e.reason}")
                hideKeyboard(view, this)
            }
            catch (e: FirebaseAuthUserCollisionException) {
                Snackbar.make(view, getString(R.string.existing_user_collision_msg), Snackbar.LENGTH_LONG).show()
                Log.e("error", "${e.message}")
                hideKeyboard(view, this)
            }
        }
    }

    private fun addNewUserToRealtimeDatabase() {
        val userID: String = authenticator.currentUser!!.uid

        val allStream: NewsStream = NewsStream(resources.getString(R.string.all), mutableListOf<Int>())
        val newUser: User = User(mutableListOf<String>(), mutableListOf<String>(), mutableListOf<NewsStream>(allStream))

        val map = mutableMapOf<String, User>()
        map.put(userID, newUser)

        Firebase.database.reference.child("users").updateChildren(map as Map<String, Any>)
    }

    private fun onLogInAfterSignUp(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            val intent: Intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}