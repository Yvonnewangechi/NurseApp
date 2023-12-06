package com.example.nurseapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nurseapplication.MainActivity
import com.example.nurseapplication.R
import com.example.nurseapplication.constants.Constant
import com.example.nurseapplication.helpers.ApiHelper
import com.example.nurseapplication.models.Invoice
import com.example.nurseapplication.helpers.PrefsHelper
import com.example.nurseapplication.models.Assignment
import org.json.JSONArray
import org.json.JSONObject

class AssignmentAdapter(var context: Context) :
    RecyclerView.Adapter<AssignmentAdapter.ViewHolder>(){

    // declare a variable of type List<Model> (Empty)
    var itemList : List<Assignment> = listOf() // its empty

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Adapter classes implements 3 methods
    // 1. onCreateViewHolder -> Used to call the single item file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // access/inflate the single_lab.xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_assignment,
            parent, false)
        return ViewHolder(view)
    }

    // 2. onBindViewHolder -> Used to bind(attach) data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // find the views from single_lab.xml and bind data from the model
        val tvAllocationId : TextView = holder.itemView.findViewById(R.id.tvAllocationId)
        val tvActive: TextView = holder.itemView.findViewById(R.id.tvActive)
        val tvInvoiceNo: TextView = holder.itemView.findViewById(R.id.tvInvoiceNo)
        val button : Button = holder.itemView.findViewById(R.id.btnfinish)

        // Assume single invoice from the list of invoices(itemList)
        val singleAssignment = itemList[position]

        // bind data to the singleInvoice
        tvAllocationId.text = "AllocationId : " +singleAssignment.allocation_id
        tvActive.text = "Flag : " +singleAssignment.flag
        tvInvoiceNo.text =  "Invoice No : " +singleAssignment.invoice_no

        PrefsHelper.savePrefs(context , "Invoice_No", singleAssignment.invoice_no)



        holder.itemView.setOnClickListener {
            val allocation_id = singleAssignment.allocation_id
            PrefsHelper.savePrefs(context, "allocation_id", allocation_id.toString())
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }


        button.setOnClickListener {

            val helper = ApiHelper(context)
            val api: String = Constant.BASE_URL + "/finish_booking"
            // body payload -> JSONObject
            val body = JSONObject()
            val inv = PrefsHelper.getPrefs(context ,"Invoice_No")
            Toast.makeText(context, inv, Toast.LENGTH_SHORT).show()
            body.put("invoice_no" , inv)

            helper.post(api , body , object : ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {

                }

                override fun onSuccess(result: JSONObject?) {
                    Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(result: String?) {
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                }
            })



        }



    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // Custom Functions
    // 1. Filter the itemList(contain the list of all Labs)
    // Used for searching.....
    fun filterList(filterList: List<Assignment>){
        itemList = filterList
        notifyDataSetChanged()
    }

    // Earlier the itemList is empty
    // The function will get data from the API and pass to the ItemList

    fun setListItems(data: List<Assignment>){

        itemList = data
        notifyDataSetChanged()
    }


}