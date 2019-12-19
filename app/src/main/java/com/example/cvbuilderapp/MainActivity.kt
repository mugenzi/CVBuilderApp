package com.example.cvbuilderapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.text.Layout
import android.view.Gravity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_tools.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 1)


    }

    fun goHome() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun resume() {
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            // val root: View = layoutInflater.inflate(R.layout.fragment_tools, null) as View
            // var myWebView = root.findViewById(R.id.myWebView) as WebView
            // createWebPagePrint(myWebView)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_tools,
                R.id.nav_share,
                R.id.nav_send,
                R.id.nav_experience,
                R.id.nav_education
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

       /*
        if (requestCode == 1) {
            val message = data!!.data!!.toString()
            if (resultCode == Activity.RESULT_OK) {
                var toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                if (message == "Login Successfull") {
                    resume()
                } else {
                    var toast = Toast.makeText(this, "Invalid Credials", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivityForResult(intent, 1)
                }

            }
        }
*/
        resume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun createWebPagePrint(webView: WebView) {
        try {
            /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        return;*/
            //val printManager = c.getSystemService(Context.PRINT_SERVICE) as PrintManager

            //var printManager = getActivity()?.getApplicationContext()?.getSystemService(Context.PRINT_SERVICE) as PrintManager
            // Get a PrintManager instance
            val printManager = this.getSystemService(Context.PRINT_SERVICE) as PrintManager
            val printAdapter = webView.createPrintDocumentAdapter()
            val jobName = getString(R.string.app_name) + " Document"
            val builder = PrintAttributes.Builder()
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5)
            val printJob = printManager.print(jobName, printAdapter, builder.build())
            if (printJob.isCompleted()) {
                Toast.makeText(this, "Print Complete", Toast.LENGTH_LONG).show()
            } else if (printJob.isFailed()) {
                Toast.makeText(this, "Print Failed", Toast.LENGTH_LONG).show()
            }
            // Save the job object for later status checking
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

}
