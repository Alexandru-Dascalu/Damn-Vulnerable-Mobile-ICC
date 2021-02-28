package uk.ac.swansea.dascalu.dvmicc.call_logger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainAppBar = findViewById<MaterialToolbar>(R.id.mainActivityToolbar)
        setSupportActionBar(mainAppBar)
    }
}