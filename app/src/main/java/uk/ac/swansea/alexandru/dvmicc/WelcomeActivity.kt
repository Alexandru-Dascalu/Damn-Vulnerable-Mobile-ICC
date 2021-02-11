package uk.ac.swansea.alexandru.dvmicc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView

import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val whyICCtext : TextView = findViewById<TextView>(R.id.whyICCtext)
        whyICCtext.movementMethod = LinkMovementMethod.getInstance()

        val customPermissionXML = findViewById<CodeView>(R.id.permissionXML)
        setCodeViewOptions(customPermissionXML, "xml", R.string.customPermissionsCode)

        val implicitIntentCode : CodeView = findViewById(R.id.implicitIntentCode)
        setCodeViewOptions(implicitIntentCode, "java", R.string.implicitIntentCode)

        val intentFilterXML : CodeView = findViewById(R.id.intentFilterXML)
        setCodeViewOptions(intentFilterXML, "xml", R.string.intentFilterCode)
    }

    private fun setCodeViewOptions(codeView: CodeView, language: String, codeStringID: Int) {
        codeView.setOptions(Options.Default.get(this)
                .withLanguage(language)
                .withCode(getString(codeStringID))
                .withTheme(ColorTheme.MONOKAI)
                .withFormat(Format.Medium)
                .disableHighlightAnimation())
    }
}