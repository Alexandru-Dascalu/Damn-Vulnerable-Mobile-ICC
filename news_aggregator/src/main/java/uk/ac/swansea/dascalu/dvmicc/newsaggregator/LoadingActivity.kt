package uk.ac.swansea.dascalu.dvmicc.newsaggregator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.model.Database

class LoadingActivity : AppCompatActivity(), FirebaseLoadedCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Database(FirebaseAuth.getInstance(), this)
    }

    override fun onLoaded() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}