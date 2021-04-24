package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.textfield.TextInputLayout

import uk.ac.swansea.dascalu.dvmicc.home.R
import uk.ac.swansea.dascalu.dvmicc.home.hideKeyboard
import uk.ac.swansea.dascalu.dvmicc.home.model.ChallengeViewModel
import java.util.Locale

/**
 * Abstract super class for all questions fragments for challenges with all five security levels.
 */
@SuppressLint("UseRequireInsteadOfGet")
abstract class AbstractFullQuestionsFragment : Fragment() {
    protected abstract var challengeName : String?
    protected abstract var vulnerableAppName: String?
    protected abstract var malwareName: String?
    protected abstract var securityLowFlag: String?
    protected abstract var securityMediumFlag: String?
    protected abstract var securityHighFlag: String?
    protected abstract var securityVeryHighFlag: String?

    private lateinit var vulnerabilityCorrectCodeLine: String
    private lateinit var malwareGiveawayCorrectCodeLine: String

    private var answeredVulnerable: Boolean = false
    private var answeredMalware: Boolean = false
    private var answeredLow: Boolean = false
    private var answeredMedium: Boolean = false
    private var answeredHigh: Boolean = false
    private var answeredVeryHigh: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            answeredVulnerable = savedInstanceState.getBoolean("answeredVulnerable")
            answeredMalware = savedInstanceState.getBoolean("answeredMalware")
            answeredLow = savedInstanceState.getBoolean("answeredLow")
            answeredMedium = savedInstanceState.getBoolean("answeredMedium")
            answeredHigh = savedInstanceState.getBoolean("answeredHigh")
            answeredVeryHigh = savedInstanceState.getBoolean("answeredVeryHigh")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.full_questions_fragment, container, false)
    }

    /**
     * Sets the text for the questions for the five security level flags. Subclasses should override
     * this to set the text for the malware and vulnerable app questions, and they should call the
     * super class method before returning.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setCorrectAnswers(view)

        val securityLowTextView = view.findViewById<TextView>(R.id.securityLowQuestionTitle)
        securityLowTextView.text = requireContext().resources.getString(R.string.securityLowQuestion)

        val securityMediumTextView = view.findViewById<TextView>(R.id.securityMediumQuestionTitle)
        securityMediumTextView.text = requireContext().resources.getString(R.string.securityMediumQuestion)

        val securityHighTextView = view.findViewById<TextView>(R.id.securityHighQuestionTitle)
        securityHighTextView.text = requireContext().resources.getString(R.string.securityHighQuestion)

        val securityVeryHighTextView = view.findViewById<TextView>(R.id.securityVeryHighQuestionTitle)
        securityVeryHighTextView.text = requireContext().resources.getString(R.string.securityVeryHighQuestion)

        val vulnerableAppTextView = view.findViewById<TextView>(R.id.vulnerableAppQuestionTitle)
        vulnerableAppTextView.text = requireContext().resources.getString(
                R.string.vulnerableAppQuestion, challengeName)

        val malwareTextView = view.findViewById<TextView>(R.id.malwareQuestionTitle)
        malwareTextView.text = requireContext().resources.getString(
                R.string.malwareQuestion, challengeName)

        setButtonClickListeners(view)
        restoreAnswers(view)
        setupDropDowns(view)
    }

    private fun setCorrectAnswers(view: View) {
        vulnerabilityCorrectCodeLine = view.context.getString(ChallengeViewModel.instance.challenge
                .vulnerabilityCorrectCodeLine)
        malwareGiveawayCorrectCodeLine = view.context.getString(ChallengeViewModel.instance.challenge
                .malwareGiveawayCorrectCodeLine)
    }

    private fun setButtonClickListeners(view: View) {
        val vulnerableAppQuestionButton = view.findViewById<MaterialButton>(R.id.vulnerableAppQuestionButton)
        var editText = view.findViewById<EditText>(R.id.vulnerableAppEditText)
        var textInputLayout = view.findViewById<TextInputLayout>(R.id.vulnerableAppInput)
        vulnerableAppQuestionButton.setOnClickListener(QuestionButtonClickListener(vulnerableAppName,
                view, editText, textInputLayout))

        val malwareQuestionButton = view.findViewById<MaterialButton>(R.id.malwareQuestionButton)
        editText = view.findViewById<EditText>(R.id.malwareAppEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.malwareInput)
        malwareQuestionButton.setOnClickListener(QuestionButtonClickListener(malwareName,
                view, editText, textInputLayout))

        val securityLowButton = view.findViewById<MaterialButton>(R.id.securityLowButton)
        editText = view.findViewById<EditText>(R.id.securityLowEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.securityLowInput)
        securityLowButton.setOnClickListener(QuestionButtonClickListener(securityLowFlag,
                view, editText, textInputLayout))

        val securityMediumButton = view.findViewById<MaterialButton>(R.id.securityMediumButton)
        editText = view.findViewById<EditText>(R.id.securityMediumEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.securityMediumInput)
        securityMediumButton.setOnClickListener(QuestionButtonClickListener(securityMediumFlag,
                view, editText, textInputLayout))

        val securityHighButton = view.findViewById<MaterialButton>(R.id.securityHighButton)
        editText = view.findViewById<EditText>(R.id.securityHighEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.securityHighInput)
        securityHighButton.setOnClickListener(QuestionButtonClickListener(securityHighFlag,
                view, editText, textInputLayout))

        val securityVeryHighButton = view.findViewById<MaterialButton>(R.id.securityVeryHighButton)
        editText = view.findViewById<EditText>(R.id.securityVeryHighEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.securityVeryHighInput)
        securityVeryHighButton.setOnClickListener(QuestionButtonClickListener(securityVeryHighFlag,
                view, editText, textInputLayout))

        val vulnerabilitiesButton = view.findViewById<MaterialButton>(R.id.vulnerabilitiesQuestionButton)
        editText = view.findViewById<EditText>(R.id.vulnerabilityEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.vulnerabilitySpinner)
        vulnerabilitiesButton.setOnClickListener(QuestionButtonClickListener(vulnerabilityCorrectCodeLine,
                view, editText, textInputLayout))

        val malwareGiveawayButton = view.findViewById<MaterialButton>(R.id.malwareGiveawayQuestionButton)
        editText = view.findViewById<EditText>(R.id.malwareGiveawayEditText)
        textInputLayout = view.findViewById<TextInputLayout>(R.id.malwareGiveawaySpinner)
        malwareGiveawayButton.setOnClickListener(QuestionButtonClickListener(malwareGiveawayCorrectCodeLine,
                view, editText, textInputLayout))
    }

    private fun restoreAnswers(view: View) {
        val completedString = view.context.resources.getString(R.string.completed)
        if(answeredVulnerable) {
            val vulnerableAppQuestionButton = view.findViewById<MaterialButton>(R.id.vulnerableAppQuestionButton)
            vulnerableAppQuestionButton.text = completedString
            changeButtonColors(vulnerableAppQuestionButton)
            vulnerableAppQuestionButton.isEnabled = false

            val vulnerableEditText = view.findViewById<EditText>(R.id.vulnerableAppEditText)
            vulnerableEditText.isFocusable = false
        }

        if(answeredMalware) {
            val malwareQuestionButton = view.findViewById<MaterialButton>(R.id.malwareQuestionButton)
            malwareQuestionButton.text = completedString
            changeButtonColors(malwareQuestionButton)
            malwareQuestionButton.isEnabled = false

            val editText = view.findViewById<EditText>(R.id.malwareAppEditText)
            editText.isFocusable = false
        }

        if(answeredLow) {
            val securityLowButton = view.findViewById<MaterialButton>(R.id.securityLowButton)
            securityLowButton.text = completedString
            changeButtonColors(securityLowButton)
            securityLowButton.isEnabled = false

            val editText = view.findViewById<EditText>(R.id.securityLowEditText)
            editText.isFocusable = false
        }

        if(answeredMedium) {
            val securityMediumButton = view.findViewById<MaterialButton>(R.id.securityMediumButton)
            securityMediumButton.text = completedString
            changeButtonColors(securityMediumButton)
            securityMediumButton.isEnabled = false

            val editText = view.findViewById<EditText>(R.id.securityMediumEditText)
            editText.isFocusable = false
        }

        if(answeredHigh) {
            val securityHighButton = view.findViewById<MaterialButton>(R.id.securityHighButton)
            securityHighButton.text = completedString
            changeButtonColors(securityHighButton)
            securityHighButton.isEnabled = false

            val editText = view.findViewById<EditText>(R.id.securityHighEditText)
            editText.isFocusable = false
        }

        if(answeredVeryHigh) {
            val securityVeryHighButton = view.findViewById<MaterialButton>(R.id.securityVeryHighButton)
            securityVeryHighButton.text = completedString
            changeButtonColors(securityVeryHighButton)
            securityVeryHighButton.isEnabled = false

            val editText = view.findViewById<EditText>(R.id.securityVeryHighEditText)
            editText.isFocusable = false
        }
    }

    private fun setupDropDowns(view: View) {
        val vulnerabilityEditText = view.findViewById<TextInputLayout>(R.id.vulnerabilitySpinner)
                .editText as AutoCompleteTextView
        val vulnerabilityOptions = view.context.resources.getStringArray(ChallengeViewModel
                .instance.challenge.vulnerabilityOptions)
        val vulnerabilityOptionsAdapter = ArrayAdapter(view.context, R.layout.spinner_item,
                vulnerabilityOptions)
        vulnerabilityEditText.setAdapter(vulnerabilityOptionsAdapter)

        val malwareGiveawayEditText = view.findViewById<TextInputLayout>(R.id.malwareGiveawaySpinner)
                .editText as AutoCompleteTextView
        val malwareGiveawayOptions = view.context.resources.getStringArray(ChallengeViewModel
                .instance.challenge.malwareGiveawayOptions)
        val malwareGiveawayOptionsAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item,
                malwareGiveawayOptions)
        malwareGiveawayEditText.setAdapter(malwareGiveawayOptionsAdapter)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("answeredVulnerable", !(view!!.findViewById<MaterialButton>(
                R.id.vulnerableAppQuestionButton).isEnabled))
        outState.putBoolean("answeredMalware", !(view!!.findViewById<MaterialButton>(
                R.id.malwareQuestionButton).isEnabled))

        outState.putBoolean("answeredLow", !(view!!.findViewById<MaterialButton>(
                R.id.securityLowButton).isEnabled))
        outState.putBoolean("answeredMedium", !(view!!.findViewById<MaterialButton>(
                R.id.securityMediumButton).isEnabled))
        outState.putBoolean("answeredHigh", !(view!!.findViewById<MaterialButton>(
                R.id.securityHighButton).isEnabled))
        outState.putBoolean("answeredVeryHigh", !(view!!.findViewById<MaterialButton>(
                R.id.securityVeryHighButton).isEnabled))
    }

    private fun changeButtonColors(button: MaterialButton) {
        button.setBackgroundColor(MaterialColors.getColor(button.context, R.attr.colorBackgroundFloating,
                Color.BLACK))
        button.setTextColor(MaterialColors.getColor(button.context, R.attr.colorSecondary,
                Color.BLACK))
        button.strokeWidth = 5
        button.setStrokeColorResource(R.color.limeDark)
    }

    inner class QuestionButtonClickListener(private val correctAnswer: String?,
                                            private val rootview: View, private val editText: EditText,
                                            private val textInputLayout: TextInputLayout) : View.OnClickListener {

        override fun onClick(buttonView: View?) {
            val button: MaterialButton = buttonView as MaterialButton

            if(editText.text.toString().toLowerCase(Locale.ROOT) !=
                    correctAnswer!!.toLowerCase(Locale.ROOT)) {
                textInputLayout.error = rootview.context.resources.getString(R.string.wrongAnswer)
            } else {
                textInputLayout.error = null
                editText.isFocusable = false

                button.text = rootview.context.resources.getString(R.string.completed)
                changeButtonColors(button)
                button.isEnabled = false
                hideKeyboard(buttonView, context!!)
            }

            val vulnerableEditText = rootview.findViewById<EditText>(R.id.vulnerableAppEditText)
            val malwareEditText = rootview.findViewById<EditText>(R.id.malwareAppEditText)

            if(!malwareEditText.isFocusable && !vulnerableEditText.isFocusable) {
                ChallengeViewModel.instance.hasGuessedApps = true
            }

            if(checkHasCompletedChallenge()) {
                ChallengeViewModel.instance.hasCompletedChallenge = true
            }
        }

        private fun checkHasCompletedChallenge() : Boolean {
            val vulnerableEditText = rootview.findViewById<EditText>(R.id.vulnerableAppEditText)
            val malwareEditText = rootview.findViewById<EditText>(R.id.malwareAppEditText)
            val lowEditText = rootview.findViewById<EditText>(R.id.securityLowEditText)
            val mediumEditText = rootview.findViewById<EditText>(R.id.securityMediumEditText)
            val highEditText = rootview.findViewById<EditText>(R.id.securityHighEditText)
            val veryHighEditText = rootview.findViewById<EditText>(R.id.securityVeryHighEditText)

            val vulnerabilityCodeLineEditText = rootview.findViewById<EditText>(R.id.vulnerabilityEditText)
            val malwareGiveawayCodeLineEditText = rootview.findViewById<EditText>(R.id.malwareGiveawayEditText)

            return !vulnerableEditText.isFocusable && !malwareEditText.isFocusable &&
                    !lowEditText.isFocusable && !mediumEditText.isFocusable &&
                    !highEditText.isFocusable && !veryHighEditText.isFocusable &&
                    !vulnerabilityCodeLineEditText.isFocusable && !malwareGiveawayCodeLineEditText.isFocusable
        }
    }
}