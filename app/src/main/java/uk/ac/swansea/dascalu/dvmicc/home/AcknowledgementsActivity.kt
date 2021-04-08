package uk.ac.swansea.dascalu.dvmicc.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class AcknowledgementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acknowledgements)

        val appBar = findViewById<MaterialToolbar>(R.id.acknowledgementsActivityToolbar)
        title = resources.getString(R.string.acknowledgementsTitle)
        setSupportActionBar(appBar)
    }
}