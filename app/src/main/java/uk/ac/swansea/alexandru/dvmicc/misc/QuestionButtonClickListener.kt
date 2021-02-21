package uk.ac.swansea.alexandru.dvmicc.misc

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.textfield.TextInputLayout
import uk.ac.swansea.alexandru.dvmicc.R

class QuestionButtonClickListener(private val correctAnswer: String?,
                                  private val context: Context, private val editText: EditText,
                                  private val textInputLayout: TextInputLayout) : View.OnClickListener {

    override fun onClick(buttonView: View?) {
        val button: MaterialButton = buttonView as MaterialButton

        if(editText.text.toString() != correctAnswer) {
            textInputLayout.error = context.resources.getString(R.string.wrongAnswer)
        } else {
            textInputLayout.error = null
            editText.isFocusable = false

            button.text = context.resources.getString(R.string.completed)
            changeButtonColors(button)
            button.isEnabled = false
        }
    }

    private fun changeButtonColors(button: MaterialButton) {
        button.setBackgroundColor(MaterialColors.getColor(context, R.attr.colorBackgroundFloating,
                Color.BLACK))
        button.setTextColor(MaterialColors.getColor(context, R.attr.colorSecondary,
                Color.BLACK))
        button.strokeWidth = 5
        button.setStrokeColorResource(R.color.limeDark)
    }
}