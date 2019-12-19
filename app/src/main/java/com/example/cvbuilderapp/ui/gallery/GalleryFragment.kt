package com.example.cvbuilderapp.ui.gallery

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cvbuilderapp.R
import com.example.cvbuilderapp.model.Contact
import com.example.cvbuilderapp.model.SQLController
import java.lang.Exception

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.net.URI
import java.nio.file.Files.copy
import java.text.SimpleDateFormat
import java.util.*
import android.app.Activity;
import android.media.tv.TvContract.AUTHORITY
import android.system.Os.read
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.barteksc.pdfviewer.util.FileUtils
import kotlinx.android.synthetic.main.fragment_slideshow.*
import java.io.*

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    lateinit var dbcon: SQLController
    lateinit var btnAdd: Button
    lateinit var c: Context

    lateinit var input_fname: TextView
    lateinit var input_lname: TextView
    lateinit var input_phone: TextView
    lateinit var input_street: TextView
    lateinit var input_city: TextView
    lateinit var input_state: TextView
    lateinit var input_zipcode: TextView
    lateinit var input_email: TextView
    lateinit var input_linkedin: TextView
    var existingObject: Contact? = null
    private val STORAGE_CODE: Int = 100;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        c = activity!!.applicationContext
        btnAdd = root.findViewById(R.id.addContact) as Button

        initializeTextView(root)
        initializeData()
        if (existingObject == null) {
            btnAdd.text = "SAVE"
        } else {
            btnAdd.text = "EDIT"
        }
        btnAdd.setOnClickListener {
            if (existingObject == null) {
                addContact(it)
            } else {
                updateContact(it)
            }
            createResumePDFFile()
        }


        var builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build());

        return root
    }

    private fun initializeTextView(root: View?) {
        input_fname = root?.findViewById(R.id.input_fname) as TextView
        input_lname = root?.findViewById(R.id.input_lname) as TextView
        input_phone = root?.findViewById(R.id.input_phone) as TextView
        input_street = root?.findViewById(R.id.input_street) as TextView
        input_city = root?.findViewById(R.id.input_city) as TextView
        input_state = root?.findViewById(R.id.input_state) as TextView
        input_zipcode = root?.findViewById(R.id.input_zipcode) as TextView
        input_email = root?.findViewById(R.id.input_email) as TextView
        input_linkedin = root?.findViewById(R.id.input_linkedin) as TextView
    }

    private fun initializeData() {
        try {
            dbcon = SQLController(c)
            dbcon.open()
            val cursor = dbcon.readData()
            if (cursor.count > 0) {
                input_fname.setText(cursor.getString(1))
                input_lname.setText(cursor.getString(2))
                input_phone.setText(cursor.getString(3))
                input_street.setText(cursor.getString(4))
                input_city.setText(cursor.getString(5))
                input_state.setText(cursor.getString(6))
                input_zipcode.setText(cursor.getString(7))
                input_email.setText(cursor.getString(8))
                input_linkedin.setText(cursor.getString(9))
                existingObject = Contact(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
                )
            } else {
                showToastMessage("No id found")
            }
        } catch (e: Exception) {
            showToastMessage("Read Data Error")
        } finally {
            dbcon.close()
        }
    }

    fun addContact(view: View) {
        try {
            dbcon = SQLController(c)
            dbcon.open()
            val firstName = input_fname.text.toString()
            val lastName = input_lname.text.toString()
            val phone = input_phone.text.toString()
            val street = input_street.text.toString()
            val city = input_city.text.toString()
            val state = input_state.text.toString()
            val zipCode = input_zipcode.text.toString()
            val email = input_email.text.toString()
            val linkedIn = input_linkedin.text.toString()
            var newObject: Contact =
                Contact(
                    0,
                    firstName,
                    lastName,
                    phone,
                    street,
                    city,
                    state,
                    zipCode,
                    email,
                    linkedIn
                )
            var rows = dbcon.insertData(newObject)
            dbcon.close()
            if (rows > 0) {
                showToastMessage("Data saved successfully")
            } else {
                showToastMessage("Inset Failure")
            }
        } catch (e: Exception) {
            showToastMessage("Add Contact Exception")
        } finally {
            dbcon.close()
        }
    }

    fun updateContact(view: View) {
        try {
            dbcon = SQLController(c)
            dbcon.open()
            val firstName = input_fname.text.toString()
            val lastName = input_lname.text.toString()
            val phone = input_phone.text.toString()
            val street = input_street.text.toString()
            val city = input_city.text.toString()
            val state = input_state.text.toString()
            val zipCode = input_zipcode.text.toString()
            val email = input_email.text.toString()
            val linkedIn = input_linkedin.text.toString()
            var newObject: Contact =
                Contact(
                    existingObject!!.id,
                    firstName,
                    lastName,
                    phone,
                    street,
                    city,
                    state,
                    zipCode,
                    email,
                    linkedIn
                )
            var rows = dbcon.updateData(newObject)
            dbcon.close()
            if (rows > 0) {
                showToastMessage("Data Updated successfully")
            } else {
                showToastMessage("Update Failure")
            }
        } catch (e: Exception) {
            showToastMessage("Update Contact Exception")
        } finally {
            dbcon.close()
        }
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(c, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun savePdf() {
        //create object of Document class
        val mDoc = Document()
        //pdf file name
        val mFileName = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        //pdf file path
        val mFilePath =
            Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //open the document for writing
            mDoc.open()

            //get text from EditText i.e. textEt
            val mText = existingObject.toString()

            //add author of the document (metadata)
            mDoc.addAuthor("Atif Pervaiz")

            //add paragraph to the document
            mDoc.add(Paragraph(mText))

            //close document
            mDoc.close()

            //show file saved message with file name and path
            Toast.makeText(c, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            //if anything goes wrong causing exception, get and show exception message
            Toast.makeText(c, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted, call savePdf() method
                    savePdf()
                } else {
                    //permission from popup was denied, show error message
                    Toast.makeText(c, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createResumePDFFile() {
        var doc: Document = Document();
        val fileName = "resume_file.pdf"
        lateinit var file: File
        try {
            var assetManager = c.assets
            //var inn:InputStream = assetManager.open(fileName);
            var out: OutputStream = c.openFileOutput(fileName, Context.MODE_APPEND)

            file = File(c.getCacheDir().toString() + "/$fileName")
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            // val path: String = c?.filesDir.toString() + "/resumes"
            // var dir: File = File(path);
            //if (!dir.exists())
            // dir.mkdirs();
            // file = File(dir, fileName);
            // file = FileUtils.fileFromAsset(c, fileName)
            var fOut: FileOutputStream = FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);
            //PdfWriter.getInstance(doc, out);
            doc.open();

            var p1: Paragraph = Paragraph(existingObject.toString());
            var paraFont: Font = Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);
            doc.add(p1);

        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (e: IOException) {
            Log.e("PDFCreator", "ioException:" + e);
        } catch (e: Exception) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }
        //viewPdf(fileName, Uri.fromFile(file));
    }

    private fun viewPdf(file: String, uri: Uri) {
        try {
            var target: Intent = Intent(Intent.ACTION_VIEW);
            target.setDataAndType(uri, "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            var intent: Intent = Intent.createChooser(target, "Open File");

            try {
                startActivity(intent);
            } catch (e: ActivityNotFoundException) {
                showToastMessage("Can't read pdf file" + e.toString())
            }
        } catch (e: Exception) {
            showToastMessage("Open PDF Error" + e.toString())
        }
    }
}