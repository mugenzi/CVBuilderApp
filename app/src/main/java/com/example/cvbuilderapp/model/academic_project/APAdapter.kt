package com.example.cvbuilderapp.model.academic_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilderapp.R
import kotlinx.android.synthetic.main.ac_item.view.*

class APAdapter(private val acList: ArrayList<AcademicProject>) : RecyclerView.Adapter<APAdapter.ViewHolder>() {
    private var listener: OnClickListener? = null


    fun setListener(clickListener: OnClickListener) {
        this.listener = clickListener

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val acproject: AcademicProject = acList[position]
        holder.bindItems(acproject)

        holder.itemView.setOnClickListener(View.OnClickListener {

            if (listener != null) {
                listener!!.onItemClick(acproject, position)

            }

        })



        holder.itemView.btDelete.setOnClickListener(View.OnClickListener {

            if (listener != null) {

                listener!!.onItemDelete(acproject)

            }

        })

    }



    override fun getItemCount(): Int {
        return acList.size

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.ac_item, parent, false))
    }




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun bindItems(acproject: AcademicProject) {

            val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
            val tvSchool= itemView.findViewById<TextView>(R.id.tvSchool)
            val tvYear = itemView.findViewById<TextView>(R.id.tvYear)
            val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
            val tvTech = itemView.findViewById<TextView>(R.id.tvTech)

            tvTitle.text = acproject.title
            tvSchool.text = acproject.school
            tvYear.text = acproject.year
            tvDesc.text = acproject.description
            tvTech.text = acproject.technology

        }

    }



    interface OnClickListener {

        fun onItemClick(acproject: AcademicProject, position: Int)
        fun onItemDelete(acproject: AcademicProject)

    }



}