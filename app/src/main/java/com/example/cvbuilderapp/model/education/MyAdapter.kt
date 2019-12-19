package com.example.cvbuilderapp.model.education

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilderapp.R
import kotlinx.android.synthetic.main.education_item.view.*

class MyAdapter(private val educList: ArrayList<Education>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var listener: OnClickListener? = null

    fun setListener(clickListener: OnClickListener) {
        this.listener = clickListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val education: Education = educList[position]
        holder.bindItems(education)
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (listener != null) {
                listener!!.onItemClick(education, position)
            }
        })
        holder.itemView.btDelete.setOnClickListener(View.OnClickListener {
            if (listener != null) {
                listener!!.onItemDelete(education)
            }
        })
    }

    override fun getItemCount(): Int {
        return educList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent!!.context).inflate(
                R.layout.education_item,
                parent,
                false
            )
        )
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentlayout: RelativeLayout = itemView.findViewById(R.id.rLayout) as RelativeLayout
        fun bindItems(education: Education) {
            val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
            val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
            val tvTimestamp = itemView.findViewById<TextView>(R.id.tvTimestamp)
            tvTitle.text = education.school
            tvDesc.text = education.degree
            tvTimestamp.text = education.major
        }
    }

    interface OnClickListener {
        fun onItemClick(education: Education, position: Int)
        fun onItemDelete(education: Education)
    }

}