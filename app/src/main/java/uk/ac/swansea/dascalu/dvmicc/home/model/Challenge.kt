package uk.ac.swansea.dascalu.dvmicc.home.model

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

import uk.ac.swansea.dascalu.dvmicc.home.R
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.BroadcastTheftQuestionsFragment

enum class Challenge(val challengeNameIndex: Int, val securityLevels: Map<String, SecurityLevel>,
                     val attackExplanation: Int, val scenarioExplanation: Int,
                     val scenarioInstructions: Int, val apiRequirements: Int) {

    BROADCAST_THEFT(0,
            mapOf("low" to SecurityLevel(R.string.broadcastTheftLowDescription, R.string.newsAggregatorLowManifest, R.string.newsAggregatorLowIntentCode),
                    "medium" to SecurityLevel(R.string.broadcastTheftMediumDescription, R.string.newsAggregatorMediumManifest, R.string.newsAggregatorMediumIntentCode),
                    "high" to SecurityLevel(R.string.broadcastTheftHighDescription, R.string.newsAggregatorHighManifest, R.string.newsAggregatorMediumIntentCode),
                    "very high" to SecurityLevel(R.string.broadcastTheftVeryHighDescription, R.string.newsAggregatorVeryHighManifest, R.string.newsAggregatorMediumIntentCode),
                    "impossible" to SecurityLevel(R.string.broadcastTheftImpossibleDescription, R.string.newsAggregatorLowManifest, R.string.newsAggregatorImpossibleIntentCode)),
            R.string.broadcastTheftExplanation,
            R.string.broadcastTheftScenarioExplanation, R.string.broadcastTheftInstructions,
            R.string.broadcastTheftAPIRequirements),
    BROADCAST_THEFT_DOS(1, mapOf("low" to SecurityLevel()), R.string.broadcastTheftDOSExplanation, 0, 0, 0)

}