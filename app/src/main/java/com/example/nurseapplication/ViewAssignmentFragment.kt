package com.example.nurseapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nurseapplication.adapters.AssignmentAdapter
import com.example.nurseapplication.adapters.InvoiceAdapter
import com.example.nurseapplication.models.Invoice
import com.google.gson.GsonBuilder
import com.example.nurseapplication.helpers.ApiHelper
import com.example.nurseapplication.helpers.PrefsHelper
import com.example.nurseapplication.models.Assignment
import org.json.JSONArray
import org.json.JSONObject






class ViewAssignmentFragment : Fragment() {
    lateinit var itemList: List<Assignment>
    lateinit var recyclerView: RecyclerView
    lateinit var AssignmentAdapter: AssignmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_assignment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        AssignmentAdapter = AssignmentAdapter(requireContext())  // Update the variable name
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val edSearch : EditText = view.findViewById(R.id.search)
        edSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(textChanged: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(textChanged.toString())

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        // call a function to fetch data
        fetchData()

        return view
    }

    fun fetchData() {
        // The function fetches data from the API
        val api = com.example.nurseapplication.constants.Constant.BASE_URL + "/view_assignment"
        val helper = ApiHelper(requireContext())
        val body = JSONObject()

        val nurse_id = PrefsHelper.getPrefs(requireContext(), "LoggedMember_id")
        Toast.makeText(requireContext(), nurse_id, Toast.LENGTH_SHORT).show()
        body.put("nurse_id", nurse_id)
        helper.post(api, body, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(), Array<Assignment>::class.java).toList()
                AssignmentAdapter.setListItems(itemList)
                recyclerView.adapter = AssignmentAdapter
            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(requireContext(), "No Assignment", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {
                Toast.makeText(requireContext(), "Error...", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun filter(text:String){
        // create an array list to filter our labs
        val filteredList: ArrayList<Assignment> = ArrayList()

        // loop through the itemList
        for (item in itemList){
            if (item.invoice_no.lowercase().contains(text.lowercase())){
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()){
            AssignmentAdapter.filterList(filteredList)
        }

        else{
            AssignmentAdapter.filterList(filteredList)
        }

    } // end filter function
}





