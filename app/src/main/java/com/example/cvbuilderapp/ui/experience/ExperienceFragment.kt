package com.example.cvbuilderapp.ui.experience

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.customersqliteapp.Experience
import com.example.cvbuilderapp.R
import com.example.cvbuilderapp.model.DBHelper
import com.example.cvbuilderapp.model.SQLController
import com.example.cvbuilderapp.model.education.MyAdapter
import com.example.cvbuilderapp.ui.gallery.GalleryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.add_experience.*
import java.util.*
import kotlin.collections.ArrayList

class ExperienceFragment : Fragment()  {

    private lateinit var galleryViewModel: GalleryViewModel
    private var myAdapter: MyAdapter? = null
    private var dbHelper: DBHelper? = null
    private var educList = ArrayList<Experience>()
    lateinit var recyclerView: RecyclerView
    lateinit var addMenu: FloatingActionButton
    lateinit var c: Context
    lateinit var dbcon: SQLController
    lateinit var btnAdd: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        c = activity!!.applicationContext
        dbHelper = DBHelper(c)
        val root = inflater.inflate(R.layout.fragment_experience, container, false)
        addMenu = root.findViewById(R.id.addMenu) as FloatingActionButton
        addMenu.setOnClickListener {
           // showNoteDialog(false, null, -1)
        }
        recyclerView = root.findViewById(R.id.recyclerView) as RecyclerView
        //getEducationList()




        return root
    }



}