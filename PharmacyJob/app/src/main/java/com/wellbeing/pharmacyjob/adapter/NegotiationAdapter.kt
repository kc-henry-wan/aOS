package com.wellbeing.pharmacynego.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.model.NegotiationList
import com.wellbeing.pharmacyjob.utils.FavoriteManager

class NegotiationAdapter(
    private var negotiationList: List<NegotiationList>,
    private val negoClickListener: (NegotiationList) -> Unit
) : RecyclerView.Adapter<NegotiationAdapter.NegotiationViewHolder>() {

    private lateinit var favoriteManager: FavoriteManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NegotiationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)

        favoriteManager = FavoriteManager(parent.context)

        return NegotiationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NegotiationViewHolder, position: Int) {
        val nego = negotiationList[position]
        holder.bind(nego)
        holder.itemView.setOnClickListener { negoClickListener(nego) }
    }

    override fun getItemCount(): Int = negotiationList.size

    inner class NegotiationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTimeTextView: TextView = itemView.findViewById(R.id.dateTimeTextView)
        private val hourlyRateTextView: TextView = itemView.findViewById(R.id.hourlyRateTextView)
        private val totalTextView: TextView = itemView.findViewById(R.id.totalTextView)
        private val branchNameTextView: TextView = itemView.findViewById(R.id.branchNameTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val distanceTextView: TextView = itemView.findViewById(R.id.distanceTextView)
        private val favIcon: ImageView = itemView.findViewById(R.id.favIcon)


        fun bind(nego: NegotiationList) {
            dateTimeTextView.text =
                "${nego.jobDate} / ${nego.jobStartTime.take(5)} - ${nego.jobEndTime.take(5)}"
            hourlyRateTextView.text = "£${nego.purposedHourlyRate}/hr"
            totalTextView.text = "  Total: £${nego.purposedTotalPaid}"
            branchNameTextView.text = nego.branchName
            addressTextView.text =
                "${nego.branchAddress1}  ${nego.branchAddress2}  ${nego.branchPostalCode}"
            distanceTextView.text = "1.25"
//                String.format(Locale.UK, "%.2f miles", nego.distance)
        }

    }


    fun updateData(newItems: List<NegotiationList>) {
        AppLogger.d("JobAdapter", "updateData, size:" + negotiationList.size)
        negotiationList = newItems
        notifyDataSetChanged()
    }
}
