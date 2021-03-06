package uk.ac.swansea.dascalu.dvmicc.home.misc

import android.graphics.Color
import android.view.View
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.textfield.TextInputLayout

import uk.ac.swansea.dascalu.dvmicc.home.ChallengeActivity
import uk.ac.swansea.dascalu.dvmicc.home.R

class QuestionButtonClickListener(private val correctAnswer: String?,
                                  private val view: View, private val editText: EditText,
                                  private val textInputLayout: TextInputLayout) : View.OnClickListener {

    override fun onClick(buttonView: View?) {
        val button: MaterialButton = buttonView as MaterialButton

        if(editText.text.toString() != correctAnswer) {
            textInputLayout.error = view.context.resources.getString(R.string.wrongAnswer)
        } else {
            textInputLayout.error = null
            editText.isFocusable = false

            button.text = view.context.resources.getString(R.string.completed)
            changeButtonColors(button)
            button.isEnabled = false
        }

        val vulnerableEditText = view.findViewById<EditText>(R.id.vulnerableAppEditText)
        val malwareEditText = view.findViewById<EditText>(R.id.malwareAppEditText)

        if(!malwareEditText.isFocusable && !vulnerableEditText.isFocusable) {
            val activity = view.context as ChallengeActivity
            activity.hasGuessedApps = true
        }
    }

    private fun changeButtonColors(button: MaterialButton) {
        button.setBackgroundColor(MaterialColors.getColor(view.context, R.attr.colorBackgroundFloating,
                Color.BLACK))
        button.setTextColor(MaterialColors.getColor(view.context, R.attr.colorSecondary,
                Color.BLACK))
        button.strokeWidth = 5
        button.setStrokeColorResource(R.color.limeDark)
    }
}