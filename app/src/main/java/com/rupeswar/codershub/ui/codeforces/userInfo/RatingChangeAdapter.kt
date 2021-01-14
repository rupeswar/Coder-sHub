package com.rupeswar.codershub.ui.codeforces.userInfo

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.codeforces.RatingChange

class RatingChangeAdapter : RecyclerView.Adapter<RatingChangeAdapter.RatingChangeViewHolder>() {

    private val ratingChanges = ArrayList<RatingChange>()

    class RatingChangeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.contestName)
        val rank: TextView = itemView.findViewById(R.id.rank)
        val oldRating: TextView = itemView.findViewById(R.id.oldRating)
        val change: TextView = itemView.findViewById(R.id.change)
        val newRating: TextView = itemView.findViewById(R.id.newRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingChangeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rating_change, parent, false)
        val viewHolder = RatingChangeViewHolder(view)
        view.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(parent.context, Uri.parse("http://www.codeforces.com/contest/${ratingChanges[viewHolder.adapterPosition].cid}"))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RatingChangeViewHolder, position: Int) {
        val currentRatingChange = ratingChanges[position]
        holder.name.text = currentRatingChange.name
        holder.rank.text = currentRatingChange.rank.toString()
        holder.oldRating.text = currentRatingChange.oldRating.toString()
        holder.change.text = currentRatingChange.change.toString()
        holder.newRating.text = currentRatingChange.newRating.toString()
    }

    override fun getItemCount(): Int {
        return ratingChanges.size
    }

    fun updateRatingChanges(updatedRatingChanges: ArrayList<RatingChange>) {
        ratingChanges.clear()
        ratingChanges.addAll(updatedRatingChanges)

        notifyDataSetChanged()
    }
}