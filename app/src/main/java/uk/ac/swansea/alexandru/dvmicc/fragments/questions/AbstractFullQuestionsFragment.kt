package uk.ac.swansea.alexandru.dvmicc.fragments.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import uk.ac.swansea.alexandru.dvmicc.R

abstract class AbstractFullQuestionsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val questionsRootView = inflater.inflate(R.layout.full_questions_fragment, container, false)

        return questionsRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val securityLowTextView = view.findViewById<TextView>(R.id.securityLowQuestionTextView)
        securityLowTextView.text = context!!.resources.getString(R.string.securityLowQuestion)

        val securityMediumTextView = view.findViewById<TextView>(R.id.securityMediumQuestionTextView)
        securityMediumTextView.text = context!!.resources.getString(R.string.securityLowQuestion)

        val securityHighTextView = view.findViewById<TextView>(R.id.securityHighQuestionTextView)
        securityHighTextView.text = context!!.resources.getString(R.string.securityLowQuestion)

        val securityVeryHighTextView = view.findViewById<TextView>(R.id.securityVeryHighQuestionTextView)
        securityVeryHighTextView.text = context!!.resources.getString(R.string.securityVeryHighQuestion)

        val securityImpossibleTextView = view.findViewById<TextView>(R.id.securityImpossibleQuestionTextView)
        securityImpossibleTextView.text = context!!.resources.getString(R.string.securityImpossibleQuestion)
    }
}