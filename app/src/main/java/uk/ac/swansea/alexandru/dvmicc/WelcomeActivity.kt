package uk.ac.swansea.alexandru.dvmicc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val whyICCtext : TextView = findViewById<TextView>(R.id.whyICCtext)
        whyICCtext.movementMethod = LinkMovementMethod.getInstance()
    }
}