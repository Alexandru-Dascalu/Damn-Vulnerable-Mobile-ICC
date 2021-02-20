package uk.ac.swansea.alexandru.dvmicc.fragments.questions

import android.os.Bundle
import android.view.View
import android.widget.TextView
import uk.ac.swansea.alexandru.dvmicc.R

class BroadcastTheftQuestionsFragment() : AbstractFullQuestionsFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vulnerableAppTextView = view.findViewById<TextView>(R.id.vulnerableAppQuestionTextView)
        vulnerableAppTextView.text = context!!.resources.getString(R.string.vulnerableAppQuestion,
                context!!.resources.getString(R.string.broadcastTheftName))

        val malwareTextView = view.findViewById<TextView>(R.id.malwareQuestionTextView)
        malwareTextView.text = context!!.resources.getString(R.string.malwareQuestion,
                context!!.resources.getString(R.string.broadcastTheftName))

        super.onViewCreated(view, savedInstanceState)
    }
}