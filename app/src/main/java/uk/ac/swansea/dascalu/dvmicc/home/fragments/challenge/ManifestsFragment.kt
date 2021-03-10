package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import io.github.kbiakov.codeview.CodeView

import uk.ac.swansea.dascalu.dvmicc.home.R
import uk.ac.swansea.dascalu.dvmicc.home.setCodeViewOptions

@SuppressLint("UseRequireInsteadOfGet")
class ManifestsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val manifestsRootView = inflater.inflate(R.layout.manifests_fragment, container, false)

        return manifestsRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val callLoggerXML = view.findViewById<CodeView>(R.id.callLogManifest)
        setCodeViewOptions(context!!, callLoggerXML, "xml", R.string.callLoggerManifest)

        val newsAggregatorXML = view.findViewById<CodeView>(R.id.newsAggregatorManifest)
        setCodeViewOptions(context!!, newsAggregatorXML, "xml", R.string.newsAggregatorLowManifest)

        val newsAggregatorBroadcastCode = view.findViewById<CodeView>(R.id.newsAggregatorBroadcastCode)
        setCodeViewOptions(context!!, newsAggregatorBroadcastCode, "java", R.string.newsAggregatorLowIntentCode)

        val newsAggregatorLoginCode = view.findViewById<CodeView>(R.id.newsAggregatorLoginIntentCode)
        setCodeViewOptions(context!!, newsAggregatorLoginCode, "java", R.string.newsAggregatorLoginIntentCode)

        val newsAggregatorSignUpIntentCode = view.findViewById<CodeView>(R.id.newsAggregatorSignUpIntentCode)
        setCodeViewOptions(context!!, newsAggregatorSignUpIntentCode, "java", R.string.newsAggregatorSignUpCode)

        val newsAggregatorLoadingCode = view.findViewById<CodeView>(R.id.newsAggregatorLoadingCode)
        setCodeViewOptions(context!!, newsAggregatorLoadingCode, "java", R.string.newsAggregatorLoadingCode)

        val newsAggregatorArticleIntentCode = view.findViewById<CodeView>(R.id.newsAggregatorArticleIntentCode)
        setCodeViewOptions(context!!, newsAggregatorArticleIntentCode, "java", R.string.newsAggregatorArticleIntentCode)

        val callLoggerIntentCode = view.findViewById<CodeView>(R.id.callLoggerIntentCode)
        setCodeViewOptions(context!!, callLoggerIntentCode, "java", R.string.callLoggerIntentCode)
    }
}