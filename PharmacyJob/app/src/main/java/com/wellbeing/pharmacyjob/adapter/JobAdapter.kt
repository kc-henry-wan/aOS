package com.wellbeing.pharmacyjob.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wellbeing.pharmacyjob.model.JobList
import com.wellbeing.pharmacyjob.R

class JobAdapter(private var jobList: List<JobList>, private val jobClickListener: (JobList) -> Unit) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

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

        fun bind(job: JobList) {
            dateTimeTextView.text = "${job.jobDate} / ${job.jobStartTime} - ${job.jobEndTime}"
            hourlyRateTextView.text = "£${job.hourlyRate}/hr"
            totalTextView.text = "  Total: £${job.totalPaid}"
            branchNameTextView.text = job.branchName
            addressTextView.text = "${job.address} ${job.postalCode}"
            distanceTextView.text = String.format("%.2f miles", job.distance) // Assuming distance is a Double
        }
    }

    fun updateData(newItems: List<JobList>) {
        AppLogger.d("JobAdapter","updateData, size:" + jobList.size)
        jobList = newItems
        notifyDataSetChanged()
    }
}
