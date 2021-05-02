package com.example.whocallsyoumost

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.Manifest
import android.provider.CallLog
import android.widget.ListView
import android.widget.SimpleCursorAdapter

class MainActivity : AppCompatActivity() {

    var cols= listOf<String>(
        CallLog.Calls._ID,
        CallLog.Calls.NUMBER,
        CallLog.Calls.TYPE,
        CallLog.Calls.DURATION,
    ).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,Array(1){Manifest.permission.READ_CALL_LOG},101)
        }else{
            displayLog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==101&& grantResults[0]==PackageManager.PERMISSION_GRANTED)
            displayLog()
    }

    private fun displayLog() {

        var from = listOf<String>(
            CallLog.Calls.NUMBER,
            CallLog.Calls.DURATION,
            CallLog.Calls.TYPE).toTypedArray()

        var to = intArrayOf(R.id.ContactName,R.id.ContactNumber,R.id.CallType)

        var rs = contentResolver.query(CallLog.Calls.CONTENT_URI,cols,null,null,
            "${CallLog.Calls.LAST_MODIFIED} DESC")

        var  adapter = SimpleCursorAdapter(
            this,
            R.layout.row_layout,
            rs,
            from,
            to,
            0
        )
        findViewById<ListView>(R.id.list_view).adapter = adapter
    }

}