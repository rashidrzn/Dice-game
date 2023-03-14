package com.example.w1867559

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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
    lateinit var selectedimageList: MutableList<ImageView>
    var userWin=0
    var comWin=0

    var allUserTotal = 0
    //    lateinit var selectedimageList: MutableList<ImageView>
    lateinit var total: MutableList<Int>

    lateinit var ClickedDices: MutableList<ImageView>

    var attempt = 0
    var localUserScore = 0

    //scores after score btn pressed
    var UScore: Int = 0
    var CScore: Int = 0
    var Count: Int = 0

    var UserScore: Int = 0
    var ComputerScore: Int = 0
    var TargetScore: Int = 0
    var optionalRethrow = 0
    private var throwCount = 0
//    var localUserScore: Int = 0

    //   @SuppressLint("SetTextI18n")
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        total = arrayListOf()
        val user1: ImageView = findViewById(R.id.i_1)
        val user2: ImageView = findViewById(R.id.i_2)
        val user3: ImageView = findViewById(R.id.i_3)
        val user4: ImageView = findViewById(R.id.i_4)
        val user5: ImageView = findViewById(R.id.i_5)
        UserImageList = mutableListOf()
        UserImageList.add(user1)
        UserImageList.add(user2)
        UserImageList.add(user3)
        UserImageList.add(user4)
        UserImageList.add(user5)

        val com1: ImageView = findViewById(R.id.i_6)
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
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)


        selectedimageList = mutableListOf()
        if (savedInstanceState != null) {
            var afterUScore = savedInstanceState.getInt("UserScore", 0)
            userScore.setText("" + afterUScore.toString())

            var afterCScore = savedInstanceState.getInt("comScore", 0)

            computerScore.setText("" + afterCScore.toString())

        }

        val input = intent.getIntExtra("input", 0)
        val textView = findViewById<TextView>(R.id.text_8)
        TargetScore = input
        textView.text = "$input"
        textView.setText(textView.text)
        throwbtn = findViewById(R.id.rollbtn)
        throwbtn.setOnClickListener {
            localUserScore = 0
            if (optionalRethrow == 0) {
                Throw()
                diceClicked()
                optionalRethrow++
            } else {
                if (optionalRethrow == 1 || optionalRethrow == 2) {
                    reRoll()
                    selectedimageList.clear()
                    for (q in UserImageList) {
                        q.clearColorFilter()
                    }
                    optionalRethrow++
                    if (optionalRethrow == 3) {
                        Score()
                        optionalRethrow = 0
                    }
                }
            }
        }

        ScoreBtn = findViewById(R.id.scorebtn)
        ScoreBtn.setOnClickListener {
            Score()
            attempt = 0
        }
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)



    }

    override fun onBackPressed() {
        // Navigate back to the initial screen of the application
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun Score() {
        for ((index, num) in total.withIndex()) {
            if (index < 5) {
                UScore = UScore + num
            }
//            else {
//                CScore = CScore + num
//            }
        }
        total.clear()
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)
        userScore.setText("" + UScore)
        computerScore.setText("" + CScore)
        Count++

        if (UScore > TargetScore || CScore > TargetScore) {
            if (UScore > CScore) {
                userWin++
                var Uwin:TextView=findViewById(R.id.UWin)
                Uwin.setText(""+userWin)
                showResultPopup("User won!", Color.GREEN)
            } else {
                comWin++
                var Cwin:TextView=findViewById(R.id.CWin)
                Cwin.setText(""+comWin)
                showResultPopup("Computer won!", Color.RED)
            }
        }
        throwCount = 0
        optionalRethrow = 0
        selectedimageList.clear()
    }


    fun Throw() {
        UserScore = setImage(UserImageList)
        ComputerScore = setImage(ComputerimageList)
        CScore += ComputerScore
    }

    fun setImage(UserImageList: MutableList<ImageView>): Int {
        localUserScore = 0
        for (a in UserImageList) {
            var an = ((1..5).random())
            if (an == 1) {
                a.setImageResource(R.drawable.die_face_1)
                localUserScore += an
                total.add(an)
            } else if (an == 2) {
                a.setImageResource(R.drawable.die_face_2)
                localUserScore += an
                total.add(an)
            } else if (an == 3) {
                a.setImageResource(R.drawable.die_face_3)
                localUserScore += an
                total.add(an)
            } else if (an == 4) {
                a.setImageResource(R.drawable.die_face_4)
                localUserScore += an
                total.add(an)
            } else if (an == 5) {
                a.setImageResource(R.drawable.die_face_5)
                localUserScore += an
                total.add(an)
            }
        }
        allUserTotal += localUserScore
        return localUserScore

    }

    fun reRoll(): Int {
        localUserScore = 0
//        for (i in selectedimageList) {
//            UserImageList.remove(i)
//        }
        for (x in selectedimageList) {
            println(x)
        }
        for ((index, a) in UserImageList.withIndex()) {
            if (a !in selectedimageList) {
                var an = ((1..5).random())
                if (an == 1) {
                    a.setImageResource(R.drawable.die_face_1)
                    localUserScore += an
                    total[index] = an
                } else if (an == 2) {
                    a.setImageResource(R.drawable.die_face_2)
                    localUserScore += an
                    total[index] = an
                } else if (an == 3) {
                    a.setImageResource(R.drawable.die_face_3)
                    localUserScore += an
                    total[index] = an
                } else if (an == 4) {
                    a.setImageResource(R.drawable.die_face_4)
                    localUserScore += an
                    total[index] = an
                } else if (an == 5) {
                    a.setImageResource(R.drawable.die_face_5)
                    localUserScore += an
                    total[index] = an
                }
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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("localUserScore", localUserScore)
        outState.putInt("UserScore", UScore)
        outState.putInt("comScore", CScore)
        outState.putInt("throwCount", throwCount)

    }

    fun diceClicked() {
        for (i in UserImageList) {
            if (i !in selectedimageList) {
                i.setOnClickListener {
                    selectedimageList.add(i)
                    i.setColorFilter(R.color.purple_500)
                }
            }
        }

    }




}