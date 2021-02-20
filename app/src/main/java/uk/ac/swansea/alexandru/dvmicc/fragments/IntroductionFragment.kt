package uk.ac.swansea.alexandru.dvmicc.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import uk.ac.swansea.alexandru.dvmicc.R

class IntroductionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val introductionRootView = inflater.inflate(R.layout.introduction_fragment, container, false)

        return introductionRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val whyICCtext : TextView = view.findViewById<TextView>(R.id.whyICCtext)
        whyICCtext.movementMethod = LinkMovementMethod.getInstance()
    }
}
