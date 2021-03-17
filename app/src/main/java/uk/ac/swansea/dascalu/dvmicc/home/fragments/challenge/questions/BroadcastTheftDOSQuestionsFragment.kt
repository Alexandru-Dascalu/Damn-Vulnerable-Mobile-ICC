package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import uk.ac.swansea.dascalu.dvmicc.home.R

@SuppressLint("UseRequireInsteadOfGet")
class BroadcastTheftDOSQuestionsFragment : AbstractFullQuestionsFragment() {
    override var challengeName: String? = null
    override var vulnerableAppName: String? = null
    override var malwareName: String? = null
    override var securityLowFlag: String? = null
    override var securityMediumFlag: String? = null
    override var securityHighFlag: String? = null
    override var securityVeryHighFlag: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        challengeName = context!!.resources.getString(R.string.broadcastTheftDOSName)
        vulnerableAppName = context!!.resources.getString(R.string.callRedirectAppName)
        malwareName = context!!.resources.getString(R.string.callLogAppName)

        val flags = context!!.resources.getStringArray(R.array.broadcastTheftDOSFlags)
        securityLowFlag = flags[0]
        securityHighFlag = flags[1]

        super.onViewCreated(view, savedInstanceState)
    }
}