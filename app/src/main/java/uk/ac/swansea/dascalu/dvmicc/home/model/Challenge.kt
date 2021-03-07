package uk.ac.swansea.dascalu.dvmicc.home.model

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

import uk.ac.swansea.dascalu.dvmicc.home.R
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftQuestionsFragment

enum class Challenge(val challengeNameIndex: Int, val securityLevels: Map<String, SecurityLevel>,
                     val attackExplanation: Int, val questionsFragment: KClass<out Fragment>,
                     val scenarioExplanation: Int, val scenarioInstructions: Int,
                     val apiRequirements: Int) {

    BROADCAST_THEFT(0,
            mapOf("low" to SecurityLevel(R.string.broadcastTheftLowDescription, R.string.broadcastTheftLowManifest),
                    "medium" to SecurityLevel(R.string.broadcastTheftMediumDescription, R.string.broadcastTheftMediumManifest),
                    "high" to SecurityLevel(R.string.broadcastTheftHighDescription, R.string.broadcastTheftHighManifest),
                    "very high" to SecurityLevel(R.string.broadcastTheftVeryHighDescription, R.string.broadcastTheftVeryHighManifest),
                    "impossible" to SecurityLevel(R.string.broadcastTheftImpossibleDescription, R.string.broadcastTheftLowManifest)),
            R.string.broadcastTheftExplanation, BroadcastTheftQuestionsFragment::class,
            R.string.broadcastTheftScenarioExplanation, R.string.broadcastTheftInstructions,
            R.string.broadcastTheftAPIRequirements)

}