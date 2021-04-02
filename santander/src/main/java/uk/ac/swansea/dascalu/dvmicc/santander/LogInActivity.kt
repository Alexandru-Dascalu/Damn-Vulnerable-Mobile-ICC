package uk.ac.swansea.dascalu.dvmicc.santander

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val toolbar = findViewById<MaterialToolbar>(R.id.loginActivityToolbar)
        setSupportActionBar(toolbar)
    }
}