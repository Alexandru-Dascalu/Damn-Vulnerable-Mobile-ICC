package uk.ac.swansea.dascalu.dvmicc.home.model

import uk.ac.swansea.dascalu.dvmicc.home.R

enum class Challenge(val challengeNameIndex: Int, val attackExplanation: Int, val securityLevels: Map<String, SecurityLevel>,
                     val instructions: Int, val scenarioExplanation: Int, val apiRequirements: Int) {

    BROADCAST_THEFT(0,  R.string.broadcastTheftExplanation,
            mapOf("low" to SecurityLevel(R.string.broadcastTheftLowDescription, R.string.newsAggregatorLowManifest, R.string.newsAggregatorLowIntentCode),
                    "medium" to SecurityLevel(R.string.broadcastTheftMediumDescription, R.string.newsAggregatorMediumManifest, R.string.newsAggregatorMediumIntentCode),
                    "high" to SecurityLevel(R.string.broadcastTheftHighDescription, R.string.newsAggregatorHighManifest, R.string.newsAggregatorMediumIntentCode),
                    "very high" to SecurityLevel(R.string.broadcastTheftVeryHighDescription, R.string.newsAggregatorVeryHighManifest, R.string.newsAggregatorMediumIntentCode),
                    "impossible" to SecurityLevel(R.string.broadcastTheftImpossibleDescription, R.string.newsAggregatorLowManifest, R.string.newsAggregatorImpossibleIntentCode)),
            R.string.broadcastTheftInstructions, R.string.broadcastTheftScenarioExplanation,
            R.string.broadcastTheftAPIRequirements),

    BROADCAST_THEFT_DOS(1, R.string.broadcastTheftDOSExplanation,
            mapOf("low" to SecurityLevel(R.string.broadcastTheftDOSLowDescription, R.string.callRedirectLowManifest, R.string.callRedirectLowIntentCode),
                    "high" to SecurityLevel(R.string.broadcastTheftDOSHighDescription, R.string.callRedirectHighManifest, R.string.callRedirectHighIntentCode),
                    "impossible" to SecurityLevel(R.string.broadcastTheftDOSImpossibleDescription)),
            R.string.broadcastTheftDOSInstructions, 0, 0),

    BROADCAST_THEFT_MITM(2, R.string.broadcastTheftMITMExplanation,
            mapOf("low" to SecurityLevel(R.string.broadcastTheftMITMLowDescription, R.string.callRedirectLowManifest, R.string.callRedirectLowIntentCode),
                    "high" to SecurityLevel(R.string.broadcastTheftMITMHighDescription, R.string.callRedirectHighManifest, R.string.callRedirectHighIntentCode),
                    "impossible" to SecurityLevel(R.string.broadcastTheftMITMImpossibleDescription)),
            R.string.broadcastTheftMITMInstructions, 0, 0),

    ACTIVITY_INTENT_HIJACK(3, R.string.activityHijackExplanation,
            mapOf("low" to SecurityLevel(R.string.activityHijackLowDescription, R.string.santanderManifest, R.string.santanderLowIntentCode),
                    "impossible" to SecurityLevel(R.string.activityHijackImpossibleDescription, R.string.santanderImpossibleIntentCode)),
            R.string.activityHijackInstructions, 0, 0)
}

data class SecurityLevel(val explanationID: Int, val manifestID: Int? = null, val intentCodeID: Int? = null)