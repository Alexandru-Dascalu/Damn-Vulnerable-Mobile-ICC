package uk.ac.swansea.alexandru.dvmicc.model

import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

import uk.ac.swansea.alexandru.dvmicc.R
import uk.ac.swansea.alexandru.dvmicc.questionsActivities.BroadcastTheftQuestionsActivity

enum class Challenge(val challengeNameIndex: Int, val malwareName: Int, val vulnerableAppName: Int,
                     val securityLevels: Map<Int, Int>, val attackExplanation: Int,
                     val questionsActivity: KClass<out AppCompatActivity>, val scenarioExplanation: Int,
                     val flags: Int, val apiRequirements: Int) {

    BROADCAST_THEFT(0, R.string.callLogAppName, R.string.newsAggregatorAppName,
            mapOf(R.string.lowSecurityLevel to R.string.broadcastTheftLow,
                    R.string.mediumSecurityLevel to R.string.broadcastTheftMedium,
                    R.string.highSecurityLevel to R.string.broadcastTheftHigh,
                    R.string.veryHighSecurityLevel to R.string.broadcastTheftVeryHigh,
                    R.string.impossibleSecurityLevel to R.string.broadcastTheftImpossible),
            R.string.broadcastTheftExaplanation, BroadcastTheftQuestionsActivity::class,
            R.string.broadcastTheftScenarioExplanation, R.array.broadcastTheftFlags,
            R.string.broadcastTheftAPIRequirements)

}