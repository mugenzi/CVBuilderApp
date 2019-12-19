package com.example.cvbuilderapp.model.experience

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customersqliteapp.Experience
import com.example.cvbuilderapp.R
import kotlinx.android.synthetic.main.experience_item.view.*

class ExperienceAdapter(private val experienceList: ArrayList<Experience>) : RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {
    private var listener: OnClickListener? = null


    fun setListener(clickListener: OnClickListener) {
        this.listener = clickListener

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val experience: Experience = experienceList[position]
        holder.bindItems(experience)

        holder.itemView.setOnClickListener(View.OnClickListener {

            if (listener != null) {
                listener!!.onItemClick(experience, position)

            }

        })



        holder.itemView.btDelete.setOnClickListener(View.OnClickListener {

            if (listener != null) {

                listener!!.onItemDelete(experience)

            }

        })

    }



    override fun getItemCount(): Int {
        return experienceList.size

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.experience_item, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(experience: Experience) {

//            val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
            val tvCompanyName= itemView.findViewById<TextView>(R.id.tvCompanyName)
            val tvCompanyDescription = itemView.findViewById<TextView>(R.id.tvCompanyDescription)
            val tvjobTitle = itemView.findViewById<TextView>(R.id.tvjobTitle)
            val tvjobDescription = itemView.findViewById<TextView>(R.id.tvjobDescription)
            val tvstartDate= itemView.findViewById<TextView>(R.id.tvstartDate)
            val tvendDate = itemView.findViewById<TextView>(R.id.tvendDate)
            val tvtasks = itemView.findViewById<TextView>(R.id.tvtasks)
            val tvachievements = itemView.findViewById<TextView>(R.id.tvachievements)
            val tvtechnologiesUsed = itemView.findViewById<TextView>(R.id.tvtechnologiesUsed)

//            tvTitle.text = experience.title
            tvCompanyName.text = experience.companyName
            tvCompanyDescription.text = experience.companyDescription
            tvjobTitle.text = experience.jobTitle
            tvjobDescription.text = experience.jobDescription
            tvstartDate.text = experience.startDate
            tvendDate.text = experience.endDate
            tvtasks.text = experience.tasks
            tvachievements.text = experience.achievements
            tvtechnologiesUsed.text = experience.technologiesUsed

        }

    }



    interface OnClickListener {

        fun onItemClick(experience: Experience, position: Int)
        fun onItemDelete(experience: Experience)

    }



}