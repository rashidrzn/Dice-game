package com.example.w1867559

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class NewGame : AppCompatActivity() {

    private lateinit var userScore: TextView
    private lateinit var computerScore: TextView
    lateinit var throwbtn: Button
    lateinit var ScoreBtn: Button
    lateinit var UserImageList: MutableList<ImageView>
    lateinit var ComputerimageList: MutableList<ImageView>
    var UScore: Int = 0
    var CScore: Int = 0
    var Count: Int = 0
    var UserScore: Int = 0
    var ComputerScore: Int = 0
    var TargetScore: Int = 0

    //   @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val input = intent.getIntExtra("input", 0)
        val textView = findViewById<TextView>(R.id.text_8)
        TargetScore = input
        textView.text = "$input"
        textView.setText(textView.text)

        throwbtn = findViewById(R.id.rollbtn)
           throwbtn.setOnClickListener {
            Throw()
       }

        ScoreBtn = findViewById(R.id.scorebtn)
        ScoreBtn.setOnClickListener {
            Score()
        }
//        ScoreBtn.setOnClickListener {
//            userScore = findViewById(R.id.userScore)
//            computerScore = findViewById(R.id.computerScore)
//            userScore.setText("" + UScore)
//            computerScore.setText("" + CScore)
//            Count++
//
//            if (Count == 5) {
//                if (UScore > CScore) {
//                    showResultPopup("User won", Color.GREEN)
//                } else {
//                    showResultPopup("User won", Color.RED)
//                }
//            }
//        }
    }

    override fun onBackPressed() {
        // Navigate back to the initial screen of the application
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun Score() {
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)
        userScore.setText("" + UScore)
        computerScore.setText("" + CScore)
        Count++

        if (UScore>TargetScore || CScore>TargetScore) {
            if (UScore > CScore) {
                showResultPopup("User won!", Color.GREEN)
            } else {
                showResultPopup("Computer won!", Color.RED)
            }
        }
    }


    fun Throw() {

        val user1: ImageView = findViewById(R.id.i_1)
        var user2: ImageView = findViewById(R.id.i_2)
        var user3: ImageView = findViewById(R.id.i_3)
        var user4: ImageView = findViewById(R.id.i_4)
        var user5: ImageView = findViewById(R.id.i_5)

        var com1: ImageView = findViewById(R.id.i_6)
        var com2: ImageView = findViewById(R.id.i_7)
        var com3: ImageView = findViewById(R.id.i_8)
        var com4: ImageView = findViewById(R.id.i_9)
        var com5: ImageView = findViewById(R.id.i_10)

        ComputerimageList = mutableListOf()
        ComputerimageList.add(com1)
        ComputerimageList.add(com2)
        ComputerimageList.add(com3)
        ComputerimageList.add(com4)
        ComputerimageList.add(com5)

        UserImageList = mutableListOf()
        UserImageList.add(user1)
        UserImageList.add(user2)
        UserImageList.add(user3)
        UserImageList.add(user4)
        UserImageList.add(user5)

        UserScore = setImage(UserImageList, UserScore)
        UScore += UserScore
        ComputerScore = setImage(ComputerimageList, ComputerScore)
        CScore += ComputerScore
    }

    fun setImage(UserImageList: MutableList<ImageView>, localUserScore: Int): Int {
        var localUserScore = 0

        for (a in UserImageList) {
            var an = ((1..5).random())
            if (an == 1) {
                a.setImageResource(R.drawable.die_face_1)
                localUserScore += an
            } else if (an == 2) {
                a.setImageResource(R.drawable.die_face_2)
                localUserScore += an
            } else if (an == 3) {
                a.setImageResource(R.drawable.die_face_3)
                localUserScore += an
            } else if (an == 4) {
                a.setImageResource(R.drawable.die_face_4)
                localUserScore += an
            } else if (an == 5) {
                a.setImageResource(R.drawable.die_face_5)
                localUserScore += an
            }
        }
        return localUserScore
    }


    fun showResultPopup(message: String, color: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)
        dialog.findViewById<TextView>(R.id.Winner).apply {
            text = message
            this.setBackgroundColor(color)
        }
        dialog.findViewById<Button>(R.id.okButton).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}