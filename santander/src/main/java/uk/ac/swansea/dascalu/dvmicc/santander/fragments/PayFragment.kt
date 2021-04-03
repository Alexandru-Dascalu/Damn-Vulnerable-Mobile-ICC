package uk.ac.swansea.dascalu.dvmicc.santander.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

import uk.ac.swansea.dascalu.dvmicc.santander.EVERYDAY_ACCOUNT_BALANCE
import uk.ac.swansea.dascalu.dvmicc.santander.R
import uk.ac.swansea.dascalu.dvmicc.santander.hideKeyboard

class PayFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.payment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val payButton = view.findViewById<MaterialButton>(R.id.paymentbutton)
        payButton.setOnClickListener { it ->
            makePayment(view)
        }
    }

    private fun makePayment(rootView: View) {
        val amount: Int = rootView.findViewById<EditText>(R.id.amountInput).text.toString().toInt()
        EVERYDAY_ACCOUNT_BALANCE -= amount

        Snackbar.make(rootView, "Payment made succesfully!", Snackbar.LENGTH_LONG).show()
        hideKeyboard(rootView, rootView.context)
    }
}