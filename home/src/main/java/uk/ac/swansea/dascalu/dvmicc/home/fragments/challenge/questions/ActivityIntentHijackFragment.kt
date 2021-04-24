package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

import uk.ac.swansea.dascalu.dvmicc.home.R

class ActivityIntentHijackFragment : AbstractFullQuestionsFragment() {
    override var challengeName : String? = null
    override var vulnerableAppName: String? = null
    override var malwareName: String? = null
    override var securityLowFlag: String? = null
    override var securityMediumFlag: String? = null
    override var securityHighFlag: String? = null
    override var securityVeryHighFlag: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        challengeName = view.context.resources.getString(R.string.activityHijackName)
        vulnerableAppName = view.context.resources.getString(R.string.santanderAppName)
        malwareName = view.context.resources.getString(R.string.moneyManagerAppName)

        securityLowFlag = view.context.resources.getStringArray(R.array.activityHijackflags)[0]

        super.onViewCreated(view, savedInstanceState)
        hideElementsForUnusedSecurityLevels(view)
    }

    private fun hideElementsForUnusedSecurityLevels(view: View) {
        view.findViewById<TextView>(R.id.securityMediumQuestionTitle).visibility = View.GONE
        view.findViewById<TextInputLayout>(R.id.securityMediumInput).visibility = View.GONE
        view.findViewById<EditText>(R.id.securityMediumEditText).isFocusable = false
        view.findViewById<MaterialButton>(R.id.securityMediumButton).visibility = View.GONE

        view.findViewById<TextView>(R.id.securityHighQuestionTitle).visibility = View.GONE
        view.findViewById<TextInputLayout>(R.id.securityHighInput).visibility = View.GONE
        view.findViewById<EditText>(R.id.securityHighEditText).isFocusable = false
        view.findViewById<MaterialButton>(R.id.securityHighButton).visibility = View.GONE

        view.findViewById<TextView>(R.id.securityVeryHighQuestionTitle).visibility = View.GONE
        view.findViewById<TextInputLayout>(R.id.securityVeryHighInput).visibility = View.GONE
        view.findViewById<EditText>(R.id.securityVeryHighEditText).isFocusable = false
        view.findViewById<MaterialButton>(R.id.securityVeryHighButton).visibility = View.GONE
    }
}