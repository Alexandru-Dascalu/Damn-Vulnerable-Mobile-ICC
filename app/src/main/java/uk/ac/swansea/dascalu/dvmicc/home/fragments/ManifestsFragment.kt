package uk.ac.swansea.dascalu.dvmicc.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme

import uk.ac.swansea.dascalu.dvmicc.home.R

class ManifestsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val manifestsRootView = inflater.inflate(R.layout.manifests_fragment, container, false)

        return manifestsRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val customPermissionXML = view.findViewById<CodeView>(R.id.broadcastTheftManifest)
        setCodeViewOptions(customPermissionXML, "xml", R.string.customPermissionsCode)
    }

    private fun setCodeViewOptions(codeView: CodeView, language: String, codeStringID: Int) {
        codeView.setOptions(Options.Default.get(requireContext())
                .withLanguage(language)
                .withCode(getString(codeStringID))
                .withTheme(ColorTheme.MONOKAI)
                .withFormat(Format.Medium)
                .disableHighlightAnimation())
    }
}