package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions

import android.os.Bundle
import android.view.View
import android.widget.TextView

import uk.ac.swansea.dascalu.dvmicc.home.R

class BroadcastTheftQuestionsFragment() : AbstractFullQuestionsFragment() {
    override var vulnerableAppName: String? = null
    override var malwareName: String? = null
    override var securityLowFlag: String? = null
    override var securityMediumFlag: String? = null
    override var securityHighFlag: String? = null
    override var securityVeryHighFlag: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vulnerableAppTextView = view.findViewById<TextView>(R.id.vulnerableAppQuestionTextView)
        vulnerableAppTextView.text = requireContext().resources.getString(R.string.vulnerableAppQuestion,
                requireContext().resources.getString(R.string.broadcastTheftName))

        val malwareTextView = view.findViewById<TextView>(R.id.malwareQuestionTextView)
        malwareTextView.text = requireContext().resources.getString(R.string.malwareQuestion,
                requireContext().resources.getString(R.string.broadcastTheftName))

        vulnerableAppName = requireContext().resources.getString(R.string.newsAggregatorAppName)
        malwareName = requireContext().resources.getString(R.string.callLogAppName)

        val flags = requireContext().resources.getStringArray(R.array.broadcastTheftFlags)
        securityLowFlag = flags[0]
        securityMediumFlag = flags[1]
        securityHighFlag = flags[2]
        securityVeryHighFlag = flags[3]

        super.onViewCreated(view, savedInstanceState)
    }
}