package com.example.whocallsyoumost

import android.annotation.SuppressLint
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

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view=View.inflate(context,R.layout.row_layout,null)
        val name = view.findViewById<TextView>(R.id.ContactName)
        val number= view.findViewById<TextView>(R.id.ContactNumber,)
        val callCount= view.findViewById<TextView>(R.id.CallCount)
        val durationSum= view.findViewById<TextView>(R.id.CallDuration)
        val typeMade= view.findViewById<TextView>(R.id.CallTypeMade)
        val typeMissed= view.findViewById<TextView>(R.id.CallTypeMissed)
        val typeReceived= view.findViewById<TextView>(R.id.CallTypeReceived)

        val listItem : Contact =arrayList.get(p0)

        name.text=listItem.name
        number.text=listItem.number
        callCount.text=listItem.callCount
        durationSum.text=listItem.durationSum
        typeMade.text=listItem.typeMade
        typeMissed.text=listItem.typeMissed
        typeReceived.text=listItem.typeReceived

        return  view
    }
}