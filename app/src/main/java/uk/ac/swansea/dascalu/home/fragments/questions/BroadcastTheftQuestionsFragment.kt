package uk.ac.swansea.dascalu.home.fragments.questions

import android.os.Bundle
import android.view.View
import android.widget.TextView

import uk.ac.swansea.dascalu.home.R

class BroadcastTheftQuestionsFragment() : AbstractFullQuestionsFragment() {
    override var vulnerableAppName: String? = null
    override var malwareName: String? = null
    override var securityLowFlag: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vulnerableAppTextView = view.findViewById<TextView>(R.id.vulnerableAppQuestionTextView)
        vulnerableAppTextView.text = requireContext().resources.getString(R.string.vulnerableAppQuestion,
                requireContext().resources.getString(R.string.broadcastTheftName))

        val malwareTextView = view.findViewById<TextView>(R.id.malwareQuestionTextView)
        malwareTextView.text = requireContext().resources.getString(R.string.malwareQuestion,
                requireContext().resources.getString(R.string.broadcastTheftName))

        vulnerableAppName = requireContext().resources.getString(R.string.newsAggregatorAppName)
        malwareName = requireContext().resources.getString(R.string.callLogAppName)
        securityLowFlag = requireContext().resources.getStringArray(R.array.broadcastTheftFlags)[0]

        super.onViewCreated(view, savedInstanceState)
    }
}