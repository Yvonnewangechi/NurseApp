package com.example.nurseapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nurseapplication.adapters.InvoiceAdapter
import com.example.nurseapplication.models.Invoice
import com.google.gson.GsonBuilder
import com.example.nurseapplication.helpers.ApiHelper
import com.example.nurseapplication.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject






class ViewInvoiceFragment : Fragment() {
    lateinit var itemList: List<Invoice>
    lateinit var recyclerView: RecyclerView
    lateinit var invoiceAdapter: InvoiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_invoice, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        invoiceAdapter = InvoiceAdapter(requireContext())  // Update the variable name
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // call a function to fetch data
        fetchData()

        return view
    }

    fun fetchData() {
        // The function fetches data from the API
        val api = com.example.nurseapplication.constants.Constant.BASE_URL + "/view_invoice_details"
        val helper = ApiHelper(requireContext())
        val body = JSONObject()

        val lab_id = PrefsHelper.getPrefs(requireContext(), "lab_id")
        val inv = PrefsHelper.getPrefs(requireContext() ,"Invoice_No")
        body.put("invoice_no", inv)
        helper.post(api, body, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {

                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(), Array<Invoice>::class.java).toList()
                invoiceAdapter.setListItems(itemList)
                recyclerView.adapter = invoiceAdapter
            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(requireContext(), "No invoice found", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {
                Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
            }
        })
    }
}





