package com.wellbeing.pharmacyjob.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.model.JobList

class JobAdapter(
    private var jobList: List<JobList>,
    private val jobClickListener: (JobList) -> Unit
) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobList[position]
        holder.bind(job)
        holder.itemView.setOnClickListener { jobClickListener(job) }
    }

    override fun getItemCount(): Int = jobList.size

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTimeTextView: TextView = itemView.findViewById(R.id.dateTimeTextView)
        private val hourlyRateTextView: TextView = itemView.findViewById(R.id.hourlyRateTextView)
        private val totalTextView: TextView = itemView.findViewById(R.id.totalTextView)
        private val branchNameTextView: TextView = itemView.findViewById(R.id.branchNameTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val distanceTextView: TextView = itemView.findViewById(R.id.distanceTextView)
        private val favIcon: ImageView = itemView.findViewById(R.id.favIcon)

        fun bind(job: JobList) {
            dateTimeTextView.text =
                "${job.jobDate} / ${job.jobStartTime.take(5)} - ${job.jobEndTime.take(5)}"
            hourlyRateTextView.text = "£${job.hourlyRate}/hr"
            totalTextView.text = "  Total: £${job.totalPaid}"
            branchNameTextView.text = job.branchName
            addressTextView.text =
                "${job.branchAddress1}  ${job.branchAddress2}  ${job.branchPostalCode}"
            distanceTextView.text =
                String.format("%.2f miles", job.distance) // Assuming distance is a Double


            // Check the favorite state from SharedPreferences
            val sharedPref =
                itemView.context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
            val isFavorite = sharedPref.getBoolean("job_${job.jobId}", false)
            updateFavoriteIcon(isFavorite)


            // Handle favorite icon click
            favIcon.setOnClickListener {
                val isCurrentlyFavorite = sharedPref.getBoolean("job_${job.jobId}", false)
                val editor = sharedPref.edit()
                editor.putBoolean("job_${job.jobId}", !isCurrentlyFavorite)
                editor.apply()
                updateFavoriteIcon(!isCurrentlyFavorite)
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            if (isFavorite) {
                favIcon.setImageResource(R.drawable.ic_icon_star) // Your filled favorite icon
                favIcon.setColorFilter(itemView.context.getColor(R.color.favoriteColor))
            } else {
                favIcon.setImageResource(R.drawable.ic_icon_star_outline) // Your empty favorite icon
                favIcon.setColorFilter(itemView.context.getColor(R.color.lightGrey))
            }
        }
    }


    fun updateData(newItems: List<JobList>) {
        AppLogger.d("JobAdapter", "updateData, size:" + jobList.size)
        jobList = newItems
        notifyDataSetChanged()
    }
}
