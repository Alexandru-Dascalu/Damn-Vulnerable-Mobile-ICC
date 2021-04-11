package uk.ac.swansea.dascalu.dvmicc.whatsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appbar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        setSupportActionBar(appbar)
    }
}