package uk.ac.swansea.dascalu.dvmicc.battery_booster.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uk.ac.swansea.dascalu.dvmicc.battery_booster.R

class UsageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.usage_fragment, container, false)
    }
}