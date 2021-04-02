package uk.ac.swansea.dascalu.dvmicc.abattery_booster.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uk.ac.swansea.dascalu.dvmicc.abattery_booster.R

class ChargeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.charge_fragment, container, false)
    }
}