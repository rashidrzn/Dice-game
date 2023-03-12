package com.example.w1867559

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newGame:Button=findViewById(R.id.newgamebtn)
        val about:Button=findViewById(R.id.aboutbtn)

        about.setOnClickListener {
            var intent=Intent(this,aboutactivity::class.java)
            startActivity(intent)
        }

        newGame.setOnClickListener {
            var int=Intent(this,TargetActivity::class.java)
            startActivity(int)

        }
    }
}