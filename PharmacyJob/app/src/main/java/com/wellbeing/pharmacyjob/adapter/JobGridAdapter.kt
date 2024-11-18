package com.wellbeing.pharmacyjob.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.wellbeing.pharmacyjob.R

class JobGridAdapter(context: Context, private val dataList: List<String>) :
    ArrayAdapter<String>(context, 0, dataList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var gridItem = convertView
        if (gridItem == null) {
            gridItem = LayoutInflater.from(context).inflate(R.layout.job_grid_item, parent, false)
        }

        val textView = gridItem?.findViewById<TextView>(R.id.gridItemText)
        textView?.text = dataList[position]
        val imageView = gridItem?.findViewById<ImageView>(R.id.gridItemIcon)
        imageView?.visibility = View.VISIBLE

        // Conditionally change the text color based on the item's position
        when (position) {
            0 -> imageView?.setImageResource(R.drawable.ic_job_date)
            1 -> imageView?.setImageResource(R.drawable.ic_job_time)
            2 -> imageView?.setImageResource(R.drawable.ic_job_distance)
            3 -> imageView?.setImageResource(R.drawable.ic_job_workinghour)
            4 -> imageView?.setImageResource(R.drawable.ic_job_hourlyrate)
            5 -> imageView?.setImageResource(R.drawable.ic_job_totalpaid)
            6 -> textView?.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_green_dark
                )
            ) // First item -> Green
            7 -> textView?.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_red_dark
                )
            ) // Second item -> Red
            else -> textView?.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.black
                )
            ) // Default -> Black
        }

        return gridItem!!
    }
}
