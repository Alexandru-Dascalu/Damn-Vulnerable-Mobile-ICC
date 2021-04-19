package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

import uk.ac.swansea.dascalu.dvmicc.home.R

class ContentProviderUriHijackQuestionsFragment : AbstractFullQuestionsFragment() {
    override var challengeName : String? = null
    override var vulnerableAppName: String? = null
    override var malwareName: String? = null
    override var securityLowFlag: String? = null
    override var securityMediumFlag: String? = null
    override var securityHighFlag: String? = null
    override var securityVeryHighFlag: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        challengeName = view.context.resources.getString(R.string.providerHijackName)
        vulnerableAppName = view.context.resources.getString(R.string.whatsappAppName)
        malwareName = view.context.resources.getString(R.string.smsMessagesAppName)

        val flags = view.context.resources.getStringArray(R.array.providerHijackFlags)
        securityLowFlag = flags[0]
        securityHighFlag = flags[1]

        super.onViewCreated(view, savedInstanceState)
        hideElementsForUnusedSecurityLevels(view)
    }

    private fun hideElementsForUnusedSecurityLevels(view: View) {
        view.findViewById<TextView>(R.id.securityMediumQuestionTextView).visibility = View.GONE
        view.findViewById<TextInputLayout>(R.id.securityMediumInput).visibility = View.GONE
        view.findViewById<EditText>(R.id.securityMediumEditText).isFocusable = false
        view.findViewById<MaterialButton>(R.id.securityMediumButton).visibility = View.GONE

        view.findViewById<TextView>(R.id.securityVeryHighQuestionTextView).visibility = View.GONE
        view.findViewById<TextInputLayout>(R.id.securityVeryHighInput).visibility = View.GONE
        view.findViewById<EditText>(R.id.securityVeryHighEditText).isFocusable = false
        view.findViewById<MaterialButton>(R.id.securityVeryHighButton).visibility = View.GONE
    }
}