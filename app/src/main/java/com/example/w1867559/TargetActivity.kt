package com.example.w1867559

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class TargetActivity : AppCompatActivity() {
    var targettt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)

        var target: Button = findViewById(R.id.targetbtn)
        var targetText: TextView = findViewById(R.id.target)


        val userWin = intent.getIntExtra("userWin", 0)
        val comWin = intent.getIntExtra("computerWin", 0)

        if (savedInstanceState != null) {
            var newTarget = savedInstanceState.getInt("target", 0)
            targetText.setText("" + newTarget)
        }




        target.setOnClickListener {

            val targetStr = targetText.text.toString()
            if (targetStr.isNotEmpty() && Integer.parseInt(targetText.text.toString()) >0  ) {
                var targett = targetText.text.toString()
                targettt = targetText.text.toString().toInt()

                var int = Intent(this, NewGame::class.java)
                int.putExtra("userWin", userWin)
                int.putExtra("computerWin", comWin)
                int.putExtra("input", targett.toInt())
                startActivity(int)
            } else{
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("target", targettt)
    }


}