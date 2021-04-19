package uk.ac.swansea.dascalu.dvmicc.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ChallengeInformationFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ChallengeInstructionsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.ManifestsFragment
import uk.ac.swansea.dascalu.dvmicc.home.fragments.challenge.questions.*
import uk.ac.swansea.dascalu.dvmicc.home.model.ViewModel

class ChallengeFragmentFactory() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val challenge = ViewModel.instance.challenge
        return when (className) {
            ChallengeInformationFragment::class.java.name -> ChallengeInformationFragment(
                challenge.challengeNameIndex, challenge.attackExplanation)
            ManifestsFragment::class.java.name -> ManifestsFragment()
            BroadcastTheftQuestionsFragment::class.java.name -> setFragmentState(BroadcastTheftQuestionsFragment())
            BroadcastTheftDOSQuestionsFragment::class.java.name -> setFragmentState(BroadcastTheftDOSQuestionsFragment())
            BroadcastTheftMITMQuestionsFragment::class.java.name -> setFragmentState(BroadcastTheftMITMQuestionsFragment())
            ActivityIntentHijackFragment::class.java.name -> setFragmentState(ActivityIntentHijackFragment())
            ProviderUriHijackQuestionsFragment::class.java.name -> setFragmentState(ProviderUriHijackQuestionsFragment())
            ChallengeInstructionsFragment::class.java.name -> ChallengeInstructionsFragment(
                ViewModel.instance.hasGuessedApps, challenge.instructions)
            else -> super.instantiate(classLoader, className)
        }
    }

    private fun setFragmentState(fragment: AbstractFullQuestionsFragment) : AbstractFullQuestionsFragment {
        return fragment.apply {
            val questionsFragmentState = ViewModel.instance.questionsFragmentState
            if(questionsFragmentState != null) {
                setInitialSavedState(questionsFragmentState)
            }
        }
    }
}