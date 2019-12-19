package com.example.cvbuilderapp.ui.education

import android.app.AlertDialog
import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilderapp.R
import com.example.cvbuilderapp.model.DBHelper
import com.example.cvbuilderapp.model.EducationSQLController
import com.example.cvbuilderapp.model.SQLController
import com.example.cvbuilderapp.model.education.Education
import com.example.cvbuilderapp.model.education.MyAdapter
import com.example.cvbuilderapp.ui.gallery.GalleryViewModel
import com.example.cvbuilderapp.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception


class EducationFragment : Fragment(), MyAdapter.OnClickListener {
    private lateinit var galleryViewModel: GalleryViewModel
    private var myAdapter: MyAdapter? = null
    private var dbHelper: DBHelper? = null
    private var educList = ArrayList<Education>()
    lateinit var c: Context
    lateinit var recyclerView: RecyclerView
    lateinit var addMenu: FloatingActionButton
    lateinit var dbcon: EducationSQLController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        c = activity!!.applicationContext
        dbHelper = DBHelper(c)
        val root = inflater.inflate(R.layout.fragment_education, container, false)
        addMenu = root.findViewById(R.id.addMenu) as FloatingActionButton
        addMenu.setOnClickListener {
            showNoteDialog(false, null, -1)
        }
        recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView
        getEducationList()
        return root
    }

    private fun getEducationList() {
        try {
            dbcon = EducationSQLController(c)
            dbcon.open()
            educList = dbcon.getAllData()
            recyclerView.layoutManager = LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false)
            myAdapter = MyAdapter(educList)
            recyclerView.adapter = myAdapter
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        } finally {
            dbcon.close()
        }
    }

    private fun showNoteDialog(shouldUpdate: Boolean, todo: Education?, position: Int) {
        val view =
            LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.add_todo, null)
        val alertDialogView = AlertDialog.Builder(activity).create()
        alertDialogView.setView(view)
        val tvHeader = view.findViewById<TextView>(R.id.tvHeader)
        val edTitle = view.findViewById<EditText>(R.id.edTitle)
        val edDesc = view.findViewById<EditText>(R.id.edDesc)
        val btAddUpdate = view.findViewById<Button>(R.id.btAddUpdate)
        val btCancel = view.findViewById<Button>(R.id.btCancel)
        if (shouldUpdate) btAddUpdate.text = "Update" else btAddUpdate.text = "Save"

        if (shouldUpdate && todo != null) {
            edTitle.setText(todo.school)
            edDesc.setText(todo.degree)
        }

        btAddUpdate.setOnClickListener(View.OnClickListener {
            val tName = edTitle.text.toString()
            val descName = edDesc.text.toString()

            if (TextUtils.isEmpty(tName)) {
                Toast.makeText(c, "Enter Your Title!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else if (TextUtils.isEmpty(descName)) {
                Toast.makeText(c, "Enter Your Description!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            // check if user updating Todos
            if (shouldUpdate && todo != null) {
                updateEducation(Education(tName, descName), position)      // update note by it's id
            } else {
                createEducation(Education(tName, descName,"Computer Science"))   // create new note
            }
            alertDialogView.dismiss()
        })

        btCancel.setOnClickListener(View.OnClickListener {
            alertDialogView.dismiss()
        })
        tvHeader.text =
            if (!shouldUpdate) getString(R.string.lbl_new_todo_title) else getString(R.string.lbl_new_todo_title)

        alertDialogView.setCancelable(false)
        alertDialogView.show()
    }

    private fun createEducation(education: Education) {
        try {
            dbcon = EducationSQLController(c)
            dbcon.open()
            val id =
                dbcon.insertEducation(education)    // inserting note in db and getting newly inserted note id
            val new = dbcon.getEducation(id)  // get the newly inserted note from db
            if (new != null) {
                educList.add(0, new)    // adding new note to array list at 0 position
                myAdapter!!.notifyDataSetChanged()  // refreshing the list
            }
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        } finally {
            dbcon.close()
        }
    }

    /**
     * Updating education in db and updating item in the list by its position
     */
    private fun updateEducation(t: Education, position: Int) {
        try {
            dbcon = EducationSQLController(c)
            dbcon.open()
            val education = educList[position]
            education.school = t.school    // updating title
            education.degree = t.degree  // updating description
            dbcon.updateEducation(education) // updating note in db
            educList[position] = education  // refreshing the list
            myAdapter!!.notifyItemChanged(position)
        } catch (e: Exception) {
            Utils.showMessage(c, e.toString())
        } finally {
            dbcon.close()
        }
    }
    override fun onItemClick(education: Education, position: Int) {
        //showNoteDialog(true, education, position)
    }
    override fun onItemDelete(education: Education) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}