package uk.ac.swansea.alexandru.dvmicc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import uk.ac.swansea.alexandru.dvmicc.R

class ChallengeInformationFragment(private val challengeNameIndex: Int,
                                   private val attackExplanationId: Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val challengeInformationRootView = inflater.inflate(R.layout.challenge_information_fragment, container, false)

        return challengeInformationRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val attackTypeHeader = view.findViewById<TextView>(R.id.attackClassHeader)
        val attackTypeText = view.findViewById<TextView>(R.id.attackClassText)

        val attackHeader = view.findViewById<TextView>(R.id.attackHeader)
        val attackText = view.findViewById<TextView>(R.id.attackText)

        if(challengeNameIndex < 7) {
            attackTypeHeader.text = resources.getString(R.string.intentHijackingHeading)
            attackTypeText.text = resources.getString(R.string.intentHijackingText)
        } else {
            attackTypeHeader.text = resources.getString(R.string.intentSpoofingHeading)
            attackTypeText.text = resources.getString(R.string.intentSpoofingText)
        }

        attackHeader.text = resources.getStringArray(R.array.challenges)[challengeNameIndex]
        attackText.text = resources.getString(attackExplanationId)
    }
}