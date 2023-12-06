package com.example.nurseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        val bottomSheet: FrameLayout = findViewById(R.id.bottomSheet)
        BottomSheetBehavior.from(bottomSheet).apply {
            peekHeight = 150
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        // bottom to Login
        val buttonLogin : Button = findViewById(R.id.buttonLoginButton)
        buttonLogin.setOnClickListener {
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
        }

        // bottom to Login
        val buttonAssign : Button = findViewById(R.id.bottomViewAssignment)
        buttonAssign.setOnClickListener {
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }


//        // Inside BottomSheet Activity
//        val buttonHome : Button = findViewById(R.id.bottomViewAssignment)
//        buttonHome.setOnClickListener {
//            val fragment = ViewAssignmentFragment()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.frame_container, fragment)  // Replace 'R.id.container' with the ID of the container in your activity layout
//                .addToBackStack(null)
//                .commit()
//        }


    }
}