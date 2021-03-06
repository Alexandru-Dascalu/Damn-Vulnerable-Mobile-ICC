package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import uk.ac.swansea.dascalu.dvmicc.home.R

class ChallengeInstructionsFragment(private val hasGuessedApps: Boolean,
                                    private val instructionsIndex: Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.challenge_instructions_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val instructionsTextView = view.findViewById<TextView>(R.id.instructions)

        if(hasGuessedApps) {
            instructionsTextView.text = getString(instructionsIndex)
        } else {
            instructionsTextView.text = getString(R.string.instructionsHidden)
        }
    }
}