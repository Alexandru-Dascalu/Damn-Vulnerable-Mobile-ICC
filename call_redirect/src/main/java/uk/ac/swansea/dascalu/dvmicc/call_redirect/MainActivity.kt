package uk.ac.swansea.dascalu.dvmicc.call_redirect

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    companion object {
        val COUNTRY_CODE_REGEX = Regex("^\\d{0,3}[-\\s]\\d{0,3}|\\d{1,3}\$")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun saveValidCountryCode(view: View) {
        val countryCodeEditText = findViewById<EditText>(R.id.countryCodeEditText)

        if(countryCodeEditText.text.toString().matches(COUNTRY_CODE_REGEX)) {
            saveCountryCode(countryCodeEditText.text.toString())
        } else {
            Snackbar.make(view, getString(R.string.invalidCodeWarning),
                    Snackbar.LENGTH_LONG).show()
        }
    }

    private fun saveCountryCode(countryCode: String) {
        val sharedPreferences = getSharedPreferences("country_code", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("country_code", "+$countryCode")
        editor.apply()
    }
}