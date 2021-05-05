package com.example.whocallsyoumost

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ContactAdapter(var context: Context,var arrayList: ArrayList<Contact>):BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view=View.inflate(context,R.layout.row_layout,null)
        var name = view.findViewById<TextView>(R.id.ContactName)
        var number= view.findViewById<TextView>(R.id.ContactNumber,)
        var callCount= view.findViewById<TextView>(R.id.CallCount)
        var durationSum= view.findViewById<TextView>(R.id.CallDuration)

        var listItem : Contact =arrayList.get(p0)

        name.text=listItem.name
        number.text=listItem.number
        callCount.text=listItem.callCount
        durationSum.text=listItem.durationSum

        return  view
    }
}