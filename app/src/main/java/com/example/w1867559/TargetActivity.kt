package com.example.w1867559

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class TargetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)

        var target: Button = findViewById(R.id.targetbtn)
        var targetText: TextView = findViewById(R.id.target)

        target.setOnClickListener {
            var targett = targetText.text.toString()
            if (targett.isNotBlank() && targett.toInt() > 0) {
                var intnt =
                    Intent(this, NewGame::class.java).apply { putExtra("input", targett.toInt()) }
                startActivity(intnt)
            } else {
                val message = "Please set a target in the above text field"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this, message, duration)
                toast.show()
            }
        }
    }
    override fun onBackPressed() {
        // Navigate back to the initial screen of the application
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}