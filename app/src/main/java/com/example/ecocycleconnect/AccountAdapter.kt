package com.example.ecocycleconnect

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

data class GoogleAccount(val name: String, val email: String)

class AccountAdapter(
    private val accounts: List<GoogleAccount>,
    private val onAccountClick: (GoogleAccount) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvAccountName)
        val tvEmail: TextView = view.findViewById(R.id.tvAccountEmail)
        val tvAvatar: TextView = view.findViewById(R.id.tvAvatar)
        val avatarContainer: MaterialCardView = view.findViewById(R.id.avatarContainer)
        val card: MaterialCardView = view.findViewById(R.id.cardAccount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_google_account, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accounts[position]
        holder.tvName.text = account.name
        holder.tvEmail.text = account.email
        
        // Set first letter as avatar text
        holder.tvAvatar.text = if (account.name.isNotEmpty()) account.name.take(1).uppercase() else "?"
        
        // Randomly color avatars or use a preset list
        val colors = listOf("#D2E3FC", "#CEEAD6", "#FAD2CF", "#FEEFC3")
        val textColors = listOf("#1967D2", "#137333", "#C5221F", "#E37400")
        val colorIndex = position % colors.size
        
        holder.avatarContainer.setCardBackgroundColor(Color.parseColor(colors[colorIndex]))
        holder.tvAvatar.setTextColor(Color.parseColor(textColors[colorIndex]))

        holder.card.setOnClickListener { onAccountClick(account) }
    }

    override fun getItemCount() = accounts.size
}