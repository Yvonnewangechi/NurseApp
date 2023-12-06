package com.example.nurseapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.nurseapplication.constants.Constant
import com.example.nurseapplication.helpers.ApiHelper
import com.example.nurseapplication.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class ChangePasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        val currentPassword = view.findViewById<EditText>(R.id.editCurrentPaaword)
        val newPassword = view.findViewById<EditText>(R.id.editNewPassword)
        val confirmPassword = view.findViewById<EditText>(R.id.editConfirmPassword)
        val button = view.findViewById<Button>(R.id.buttonChangePassword)
        button.setOnClickListener {
            updatePassword(
                currentPassword,
                newPassword,
                confirmPassword
            )
        }
        return view
    }

    private fun updatePassword (
        currentPassword: EditText ,
        newPassword : EditText ,
        confirmPassword : EditText
    ){
        val api = Constant.BASE_URL + "/change_password"
        val helper = ApiHelper(requireContext())
        val body = JSONObject()

        val id = PrefsHelper.getPrefs(requireContext(),"LoggedMember_id")
        body.put("nurse_id",id)
        body.put("current_password",currentPassword.text.toString())
        body.put("new_password",newPassword.text.toString())
        body.put("confirm_password",confirmPassword.text.toString())

        helper.post(api , body , object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(requireContext(), result.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {
                Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            }
        })
    }


}