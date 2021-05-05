package com.example.whocallsyoumost

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.widget.GridView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private var arrayList:ArrayList<Contact>?=null
    private var gridView:GridView?=null
    private var contactAdapter:ContactAdapter?=null

    var cols= listOf<String>(
            CallLog.Calls._ID,
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DURATION,
            //"count( ${CallLog.Calls.CACHED_NAME} ) as cc"
            //  "sum(" + CallLog.Calls.DURATION + ")",
    ).toTypedArray()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, Array(1) { Manifest.permission.READ_CALL_LOG }, 101)
        }else{
            displayLog()
        }
    }

    private fun displayLog() {
        gridView = findViewById(R.id.grid_view)
        arrayList = ArrayList()
        arrayList = setDataList()
        contactAdapter = ContactAdapter(applicationContext,arrayList!!)
        gridView?.adapter=contactAdapter
    }

    private fun setDataList():ArrayList<Contact>{
        var arrayList:ArrayList<Contact> =ArrayList()
        var contacts = getContactList()
        var numbers = getNumbersList(contacts)
        var callCounts = getCallCounts(contacts)
        var sumDuration = getSumDuration(contacts)
        for(i in 0..contacts.size-1) {
            arrayList.add(Contact(contacts[i]?:"", numbers[i]?:" ", callCounts[i]?:" ", sumDuration[i]?:" "))
        }

        return arrayList
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

    private  fun getContactList():ArrayList<String>{

        var arrayList:ArrayList<String> =ArrayList()

        var rs = contentResolver.query(CallLog.Calls.CONTENT_URI, cols, null, null,
                "${CallLog.Calls.LAST_MODIFIED} DESC")
        var length:Int=rs!!.count

        for (i in 1..length-1){
            rs.moveToPosition(i)

            if (arrayList.contains(rs.getString(rs.getColumnIndex("name")))){

            }else{
                arrayList.add(rs.getString(rs.getColumnIndex("name")))
            }
        }

        return arrayList
    }

    private fun getNumbersList(arrayList:ArrayList<String>):ArrayList<String>{

        var numberList:ArrayList<String> =ArrayList()

        for(i in 0 until arrayList.size){

            if(arrayList[i]!=null){

                var selection = "name = '${arrayList[i]}'"

                var rs = contentResolver.query(CallLog.Calls.CONTENT_URI, cols, selection, null,
                        "${CallLog.Calls.LAST_MODIFIED} DESC")
                rs!!.moveToPosition(0)
                numberList.add(rs!!.getString(rs.getColumnIndex("number")))
            }else{
                numberList.add("Unknon Number")
            }

        }
        return numberList
    }

    private fun getCallCounts(arrayList: ArrayList<String>): ArrayList<String> {
        var countList:ArrayList<String> =ArrayList()

        for(i in 0 until arrayList.size){

            if(arrayList[i]!=null){

                var selection = "name = '${arrayList[i]}'"

                var rs = contentResolver.query(CallLog.Calls.CONTENT_URI, cols, selection, null,
                        "${CallLog.Calls.LAST_MODIFIED} DESC")
                countList.add(rs!!.count.toString())
            }else{

                countList.add("Unknon Number")
            }

        }
        Log.d("numberlistSize",countList.size.toString())
        return countList
    }

    private fun getSumDuration(arrayList: ArrayList<String>): ArrayList<String> {
        var countList:ArrayList<String> =ArrayList()
        var sum = 0
        for(i in 0 until arrayList.size){
            if(arrayList[i]!=null){
                var selection = "name = '${arrayList[i]}'"
                var rs = contentResolver.query(CallLog.Calls.CONTENT_URI, cols, selection, null,
                        "${CallLog.Calls.LAST_MODIFIED} DESC")
                for (j in 0 until rs!!.count){
                    rs.moveToPosition(j)
                    sum += rs!!.getString(rs.getColumnIndex("duration")).toInt()
                }
                val hour = sum / 3600
                val minutes = (sum % 3600) / 60
                val seconds = sum % 60
                countList.add("$hour : $minutes : $seconds ")
                sum=0
            }else{
                countList.add("Unknon Number")
            }
        }

        return countList
    }

}