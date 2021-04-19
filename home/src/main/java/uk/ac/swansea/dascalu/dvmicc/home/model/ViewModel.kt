package uk.ac.swansea.dascalu.dvmicc.home.model

import androidx.fragment.app.Fragment

class ViewModel(val challenge: Challenge) {
    companion object {
        lateinit var instance: ViewModel
    }

    var hasGuessedApps : Boolean = false
    var hasCompletedChallenge : Boolean = false
    var questionsFragmentState : Fragment.SavedState? = null

    init {
        instance = this
    }
}