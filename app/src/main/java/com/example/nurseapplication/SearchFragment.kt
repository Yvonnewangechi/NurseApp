package com.example.nurseapplication

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nurseapplication.adapters.InvoiceAdapter
import com.example.nurseapplication.models.Invoice
import com.google.gson.GsonBuilder
import com.example.nurseapplication.helpers.ApiHelper
import com.modcom.medilabmemberapp.helpers.NetworkHelper
import org.json.JSONArray
import org.json.JSONObject

    class SearchFragment : Fragment() {
        // outside
        // Declare the resources to be used without assigning values
        lateinit var itemList : List<Invoice>
        lateinit var recyclerView: RecyclerView
        lateinit var InvoiceAdapter: InvoiceAdapter


        private fun showAlertDialog(){
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Internet Check")
                .setMessage("Please Check Your Internet Connection")
                .setPositiveButton("Yes"){dialog, which ->
                    dialog.dismiss()
                }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // This acts like the onCreate() function in activities
            // 1. find recyclerView

            val view = inflater.inflate(R.layout.fragment_search, container, false)

            recyclerView = view.findViewById(R.id.recyclerView)
            InvoiceAdapter = InvoiceAdapter(requireContext())
            recyclerView.layoutManager = LinearLayoutManager(context)
            // call a function to fetch data
            fetchData()

            // find the searchView based on Id

            return view

        }  // end onCreateView()

        fun fetchData(){
            // The function fetch data from the API
            val api = com.example.nurseapplication.constants.Constant.BASE_URL + "/view_invoice_details"
            val helper = ApiHelper(requireContext())
            helper.get(api, object : ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {
                    // result is JSONArray response data
                    // We need a library to convert result(JSONArray) to List<Lab>
                    // we use gson library
                    val gson = GsonBuilder().create()
                    itemList = gson.fromJson(result.toString(), Array<Invoice>::class.java).toList()
                    InvoiceAdapter.setListItems(itemList)
                    recyclerView.adapter = InvoiceAdapter
                }

                override fun onSuccess(result: JSONObject?) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(result: String?) {
                    Toast.makeText(requireContext(), "${result.toString()}", Toast.LENGTH_SHORT).show()
                }
            })
        }



    }