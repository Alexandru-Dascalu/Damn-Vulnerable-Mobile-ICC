package uk.ac.swansea.alexandru.dvmicc.fragments.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

import uk.ac.swansea.alexandru.dvmicc.R
import uk.ac.swansea.alexandru.dvmicc.misc.QuestionButtonClickListener

/**
 * Abstract super class for all questions fragments for challenges with all five security levels.
 */
abstract class AbstractFullQuestionsFragment : Fragment() {
    protected abstract var vulnerableAppName: String?
    protected abstract var malwareName: String?
    protected abstract var securityLowFlag: String?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val questionsRootView = inflater.inflate(R.layout.full_questions_fragment, container, false)

        return questionsRootView
    }

    /**
     * Sets the text for the questions for the five security level flags. Subclasses should override
     * this to set the text for the malware and vulnerable app questions, and they should call the
     * super class method before returning.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val securityLowTextView = view.findViewById<TextView>(R.id.securityLowQuestionTextView)
        securityLowTextView.text = context!!.resources.getString(R.string.securityLowQuestion)

        val securityMediumTextView = view.findViewById<TextView>(R.id.securityMediumQuestionTextView)
        securityMediumTextView.text = context!!.resources.getString(R.string.securityMediumQuestion)

        val securityHighTextView = view.findViewById<TextView>(R.id.securityHighQuestionTextView)
        securityHighTextView.text = context!!.resources.getString(R.string.securityHighQuestion)

        val securityVeryHighTextView = view.findViewById<TextView>(R.id.securityVeryHighQuestionTextView)
        securityVeryHighTextView.text = context!!.resources.getString(R.string.securityVeryHighQuestion)

        setButtonClickListeners(view)
    }

    private fun setButtonClickListeners(view: View) {
        val vulnerableAppQuestionButton = view.findViewById<MaterialButton>(R.id.vulnerableAppQuestionButton)
        var editText = view.findViewById<EditText>(R.id.vulnerableAppEditText)
        var textInputLayout = view.findViewById<TextInputLayout>(R.id.vulnerableAppInput)
        vulnerableAppQuestionButton.setOnClickListener(QuestionButtonClickListener(vulnerableAppName,
                view.context, editText, textInputLayout))

        val malwareQuestionButton = view.findViewById<MaterialButton>(R.id.malwareQuestionButton)
        editText = view.findViewById<EditText>(R.id.malwareAppEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.malwareInput)
        malwareQuestionButton.setOnClickListener(QuestionButtonClickListener(malwareName,
                view.context, editText, textInputLayout))

        val securityLowButton = view.findViewById<MaterialButton>(R.id.securityLowButton)
        editText = view.findViewById<EditText>(R.id.securityLowEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.securityLowInput)
        securityLowButton.setOnClickListener(QuestionButtonClickListener(securityLowFlag,
                view.context, editText, textInputLayout))

        val securityMediumButton = view.findViewById<MaterialButton>(R.id.securityMediumButton)
        editText = view.findViewById<EditText>(R.id.securityMediumEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.securityMediumInput)
        securityMediumButton.setOnClickListener(QuestionButtonClickListener(securityLowFlag,
                view.context, editText, textInputLayout))

        val securityHighButton = view.findViewById<MaterialButton>(R.id.securityHighButton)
        editText = view.findViewById<EditText>(R.id.securityHighEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.securityHighInput)
        securityHighButton.setOnClickListener(QuestionButtonClickListener(securityLowFlag,
                view.context, editText, textInputLayout))

        val securityVeryHighButton = view.findViewById<MaterialButton>(R.id.securityVeryHighButton)
        editText = view.findViewById<EditText>(R.id.securityVeryHighEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.securityVeryHighInput)
        securityVeryHighButton.setOnClickListener(QuestionButtonClickListener(securityLowFlag,
                view.context, editText, textInputLayout))
    }
}