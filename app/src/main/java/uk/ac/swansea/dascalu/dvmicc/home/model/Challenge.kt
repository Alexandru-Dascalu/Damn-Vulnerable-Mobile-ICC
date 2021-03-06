package uk.ac.swansea.dascalu.dvmicc.home.model

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

import uk.ac.swansea.dascalu.dvmicc.home.R
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftQuestionsFragment

enum class Challenge(val challengeNameIndex: Int, val securityLevels: Map<Int, Int>,
                     val attackExplanation: Int, val questionsFragment: KClass<out Fragment>,
                     val scenarioExplanation: Int, val scenarioInstructions: Int,
                     val apiRequirements: Int) {

    BROADCAST_THEFT(0,
            mapOf(R.string.lowSecurityLevel to R.string.broadcastTheftLow,
                    R.string.mediumSecurityLevel to R.string.broadcastTheftMedium,
                    R.string.highSecurityLevel to R.string.broadcastTheftHigh,
                    R.string.veryHighSecurityLevel to R.string.broadcastTheftVeryHigh,
                    R.string.impossibleSecurityLevel to R.string.broadcastTheftImpossible),
            R.string.broadcastTheftExplanation, BroadcastTheftQuestionsFragment::class,
            R.string.broadcastTheftScenarioExplanation, R.string.broadcastTheftInstructions,
            R.string.broadcastTheftAPIRequirements)

}