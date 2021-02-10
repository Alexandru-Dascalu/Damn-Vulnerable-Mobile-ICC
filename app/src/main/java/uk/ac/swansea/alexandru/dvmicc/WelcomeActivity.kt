package uk.ac.swansea.alexandru.dvmicc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView

import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val whyICCtext : TextView = findViewById<TextView>(R.id.whyICCtext)
        whyICCtext.movementMethod = LinkMovementMethod.getInstance()

        val codeView : CodeView = findViewById(R.id.code_view)
        codeView.setOptions(Options.Default.get(this)
                .withLanguage("java")
                .withCode(getString(R.string.implicitIntentCode))
                .disableHighlightAnimation())

        val codeViewXML : CodeView = findViewById(R.id.code_viewXML)
        codeViewXML.setOptions(Options.Default.get(this)
            .withLanguage("xml")
            .withCode(getString(R.string.intentFilterCode))
            .disableHighlightAnimation())
    }
}