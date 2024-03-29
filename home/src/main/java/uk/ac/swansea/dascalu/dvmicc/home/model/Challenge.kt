package uk.ac.swansea.dascalu.dvmicc.home.model

import uk.ac.swansea.dascalu.dvmicc.home.R

enum class Challenge(val challengeNameIndex: Int, val attackExplanation: Int, val securityLevels: Map<String, SecurityLevel>,
                     val instructions: Int, val scenarioConclusion: Int, val vulnerabilityOptions: Int,
                     val vulnerabilityCorrectCodeLine: Int, val malwareGiveawayOptions: Int, val malwareGiveawayCorrectCodeLine: Int) {

    BROADCAST_THEFT(0,  R.string.broadcastTheftExplanation,
            mapOf("low" to SecurityLevel(R.string.broadcastTheftLowDescription, R.string.newsAggregatorLowManifest, R.string.newsAggregatorLowIntentCode),
                    "medium" to SecurityLevel(R.string.broadcastTheftMediumDescription, R.string.newsAggregatorMediumManifest, R.string.newsAggregatorMediumIntentCode),
                    "high" to SecurityLevel(R.string.broadcastTheftHighDescription, R.string.newsAggregatorHighManifest, R.string.newsAggregatorMediumIntentCode),
                    "very high" to SecurityLevel(R.string.broadcastTheftVeryHighDescription, R.string.newsAggregatorVeryHighManifest, R.string.newsAggregatorMediumIntentCode),
                    "impossible" to SecurityLevel(R.string.broadcastTheftImpossibleDescription, R.string.newsAggregatorLowManifest, R.string.newsAggregatorImpossibleIntentCode)),
            R.string.broadcastTheftInstructions, R.string.broadcastTheftScenarioConclusion,
            R.array.broadcastTheftVulnerabilityQuestionOptions, R.string.broadcastTheftVulnerabilityCorrectAnswer,
            R.array.broadcastTheftMalwareGiveawayOptions, R.string.broadcastTheftMalwareGiveawayCorrectAnswer),

    BROADCAST_THEFT_DOS(1, R.string.broadcastTheftDOSExplanation,
            mapOf("low" to SecurityLevel(R.string.broadcastTheftDOSLowDescription, R.string.callRedirectLowManifest, R.string.callRedirectLowIntentCode),
                    "high" to SecurityLevel(R.string.broadcastTheftDOSHighDescription, R.string.callRedirectHighManifest, R.string.callRedirectHighIntentCode),
                    "impossible" to SecurityLevel(R.string.broadcastTheftDOSImpossibleDescription)),
            R.string.broadcastTheftDOSInstructions, R.string.broadcastTheftDOSScenarioExplanation,
            R.array.broadcastTheftDOSVulnerabilityOptions, R.string.broadcastTheftDOSVulnerabilityCorrectAnswer,
            R.array.broadcastTheftDOSMalwareGiveawayOptions, R.string.broadcastTheftDOSMalwareGiveawayCorrectAnswer),

    BROADCAST_THEFT_MITM(2, R.string.broadcastTheftMITMExplanation,
            mapOf("low" to SecurityLevel(R.string.broadcastTheftMITMLowDescription, R.string.callRedirectLowManifest, R.string.callRedirectLowIntentCode),
                    "high" to SecurityLevel(R.string.broadcastTheftMITMHighDescription, R.string.callRedirectHighManifest, R.string.callRedirectHighIntentCode),
                    "impossible" to SecurityLevel(R.string.broadcastTheftMITMImpossibleDescription)),
            R.string.broadcastTheftMITMInstructions, R.string.broadcastTheftMITMScenarioExplanation,
            R.array.broadcastTheftDOSVulnerabilityOptions, R.string.broadcastTheftDOSVulnerabilityCorrectAnswer,
            R.array.broadcastTheftMITMMalwareGiveawayOptions, R.string.broadcastTheftMITMMalwareGiveawayCorrectAnswer),

    ACTIVITY_INTENT_HIJACK(3, R.string.activityHijackExplanation,
            mapOf("low" to SecurityLevel(R.string.activityHijackLowDescription, R.string.swanBankManifest, R.string.swanBankLowIntentCode),
                    "impossible" to SecurityLevel(R.string.activityHijackImpossibleDescription, intentCodeID=R.string.swanBankImpossibleIntentCode)),
            R.string.activityHijackInstructions, R.string.activityHijackScenarioExplanation,
            R.array.activityHijackVulnerabilityOptions, R.string.activityHijackVulnerabilityCorrectAnswer,
            R.array.activityHijackMalwareGiveawayOptions, R.string.activityHijackMalwareGiveawayCorrectAnswer),

    CONTENT_PROVIDER_URI_HIJACK(4, R.string.contentProviderHijackingExplanation,
            mapOf("low" to SecurityLevel(R.string.providerHijackLowDescription, R.string.fastChatLowManifest, R.string.fastChatLowIntentCode),
                    "high" to SecurityLevel(R.string.providerHijackHighDescription, R.string.fastChatHighManifest),
                    "impossible" to SecurityLevel(R.string.providerHijackImpossibleDescription, intentCodeID = R.string.fastChatImpossibleIntentCode)),
            R.string.contentProviderHijackInstructions, R.string.contentProviderHijackScenarioConclusion,
            R.array.providerHijackVulnerabilityOptions, R.string.providerHijackVulnerabilityCorrectAnswer,
            R.array.providerHijackMalwareGiveawayOptions,R.string.providerHijackMalwareGiveawayCorrectAnswer)
}

data class SecurityLevel(val explanationID: Int, val manifestID: Int? = null, val intentCodeID: Int? = null)