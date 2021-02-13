package uk.ac.swansea.alexandru.dvmicc.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import uk.ac.swansea.alexandru.dvmicc.R

class IntroductionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val introductionRootView = inflater.inflate(R.layout.introduction_fragment, container, false)

        return introductionRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val whyICCtext : TextView = view.findViewById<TextView>(R.id.whyICCtext)
        whyICCtext.movementMethod = LinkMovementMethod.getInstance()

        val customPermissionXML = view.findViewById<CodeView>(R.id.permissionXML)
        setCodeViewOptions(customPermissionXML, "xml", R.string.customPermissionsCode)

        val implicitIntentCode : CodeView = view.findViewById(R.id.implicitIntentCode)
        setCodeViewOptions(implicitIntentCode, "java", R.string.implicitIntentCode)

        val intentFilterXML : CodeView = view.findViewById(R.id.intentFilterXML)
        setCodeViewOptions(intentFilterXML, "xml", R.string.intentFilterCode)
    }

    private fun setCodeViewOptions(codeView: CodeView, language: String, codeStringID: Int) {
        codeView.setOptions(Options.Default.get(context!!)
                .withLanguage(language)
                .withCode(getString(codeStringID))
                .withTheme(ColorTheme.MONOKAI)
                .withFormat(Format.Medium)
                .disableHighlightAnimation())
    }
}
