package com.example.cvbuilderapp.ui.slideshow

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle;
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customersqliteapp.Experience
import com.example.cvbuilderapp.R
import com.example.cvbuilderapp.model.academic_project.APAdapter
import com.example.cvbuilderapp.model.experience.DBHelperExperience
import com.example.cvbuilderapp.model.experience.ExperienceAdapter
import com.example.cvbuilderapp.utils.Utils
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class SlideshowFragment : Fragment(), OnPageChangeListener, ExperienceAdapter.OnClickListener {

    private lateinit var slideshowViewModel: SlideshowViewModel


    private var myAdapter: ExperienceAdapter? = null
    private var dbHelper: DBHelperExperience? = null
    private var experienceList = ArrayList<Experience>()
    lateinit var c: Context
    lateinit var recyclerView: RecyclerView
    lateinit var addMenu: FloatingActionButton
    lateinit var dateString:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

        //myAdapter = APAdapter(acpList)
        c = activity!!.applicationContext
        dbHelper = DBHelperExperience(c)


        //Set the Experience in myAdapter

        recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView

        getExperienceList()

        addMenu = root.findViewById(R.id.addMenu) as FloatingActionButton

        addMenu.setOnClickListener {
            showEducationDialog(false, null, -1)
        }
try {

    val view =
        LayoutInflater.from(activity!!.applicationContext)
            .inflate(R.layout.add_experience, null)
    var edstartDate = view.findViewById(R.id.edstartDate) as EditText
    edstartDate.setOnClickListener {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            activity!!.applicationContext,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                dateString = "$dayOfMonth  $monthOfYear  $year"

            },
            mYear,
            mMonth,
            mDay
        )
        dpd.show()
    }
}catch (e:Exception){

}
        return root
    }

    private fun getExperienceList() {
        try {
            dbHelper?.open()
            experienceList = dbHelper!!.getAllData()
            recyclerView.layoutManager = LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false)
            myAdapter = ExperienceAdapter(experienceList)
            myAdapter!!.setListener(this)
            recyclerView.adapter = myAdapter
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        }finally {
            dbHelper?.close()
        }

    }


    override fun onItemClick(experience: Experience, position: Int) {
        showEducationDialog(true, experience, position)
    }

    override fun onItemDelete(experience: Experience) {
        deleteConfirmation(experience)
    }

    /**
     * Shows alert dialog with EditText options to enter / edit  a education.
     * when shouldUpdate=true, it automatically displays old education and changes the  button text to UPDATE
     */
    private fun showEducationDialog(
        shouldUpdate: Boolean,
        experience: Experience?,
        position: Int
    ) {
        val view =
            LayoutInflater.from(activity!!.applicationContext)
                .inflate(R.layout.add_experience, null)

        val alertDialogView = AlertDialog.Builder(activity).create()
        alertDialogView.setView(view)


        val tvHeader = view.findViewById<TextView>(R.id.tvHeader)
        val edcompanyName = view.findViewById<EditText>(R.id.edcompanyName)
        val edcompanyDescription = view.findViewById<EditText>(R.id.edcompanyDescription)
        val edjobTitle = view.findViewById<TextView>(R.id.edjobTitle)
        val edjobDescription = view.findViewById<EditText>(R.id.edjobDescription)
        val edstartDate = view.findViewById<EditText>(R.id.edstartDate)
        val edendDate = view.findViewById<EditText>(R.id.edendDate)
        val edtasks = view.findViewById<TextView>(R.id.edtasks)
        val edachievements = view.findViewById<EditText>(R.id.edachievements)
        val edtechnologiesUsed = view.findViewById<TextView>(R.id.edtechnologiesUsed)

        val btAddUpdate = view.findViewById<Button>(R.id.btAddUpdate)
        val btCancel = view.findViewById<Button>(R.id.btCancel)
        if (shouldUpdate) btAddUpdate.text = "Update" else btAddUpdate.text = "Save"

        if (shouldUpdate && experience != null) {
            edcompanyName.setText(experience.companyName)
            edcompanyDescription.setText(experience.companyDescription)
            edjobTitle.setText(experience.jobTitle)
            edjobDescription.setText(experience.jobDescription)
            edstartDate.setText(experience.startDate)
            edendDate.setText(experience.endDate)
            edtasks.setText(experience.tasks)
            edachievements.setText(experience.achievements)
            edtechnologiesUsed.setText(experience.technologiesUsed)
        }

        btAddUpdate.setOnClickListener(View.OnClickListener {
            val tcompanyName = edcompanyName.text.toString()
            val tcompanyDescription = edcompanyDescription.text.toString()
            val tjobTitle = edjobTitle.text.toString()
            val tjobDescription = edjobDescription.text.toString()
            val tstartDate = edstartDate.text.toString()
            val tendDate = edendDate.text.toString()
            val ttasks = edtasks.text.toString()
            val tachievements = edachievements.text.toString()
            val ttechnologiesUsed = edtechnologiesUsed.text.toString()

            if (TextUtils.isEmpty(tcompanyName)) {
                Toast.makeText(c, "Enter Your Title!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else if (TextUtils.isEmpty(tcompanyDescription)) {
                Toast.makeText(c, "Enter Your School!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            // check if user updating Todos
            if (shouldUpdate && experience != null) {
                dbHelper?.open()
                updateExperience(
                    Experience(
                        tcompanyName,
                        tcompanyDescription,
                        tjobTitle,
                        tjobDescription,
                        tstartDate,
                        tendDate,
                        ttasks,
                        tachievements,
                        ttechnologiesUsed
                    ),
                    position
                )
                dbHelper?.close()
            } else {
                dbHelper?.open()
                createExperience(
                    Experience(
                        tcompanyName,
                        tcompanyDescription,
                        tjobTitle,
                        tjobDescription,
                        tstartDate,
                        tendDate,
                        ttasks,
                        tachievements,
                        ttechnologiesUsed
                    )
                )   // create new Academic Project
                dbHelper?.close()
            }
            alertDialogView.dismiss()
        })

        btCancel.setOnClickListener(View.OnClickListener {
            alertDialogView.dismiss()
        })
        tvHeader.text =
            if (!shouldUpdate) getString(R.string.lbl_new_experience_title) else getString(R.string.lbl_edit_experience_title)

        alertDialogView.setCancelable(false)
        alertDialogView.show()
    }

    /**
     * Inserting new Experience in db and refreshing the list
     */
    private fun createExperience(experience: Experience) {
        try {
            dbHelper?.open()
            val id =
                dbHelper!!.insertExperience(experience)    // inserting education in db and getting newly inserted education id
            val new = dbHelper!!.getExperience(id)  // get the newly inserted education from db
            if (new != null) {
                experienceList.add(0, new)    // adding new education to array list at 0 position
                myAdapter!!.notifyDataSetChanged()  // refreshing the list
            }
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        } finally {
            dbHelper?.close()
        }
    }

    /**
     * Updating Experience in db and updating item in the list by its position
     */
    private fun updateExperience(t: Experience, position: Int) {
        try {
            dbHelper?.open()
            val experience = experienceList[position]
            experience.companyName = t.companyName    // updating title
            experience.companyDescription = t.companyDescription  // updating description
            experience.jobTitle = t.jobTitle  // updating description
            experience.jobDescription = t.jobDescription  // updating description
            experience.startDate = t.startDate  // updating description
            experience.endDate = t.endDate    // updating title
            experience.tasks = t.tasks  // updating description
            experience.achievements = t.achievements  // updating description
            experience.technologiesUsed = t.technologiesUsed  // updating description

            dbHelper!!.updateExperience(experience) // updating education in db
            experienceList[position] = experience  // refreshing the list
            myAdapter!!.notifyItemChanged(position)
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        } finally {
            dbHelper?.close()
        }
    }


    private fun deleteConfirmation(experience: Experience) {
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setTitle("Confirm Delete...")
        alertDialog.setMessage("Are you sure you want to delete this?")
        alertDialog.setIcon(R.drawable.ic_delete)
        alertDialog.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
            dbHelper!!.deleteAcProject(experience)
            getExperienceList()  // refreshing the list
        })

        alertDialog.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel() //Cancel the dialog
        })
        alertDialog.show()
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}