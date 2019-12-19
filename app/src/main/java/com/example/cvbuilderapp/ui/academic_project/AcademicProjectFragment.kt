package com.example.cvbuilderapp.ui.academic_project

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilderapp.R
import com.example.cvbuilderapp.model.academic_project.APAdapter
import com.example.cvbuilderapp.model.academic_project.AcademicProject
import com.example.cvbuilderapp.model.academic_project.DBHelperAP
import com.example.cvbuilderapp.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.add_ap.*
import java.lang.Exception

class AcademicProjectFragment : Fragment(), APAdapter.OnClickListener {

    private var myAdapter: APAdapter? = null
    private var dbHelper: DBHelperAP? = null
    private var acpList = ArrayList<AcademicProject>()
    lateinit var c: Context
    lateinit var recyclerView: RecyclerView
    lateinit var addMenu: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_experience, container, false)
        c = activity!!.applicationContext
        dbHelper = DBHelperAP(c)


        //Set the TodoList in myAdapter

        recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView

        getAcList()



        addMenu = root.findViewById(R.id.addMenu) as FloatingActionButton

        addMenu.setOnClickListener {
            showEducationDialog(false, null, -1)
        }

        return root
    }

    private fun getAcList() {
        try {
            dbHelper?.open()
            acpList = dbHelper!!.getAllData()
            recyclerView.layoutManager = LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false)
            myAdapter = APAdapter(acpList)
            myAdapter!!.setListener(this)
            recyclerView.adapter = myAdapter
        } catch (e: Exception) {

        } finally {
            dbHelper?.close()
        }

    }


    override fun onItemClick(acproject: AcademicProject, position: Int) {
        showEducationDialog(true, acproject, position)
    }

    override fun onItemDelete(acproject: AcademicProject) {
        deleteConfirmation(acproject)
    }

    /**
     * Shows alert dialog with EditText options to enter / edit  a education.
     * when shouldUpdate=true, it automatically displays old education and changes the  button text to UPDATE
     */
    private fun showEducationDialog(
        shouldUpdate: Boolean,
        acproject: AcademicProject?,
        position: Int
    ) {
        val view =
            LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.add_ap, null)

        val alertDialogView = AlertDialog.Builder(activity).create()
        alertDialogView.setView(view)


        val tvHeader = view.findViewById<TextView>(R.id.tvHeader)
        val edTitle = view.findViewById<EditText>(R.id.edTitle)
        val edSchool = view.findViewById<EditText>(R.id.edSchool)
        val edYear = view.findViewById<TextView>(R.id.edYear)
        val edDesc = view.findViewById<EditText>(R.id.edDesc)
        val edTech = view.findViewById<TextView>(R.id.edTech)

        val btAddUpdate = view.findViewById<Button>(R.id.btAddUpdate)
        val btCancel = view.findViewById<Button>(R.id.btCancel)
        if (shouldUpdate) btAddUpdate.text = "Update" else btAddUpdate.text = "Save"

        if (shouldUpdate && acproject != null) {
            edTitle.setText(acproject.title)
            edSchool.setText(acproject.school)
            edYear.setText(acproject.year)
            edDesc.setText(acproject.description)
            edTech.setText(acproject.technology)
        }

        btAddUpdate.setOnClickListener(View.OnClickListener {
            val tTitle = edTitle.text.toString()
            val tSchool = edSchool.text.toString()
            val tYear = edYear.text.toString()
            val tDesc = edDesc.text.toString()
            val tTech = edTech.text.toString()

            if (TextUtils.isEmpty(tTitle)) {
                Toast.makeText(c, "Enter Your Title!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else if (TextUtils.isEmpty(tSchool)) {
                Toast.makeText(c, "Enter Your School!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            // check if user updating Todos
            if (shouldUpdate && acproject != null) {

                updateAcProject(
                    AcademicProject(tTitle, tSchool, tYear, tDesc, tTech),
                    position
                )
            } else {
                dbHelper?.open()
                createAcProject(
                    AcademicProject(
                        tTitle,
                        tSchool,
                        tYear,
                        tDesc,
                        tTech
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
            if (!shouldUpdate) getString(R.string.lbl_new_ac_title) else getString(R.string.lbl_edit_ac_title)

        alertDialogView.setCancelable(false)
        alertDialogView.show()
    }

    /**
     * Inserting new education in db and refreshing the list
     */
    private fun createAcProject(acproject: AcademicProject) {
        try {
            dbHelper?.open()
            val id =
                dbHelper!!.insertAcProject(acproject)    // inserting education in db and getting newly inserted education id
            val new = dbHelper!!.getAcProject(id)  // get the newly inserted education from db
            if (new != null) {
                acpList.add(0, new)    // adding new education to array list at 0 position
                myAdapter!!.notifyDataSetChanged()  // refreshing the list
            }
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        }finally {
            dbHelper?.close()
        }
    }

    /**
     * Updating education in db and updating item in the list by its position
     */
    private fun updateAcProject(t: AcademicProject, position: Int) {
        try {
            dbHelper?.open()
            val acproject = acpList[position]
            acproject.title = t.title    // updating title
            acproject.school = t.school  // updating description
            acproject.year = t.year  // updating description
            acproject.description = t.description  // updating description
            acproject.technology = t.technology  // updating description

            dbHelper!!.updateAcProject(acproject) // updating education in db
            acpList[position] = acproject  // refreshing the list
            myAdapter!!.notifyItemChanged(position)
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        }finally {
            dbHelper?.close()
        }
    }


    private fun deleteConfirmation(acproject: AcademicProject) {
        try {
            dbHelper?.open()
            val alertDialog = AlertDialog.Builder(activity)
            alertDialog.setTitle("Confirm Delete...")
            alertDialog.setMessage("Are you sure you want to delete this?")
            alertDialog.setIcon(R.drawable.ic_delete)
            alertDialog.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                dbHelper!!.deleteAcProject(acproject)
                getAcList()  // refreshing the list
            })

            alertDialog.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel() //Cancel the dialog
            })
            alertDialog.show()
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        }finally {
            dbHelper?.close()
        }
    }

}