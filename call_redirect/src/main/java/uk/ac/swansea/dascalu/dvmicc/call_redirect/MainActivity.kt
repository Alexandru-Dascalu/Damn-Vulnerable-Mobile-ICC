package uk.ac.swansea.dascalu.dvmicc.call_redirect

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun saveCountryCode(view: View) {
        val countryCodeEditText = findViewById<EditText>(R.id.countryCodeEditText)
        val sharedPreferences = getSharedPreferences("country_code", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("country_code", "+${countryCodeEditText.text}")
        editor.apply()
    }
}