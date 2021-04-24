package uk.ac.swansea.dascalu.dvmicc.home.model

import androidx.fragment.app.Fragment

class ChallengeViewModel(val challenge: Challenge) {
    companion object {
        lateinit var instance: ChallengeViewModel
    }

    var hasGuessedApps : Boolean = false
    var hasCompletedChallenge : Boolean = false
    var questionsFragmentState : Fragment.SavedState? = null

    init {
        instance = this
    }
}