package uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import uk.ac.swansea.dascalu.dvmicc.home.R

@SuppressLint("UseRequireInsteadOfGet")
class BroadcastTheftMITMQuestionsFragment : AbstractFullQuestionsFragment() {
    override var challengeName : String? = null
    override var vulnerableAppName: String? = null
    override var malwareName: String? = null
    override var securityLowFlag: String? = null
    override var securityMediumFlag: String? = null
    override var securityHighFlag: String? = null
    override var securityVeryHighFlag: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        challengeName = view.context.resources.getString(R.string.broadcastTheftMITMName)
        vulnerableAppName = view.context.resources.getString(R.string.callRedirectAppName)
        malwareName = view.context.resources.getString(R.string.batteryBoosterName)

        val flags = view.context.resources.getStringArray(R.array.broadcastTheftMITMFlags)
        securityLowFlag = flags[0]
        securityHighFlag = flags[1]

        super.onViewCreated(view, savedInstanceState)
    }
}