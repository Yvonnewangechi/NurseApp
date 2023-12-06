package com.example.nurseapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nurseapplication.MainActivity
import com.example.nurseapplication.R
import com.example.nurseapplication.models.Invoice
import com.example.nurseapplication.helpers.PrefsHelper

class InvoiceAdapter(var context: Context) :
    RecyclerView.Adapter<InvoiceAdapter.ViewHolder>(){

    // declare a variable of type List<Model> (Empty)
    var itemList : List<Invoice> = listOf() // its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Adapter classes implements 3 methods
    // 1. onCreateViewHolder -> Used to call the single item file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // access/inflate the single_lab.xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_invoice_details,
            parent, false)
        return ViewHolder(view)
    }

    // 2. onBindViewHolder -> Used to bind(attach) data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // find the views from single_lab.xml and bind data from the model
        val tvstatus : TextView = holder.itemView.findViewById(R.id.tvstatus)
        val tvBookedfor: TextView = holder.itemView.findViewById(R.id.tvBookedfor)
        val tvWheretaken: TextView = holder.itemView.findViewById(R.id.tvWhereTaken)

        // Assume single invoice from the list of invoices(itemList)
        val singleInvoiceDetails = itemList[position]

        // bind data to the singleInvoice
        tvstatus.text = "Status : " +singleInvoiceDetails.status
        tvBookedfor.text =  "Booked For : "+singleInvoiceDetails.booked_for
        tvWheretaken.text =  "WhereTaken : " +singleInvoiceDetails.where_taken

        holder.itemView.setOnClickListener {
            val lab_id = singleInvoiceDetails.lab_id
            PrefsHelper.savePrefs(context, "lab_id", lab_id.toString())
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // Custom Functions
    // 1. Filter the itemList(contain the list of all Labs)
    // Used for searching.....
    fun filterList(filterList: List<Invoice>){
        itemList = filterList
        notifyDataSetChanged()
    }

    // Earlier the itemList is empty
    // The function will get data from the API and pass to the ItemList

    fun setListItems(data: List<Invoice>){

        itemList = data
        notifyDataSetChanged()
    }


}