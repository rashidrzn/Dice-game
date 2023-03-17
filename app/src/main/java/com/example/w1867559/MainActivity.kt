package com.example.w1867559

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newGame: Button = findViewById(R.id.newgamebtn)
        val about: Button = findViewById(R.id.aboutbtn)

        val userWin = intent.getIntExtra("userWin", 0)
        val comWin = intent.getIntExtra("computerWin", 0)

        println("shit")
        println(userWin)
        println(comWin)

        about.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.aboutpopup)
            dialog.findViewById<ImageView>(R.id.close).setOnClickListener {
                var intt = Intent(this, MainActivity::class.java)
                startActivity(intt)
            }
            dialog.show()
        }

        newGame.setOnClickListener {
            var int = Intent(this, TargetActivity::class.java)
            int.putExtra("userWin", userWin)
            int.putExtra("computerWin", comWin)
            startActivity(int)

        }

    }

    fun showResultPopup() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.aboutpopup)
//        dialog.findViewById<TextView>(R.id.Winner).apply {
//
//        }
        dialog.findViewById<Button>(R.id.cut).setOnClickListener {
            var intt = Intent(this, MainActivity::class.java)
            startActivity(intt)
        }
        dialog.show()
    }
}