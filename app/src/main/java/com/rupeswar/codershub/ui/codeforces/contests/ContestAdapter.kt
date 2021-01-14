package com.rupeswar.codershub.ui.codeforces.contests

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.codeforces.Contest
import com.rupeswar.codershub.utils.AlarmReceiver
import com.rupeswar.codershub.utils.TimeUtils
import com.rupeswar.codershub.utils.cancelNotifications

class ContestAdapter(private val isPast: Boolean): RecyclerView.Adapter<ContestAdapter.ContestViewHolder>() {

    private val contests = ArrayList<Contest>()

    class ContestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.contestName)
        val startTime: TextView = itemView.findViewById(R.id.startTime)
        val duration: TextView = itemView.findViewById(R.id.duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contest, parent, false)
        val viewHolder = ContestViewHolder(view)

        if(isPast) {
            view.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(parent.context, Uri.parse("http://www.codeforces.com/contest/${contests[viewHolder.adapterPosition].id}"))
            }
        }
        else {
            view.setOnClickListener {
                val alarmManager =
                    parent.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val triggerTime = contests[viewHolder.adapterPosition].startTime - 3600000L
                val notificationManager =
                    parent.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancelNotifications()
                val notifyPendingIntent = PendingIntent.getBroadcast(
                    parent.context,
                    0,
                    Intent(parent.context, AlarmReceiver::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime,
                        notifyPendingIntent
                    )
                else
                    alarmManager.setExact(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime,
                        notifyPendingIntent
                    )
                Log.d("myApp", "Alarm Set")
                Toast.makeText(parent.context, "Alarm Set for ${contests[viewHolder.adapterPosition].name}.\nNotification will be sent 1 hour before the contest.", Toast.LENGTH_SHORT).show()
                Toast.makeText(parent.context, "Notification will be sent on ${TimeUtils.getDateAndTime(triggerTime)}", Toast.LENGTH_SHORT).show()
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        val currentContest = contests[position]
        holder.name.text = currentContest.name
        holder.startTime.text = TimeUtils.getDateAndTime(currentContest.startTime)
        holder.duration.text = TimeUtils.getDuration(currentContest.duration)
    }

    override fun getItemCount(): Int {
        return contests.size
    }

    fun updateContests(updatedContests: ArrayList<Contest>) {
        contests.clear()
        contests.addAll(updatedContests)

        notifyDataSetChanged()
    }
}