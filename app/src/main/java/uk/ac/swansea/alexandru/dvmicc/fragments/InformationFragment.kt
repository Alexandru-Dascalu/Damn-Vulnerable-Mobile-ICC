package uk.ac.swansea.alexandru.dvmicc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme

import uk.ac.swansea.alexandru.dvmicc.R

class InformationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val informationRootView = inflater.inflate(R.layout.information_fragment, container, false)

        return informationRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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