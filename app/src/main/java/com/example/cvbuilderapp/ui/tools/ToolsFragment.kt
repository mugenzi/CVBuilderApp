package com.example.cvbuilderapp.ui.tools

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cvbuilderapp.R
import com.example.cvbuilderapp.model.EducationSQLController
import com.example.cvbuilderapp.model.SQLController
import com.example.cvbuilderapp.model.academic_project.DBHelperAP
import com.example.cvbuilderapp.model.experience.DBHelperExperience


class ToolsFragment : Fragment() {
    lateinit var c: Context
    private lateinit var toolsViewModel: ToolsViewModel
    lateinit var printJobs: String
    lateinit var dbcon: SQLController
    lateinit var educationdbcon: EducationSQLController
    lateinit var experiencedbcon: DBHelperExperience
    lateinit var academicdbcon: DBHelperAP

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        c = activity!!.applicationContext
        val root = inflater.inflate(R.layout.fragment_tools, container, false)

        dbcon = SQLController(c)
        dbcon.open()
        val contactInfo = dbcon.getContactInfo()
        dbcon.close()

        var educationData = ""
        try {
            educationdbcon = EducationSQLController(c)
            educationdbcon.open()
            val educationInfo = educationdbcon.getAllData()
            educationdbcon.close()
            for (item in educationInfo) {

                educationData += "<div class=\"bold-title\">" +
                        "<label>${item.degree?.toUpperCase()}:</label>" +
                        "</div>" +
                        "<div class=\"plainText\">" +
                        "<label>${item.school?.toUpperCase()} / ${item.major}" +
                        "</label></div>"
            }
        } catch (e: java.lang.Exception) {

        }


        var experienceData = ""
        try {
            experiencedbcon = DBHelperExperience(c)
            experiencedbcon.open()
            val experienceInfo = experiencedbcon.getAllData()
            experiencedbcon.close()
            for (item in experienceInfo) {
                experienceData = experienceData + "    <div class=\"bold-title\">\n" +
                        "        <label>${item.companyName + ", from " + item.startDate + " to " + item.endDate}.</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <label>${item.companyDescription}</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"bold-title\">\n" +
                        "        <label>${item.jobTitle}.</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <label>${item.jobDescription}.</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"bold-title\">\n" +
                        "        <label>Tasks Performed:</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <ul>\n" +
                        "            <li>${item.tasks}</li>\n" +
                        "            <li>Some tasks</li>\n" +
                        "        </ul>\n" +
                        "    </div>\n" +
                        "    <div class=\"bold-title\">\n" +
                        "        <label>Achievements/Awards:</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <ul>\n" +
                        "            <li>${item.achievements}</li>\n" +
                        "            <li>Some Achievement</li>\n" +
                        "        </ul>\n" +
                        "    </div>\n" +
                        "    <div class=\"bold-title\">\n" +
                        "        <label>Technologies Used:</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <label>${item.technologiesUsed}</label>\n" +
                        "    </div>"
            }
        } catch (e: java.lang.Exception) {

        }


        var academicProjectData = ""
        try {
            academicdbcon = DBHelperAP(c)
            academicdbcon.open()
            val list = academicdbcon.getAllData()
            academicdbcon.close()
            for (item in list) {
                academicProjectData = academicProjectData + "<div class=\"bold-title\">" +
                        "        <label>PROJECT TITLE:</label>\n" +
                        "    </div>"
                "    <div class=\"plainText\">\n" +
                        "        <label>${item.name}</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"bold-title\">\n" +
                        "        <label>SCHOOL:</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <label>${item.school}</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"bold-title\">\n" +
                        "        <label>PROJECT YEAR:</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <label>${item.year}</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"bold-title\">\n" +
                        "        <label>PROJECT DESCRIPTION:</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <label>${item.description}</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"bold-title\">\n" +
                        "        <label>TECHNOLOGIES USED:</label>\n" +
                        "    </div>\n" +
                        "    <div class=\"plainText\">\n" +
                        "        <label>${item.technology}.</label>\n" +
                        "    </div>\n"
            }
        } catch (e: java.lang.Exception) {

        }
        val wv = root.findViewById(R.id.myWebView) as WebView
        val mimeType = "text/html"
        val encoding = "UTF-8"
        val html =
            "<br /><br />Read the handouts please for tomorrow.<br /><br /><!--homework help homework" +
                    "help help with homework homework assignments elementary school high school middle school" +
                    "// --><font color='#60c000' size='4'><strong>Please!</strong></font>" +
                    "<img src='http://www.homeworknow.com/hwnow/upload/images/tn_star300.gif'  />"
        var ccc = "<html>\n" +
                "\n" +
                "<head>\n" +
                "\n" +
                "</head>\n" +
                "<style>\n" +
                "    .container {\n" +
                "        padding: 30px;\n" +
                "        background-color: #64e0ed42;\n" +
                "    }\n" +
                "\n" +
                "    .title {\n" +
                "        color: #FF9800;\n" +
                "        background-color: #303F9F;\n" +
                "        text-align: center;\n" +
                "        font-size: 25px;\n" +
                "        margin-top: 10px;\n" +
                "        margin-bottom: 10px;\n" +
                "    }\n" +
                "\n" +
                "    .description {\n" +
                "        color: black;\n" +
                "        size: 18px;\n" +
                "        padding: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .input {\n" +
                "        color: black;\n" +
                "        font-size: 18px;\n" +
                "        font-style: italic;\n" +
                "        padding: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .plainText {\n" +
                "        color: black;\n" +
                "        font-size: 18px;\n" +
                "        text-align: justify;\n" +
                "        padding-right: 20px;\n" +
                "        padding-left: 20px;\n" +
                "        font-style: italic;\n" +
                "    }\n" +
                "\n" +
                "    .bold-title {\n" +
                "        color: black;\n" +
                "        font-size: 18px;\n" +
                "        font-weight: bold;\n" +
                "        margin-top: 10px;\n" +
                "        padding-right: 10px;\n" +
                "        padding-left: 10px;\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"title\">\n" +
                "        <label>RESUME</label>\n" +
                "    </div>\n" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <td><label class=\"description\">NAME:</label></td>\n" +
                "            <td><label class=\"input\">${contactInfo?.firstName + contactInfo?.lastName}</label></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td><label class=\"description\">EMAIL ADDRESS:</label></td>\n" +
                "            <td><label class=\"input\">${contactInfo?.email}</label></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td><label class=\"description\">PHONE NUMBER:</label></td>\n" +
                "            <td><label class=\"input\">${contactInfo?.phone}</label></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td><label class=\"description\">ADDRESS:</label></td>\n" +
                "            <td><label class=\"input\">${contactInfo?.street}</label></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td><label class=\"description\">ADDRESS 2:</label></td>\n" +
                "            <td><label class=\"input\">${contactInfo?.city + ", " + contactInfo?.state + ", " + contactInfo?.zipcode}</label></td>\n" +
                "        </tr>\n" +
                "\n" +
                "        <tr>\n" +
                "            <td><label class=\"description\">LINKEDIN PROFILE:</label></td>\n" +
                "            <td> <label class=\"input\">${contactInfo?.linkedIn}</label></td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <div class=\"title\">\n" +
                "        <label>CAREER OBJECTIVE</label>\n" +
                "    </div>\n" +
                "    <div class=\"plainText\">\n" +
                "        <label>Please tell us your top choice of job title. This should be a position you want to apply for and have researched so you understand what is involved in it. This should match your experience and is what you will be searching for when looking for a job. </label>\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <div class=\"title\">\n" +
                "        <label>CORE COMPETENCIES</label>\n" +
                "    </div>\n" +
                "    <div class=\"plainText\">\n" +
                "        <label>Please list your strongest skills that you have used at work or in recent academic projects. These should be your best skills that you are most confident with and can answer questions about in an interview.</label>\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <div class=\"title\">\n" +
                "        <label>VISA STATUS</label>\n" +
                "    </div>\n" +
                "    <div class=\"plainText\">\n" +
                "        <label>Please indicate your visa status; Green Card, EAD, or F-1.</label>\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <div class=\"title\">\n" +
                "        <label>PROFESSIONAL EXPERIENCE</label>\n" +
                "    </div>\n" +

                experienceData +

                "<div class=\"title\">" +
                "<label>ACADEMIC PROJECTS</label>" +
                "</div>" +

                academicProjectData +

                "    <div class=\"title\">\n" +
                "        <label>EDUCATION</label>\n" +
                "    </div>\n" +
                "\n" +


                //##########################
                educationData +


                "    <div class=\"title\">\n" +
                "        <label>PROFESSIONAL DEVELOPMENT & CERTIFICATIONS</label>\n" +
                "    </div>\n" +
                "    <div class=\"plainText\">\n" +
                "        <label>CCNA, JAVA SE, JAVA EE</label>\n" +
                "    </div>\n" +
                "\n" +
                "</div>\n" +
                "</body>\n" +
                "\n" +
                "</html>"

        wv.loadDataWithBaseURL("", ccc, mimeType, encoding, "")

        var btnAdd = root.findViewById(R.id.button2) as Button
        btnAdd.setOnClickListener {
            createWebPagePrint(wv)
        }
        return root
    }


    private fun createWebPagePrint(webView: WebView) {
        try {
            /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        return;*/
            //val printManager = c.getSystemService(Context.PRINT_SERVICE) as PrintManager

            //var printManager = getActivity()?.getApplicationContext()?.getSystemService(Context.PRINT_SERVICE) as PrintManager
            // Get a PrintManager instance
            val printManager = activity?.getSystemService(Context.PRINT_SERVICE) as PrintManager
            val printAdapter = webView.createPrintDocumentAdapter()
            val jobName = getString(R.string.app_name) + " Document"
            val builder = PrintAttributes.Builder()
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5)
            val printJob = printManager.print(jobName, printAdapter, builder.build())
            if (printJob.isCompleted()) {
                Toast.makeText(c, "Print Complete", Toast.LENGTH_LONG).show()
            } else if (printJob.isFailed()) {
                Toast.makeText(c, "Print Failed", Toast.LENGTH_LONG).show()
            }
            // Save the job object for later status checking
        } catch (e: Exception) {
            Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show()
        }
    }


    private var mWebView: WebView? = null

    private fun doWebViewPrint() {
        // Create a WebView object specifically for printing
        val webView = WebView(activity)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) =
                false

            override fun onPageFinished(view: WebView, url: String) {
                Log.i(TAG, "page finished loading $url")
                createWebPrintJob(view)
                mWebView = null
            }
        }

        // Generate an HTML document on the fly:
        val htmlDocument =
            "<html><body><h1>Test Content</h1><p>Testing, testing, testing...</p></body></html>"
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null)

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView
    }

    private fun createWebPrintJob(webView: WebView) {

        // Get a PrintManager instance
        (activity?.getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = "${getString(R.string.app_name)} Document"

            // Get a print adapter instance
            val printAdapter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView.createPrintDocumentAdapter(jobName)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }

            // Create a print job with name and adapter instance
            printManager.print(
                jobName,
                printAdapter,
                PrintAttributes.Builder().build()
            ).also { printJob ->

                // Save the job object for later status checking
                printJobs += printJob
            }
        }
    }
}