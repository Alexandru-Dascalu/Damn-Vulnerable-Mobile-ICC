package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView

import uk.ac.swansea.dascalu.dvmicc.home.R

@SuppressLint("UseRequireInsteadOfGet")
class BroadcastTheftQuestionsFragment() : AbstractFullQuestionsFragment() {
    override var challengeName: String? = null
    override var vulnerableAppName: String? = null
    override var malwareName: String? = null
    override var securityLowFlag: String? = null
    override var securityMediumFlag: String? = null
    override var securityHighFlag: String? = null
    override var securityVeryHighFlag: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        challengeName = context!!.resources.getString(R.string.broadcastTheftName)
        vulnerableAppName = context!!.resources.getString(R.string.callRedirectAppName)
        malwareName = context!!.resources.getString(R.string.cpuBoosterAppName)

        val flags = requireContext().resources.getStringArray(R.array.broadcastTheftFlags)
        securityLowFlag = flags[0]
        securityMediumFlag = flags[1]
        securityHighFlag = flags[2]
        securityVeryHighFlag = flags[3]

        super.onViewCreated(view, savedInstanceState)
    }
}