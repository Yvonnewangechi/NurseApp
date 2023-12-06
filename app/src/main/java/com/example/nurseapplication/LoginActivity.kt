package com.example.nurseapplication

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.nurseapplication.constants.Constant
import com.example.nurseapplication.helpers.ApiHelper

import com.example.nurseapplication.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nurse_login)



        // find SurnameEditText and passwordEditText
        val username: EditText = findViewById(R.id.editUsername)
        val password : EditText = findViewById(R.id.editPassword)

        // get Login Button and setListener
        val buttonLogin : Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {

            // specify endpoint
            val api: String = Constant.BASE_URL + "/nurse_login"
            // body payload -> JSONObject
            val body = JSONObject()
            body.put("username",username.text.toString())
            body.put("password", password.text.toString())

            val  helper = ApiHelper(applicationContext)
            helper.post(api, body, object : ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {

                }

                override fun onSuccess(result: JSONObject?) {
                    // logged in successfully or invalid credential
                    if(result!!.has("refresh_token")){
                        // save access_token, refresh_token, member to the Prefs

                        val refresh_token = result.getString("refresh_token")
                        val access_token = result.getString("access_token")
                        val message = result.getString("nurse")

                        PrefsHelper.savePrefs(applicationContext, "refresh_token", refresh_token)
                        PrefsHelper.savePrefs(applicationContext, "access_token", access_token)

                        // get member data from the message jsonObject
                        val nurse = JSONObject(message)
                        val nurse_id = nurse.getString("nurse_id")
                        val email = nurse.getString("email")
                        val username = nurse.getString("username")
                        val phone = nurse.getString("phone")

                        PrefsHelper.savePrefs(applicationContext, "LoggedMember_id", nurse_id)
                        PrefsHelper.savePrefs(applicationContext, "LoggedEmail", email)
                        PrefsHelper.savePrefs(applicationContext, "LoggedUsername", username)
                        PrefsHelper.savePrefs(applicationContext, "LoggedPhone", phone)


                        try {
                            val intentHome = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intentHome)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e("MainActivity", "Error launching intent: ${e.message}")
                        }

//                        Toast.makeText(applicationContext, "Login success", Toast.LENGTH_SHORT).show()

                    }  // Login is Success

                    else{
                        Toast.makeText(applicationContext, "${result.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(result: String?) {
                    Toast.makeText(applicationContext, "${result.toString()}", Toast.LENGTH_SHORT).show()
                }
            })


        }
    }
}
