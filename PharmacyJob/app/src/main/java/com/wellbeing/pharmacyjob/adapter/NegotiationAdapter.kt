package com.wellbeing.pharmacynego.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wellbeing.pharmacyjob.R
import com.wellbeing.pharmacyjob.model.NegotiationList

class NegotiationAdapter(
    private var negotiationList: List<NegotiationList>,
    private val negoClickListener: (NegotiationList) -> Unit
) : RecyclerView.Adapter<NegotiationAdapter.NegotiationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NegotiationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_negotiation, parent, false)

        return NegotiationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NegotiationViewHolder, position: Int) {
        val nego = negotiationList[position]
        holder.bind(nego)
        holder.itemView.setOnClickListener { negoClickListener(nego) }
    }

    override fun getItemCount(): Int = negotiationList.size

    inner class NegotiationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        private val tvOriginal: TextView = itemView.findViewById(R.id.tvOriginal)
        private val tvPurposed: TextView = itemView.findViewById(R.id.tvPurposed)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)


        fun bind(nego: NegotiationList) {
            tvDateTime.text =
                "${nego.jobDate} / ${nego.jobStartTime.take(5)} - ${nego.jobEndTime.take(5)}"
            tvOriginal.text =
                "Original: £${nego.originalHourlyRate}/hr; Total: £${nego.originalTotalPaid}"

            if (nego.counterHourlyRate > 0) {

                tvPurposed.text =
                    "Admin purposed: £${nego.counterHourlyRate}/hr; Total: £${nego.counterHourlyRate}"
            } else {
                tvPurposed.text =
                    "Your purposed: £${nego.purposedHourlyRate}/hr; Total: £${nego.purposedTotalPaid}"
            }

            if (nego.status == "New") {
                tvStatus.text = nego.status + "\n (Pending approval)"
            } else {
                tvStatus.text = nego.status
            }
        }

    }


    fun updateData(newItems: List<NegotiationList>) {
        AppLogger.d("NegotiationAdapter", "updateData, size:" + negotiationList.size)
        negotiationList = newItems
        notifyDataSetChanged()
    }
}
