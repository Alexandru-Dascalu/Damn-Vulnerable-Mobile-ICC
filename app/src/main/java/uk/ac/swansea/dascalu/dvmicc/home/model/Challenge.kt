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
            mapOf("low" to SecurityLevel(R.string.broadcastTheftLowDescription, R.string.broadcastTheftLowManifest, R.string.broadcastTheftLowIntentCode),
                    "medium" to SecurityLevel(R.string.broadcastTheftMediumDescription, R.string.broadcastTheftMediumManifest, R.string.broadcastTheftMediumIntentCode),
                    "high" to SecurityLevel(R.string.broadcastTheftHighDescription, R.string.broadcastTheftHighManifest, R.string.broadcastTheftMediumIntentCode),
                    "very high" to SecurityLevel(R.string.broadcastTheftVeryHighDescription, R.string.broadcastTheftVeryHighManifest, R.string.broadcastTheftMediumIntentCode),
                    "impossible" to SecurityLevel(R.string.broadcastTheftImpossibleDescription, R.string.broadcastTheftLowManifest, R.string.broadcastTheftImpossibleIntentCode)),
            R.string.broadcastTheftExplanation, BroadcastTheftQuestionsFragment::class,
            R.string.broadcastTheftScenarioExplanation, R.string.broadcastTheftInstructions,
            R.string.broadcastTheftAPIRequirements)

}