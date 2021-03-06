package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge

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
        val callLoggerXML = view.findViewById<CodeView>(R.id.callLogManifest)
        setCodeViewOptions(callLoggerXML, "xml", R.string.callLoggerManifest)

        val newsAggregatorXML = view.findViewById<CodeView>(R.id.newsAggregatorManifest)
        setCodeViewOptions(newsAggregatorXML, "xml", R.string.newsAggregatorManifest)
    }

    private fun setCodeViewOptions(codeView: CodeView, language: String, codeStringID: Int) {
        codeView.setOptions(Options.Default.get(requireContext())
                .withLanguage(language)
                .withCode(getString(codeStringID))
                .withTheme(ColorTheme.MONOKAI)
                .withFormat(Format.ExtraCompact)
                .disableHighlightAnimation())
    }
}