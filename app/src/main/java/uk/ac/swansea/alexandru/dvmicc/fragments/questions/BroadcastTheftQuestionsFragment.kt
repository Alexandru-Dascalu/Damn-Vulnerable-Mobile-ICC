package uk.ac.swansea.alexandru.dvmicc.fragments.questions

import android.os.Bundle
import android.view.View
import android.widget.TextView

import uk.ac.swansea.alexandru.dvmicc.R

class BroadcastTheftQuestionsFragment() : AbstractFullQuestionsFragment() {
    override var vulnerableAppName: String? = null
    override var malwareName: String? = null
    override var securityLowFlag: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vulnerableAppTextView = view.findViewById<TextView>(R.id.vulnerableAppQuestionTextView)
        vulnerableAppTextView.text = context!!.resources.getString(R.string.vulnerableAppQuestion,
                context!!.resources.getString(R.string.broadcastTheftName))

        val malwareTextView = view.findViewById<TextView>(R.id.malwareQuestionTextView)
        malwareTextView.text = context!!.resources.getString(R.string.malwareQuestion,
                context!!.resources.getString(R.string.broadcastTheftName))

        vulnerableAppName = context!!.resources.getString(R.string.newsAggregatorAppName)
        malwareName = context!!.resources.getString(R.string.callLogAppName)
        securityLowFlag = context!!.resources.getStringArray(R.array.broadcastTheftFlags)[0]

        super.onViewCreated(view, savedInstanceState)
    }
}