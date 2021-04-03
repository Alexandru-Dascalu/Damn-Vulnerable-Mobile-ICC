package uk.ac.swansea.dascalu.dvmicc.santander.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.swansea.dascalu.dvmicc.santander.EVERYDAY_ACCOUNT_BALANCE
import uk.ac.swansea.dascalu.dvmicc.santander.R

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val accountView = inflater.inflate(R.layout.account_card_layout, parent, false)

        return ViewHolder(accountView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(position) {
            0 -> {
                holder.accountName.text = holder.resources.getString(R.string.everydayAccountName)
                holder.accountDetails.text = holder.resources.getString(R.string.everydaryAccountDetails)
                holder.accountBalance.text = holder.resources.getString(R.string.poundSign) +
                        EVERYDAY_ACCOUNT_BALANCE.toString()
            }
            1 -> {
                holder.accountName.text = holder.resources.getString(R.string.sacingsAccountName)
                holder.accountDetails.text = holder.resources.getString(R.string.savingsAccountDetails)
                holder.accountBalance.text = holder.resources.getString(R.string.savingsAccountBalance)
            }
            2-> {
                holder.accountName.text = holder.resources.getString(R.string.businessAccountName)
                holder.accountDetails.text = holder.resources.getString(R.string.businessAccountDetails)
                holder.accountBalance.text = holder.resources.getString(R.string.businessAccountBalance)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resources = itemView.context.resources
        val accountName = itemView.findViewById<TextView>(R.id.accountNameTextView)
        val accountDetails = itemView.findViewById<TextView>(R.id.accountNumberTextView)
        val accountBalance = itemView.findViewById<TextView>(R.id.balanceTextView)
    }
}