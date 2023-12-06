package com.example.nurseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.nurseapplication.constants.Constant
import com.example.nurseapplication.helpers.ApiHelper
import com.example.nurseapplication.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)


    }
}