package com.example.cvbuilderapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import android.widget.Toast
import androidx.core.app.ActivityCompat

class FingerprintHandler(private val appContext: Context) : FingerprintManager.AuthenticationCallback() {
    private var cancellationSignal: CancellationSignal? = null

    fun startAuth(manager:FingerprintManager, cryptoObject:FingerprintManager.CryptoObject){
        cancellationSignal = CancellationSignal()
        if(ActivityCompat.checkSelfPermission(appContext,Manifest.permission.USE_FINGERPRINT)!=PackageManager.PERMISSION_GRANTED){
            return
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }
    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        Toast.makeText(appContext, "Authentication error\n" + errString, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        Toast.makeText(appContext, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        Toast.makeText(appContext, "Authentication failed.", Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        Toast.makeText(appContext, "Authentication succeeded.", Toast.LENGTH_LONG).show()
        val intent = Intent(appContext, MainActivity::class.java)
        appContext.startActivity(intent)
    }
}