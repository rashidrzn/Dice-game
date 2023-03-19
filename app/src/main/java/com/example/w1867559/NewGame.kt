package com.example.w1867559

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random


class NewGame : AppCompatActivity() {

    private lateinit var userScore: TextView
    private lateinit var computerScore: TextView
    lateinit var Uwin: TextView
    lateinit var CWin: TextView
    lateinit var throwBtn: Button
    lateinit var scoreBtn: Button
    lateinit var userImageList: MutableList<ImageView>
    lateinit var computerImageList: MutableList<ImageView>
    lateinit var selectedImageList: MutableList<ImageView>
    lateinit var diceImage:MutableList<Int>

    var userWin = 0 //no of times that the user has won
    var comWin = 0 //no of time that the computer has won
    var allUserTotal = 0
    lateinit var total: MutableList<Int>

    var attempt = 0
    var localUserScore = 0

    //scores after score btn pressed
    var hScore: Int = 0
    var cScore: Int = 0

    var humanScore: Int = 0
    var cmputrScore: Int = 0
    var targetScore: Int = 0
    var optionalRethrow = 0
    private var throwCount = 0

    var c1 = 0
    var c2 = 0
    var c3 = 0
    var c4 = 0
    var c5 = 0
    var c6 = 0


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val textView = findViewById<TextView>(R.id.text_8)
        val input = intent.getIntExtra("input", 0)
        targetScore = input
        textView.text = "$input"
        textView.setText(textView.text)

        diceImage= arrayListOf()
        Uwin = findViewById(R.id.UWin)
        CWin= findViewById(R.id.CWin)
        userWin = intent.getIntExtra("userWin", 0)
        comWin = intent.getIntExtra("computerWin", 0)
        Uwin.setText(userWin.toString())
        CWin.setText(comWin.toString())
        println("shit")
        println(userWin)
        println(comWin)


        total = arrayListOf()

        val user1: ImageView = findViewById(R.id.i_1)
        val user2: ImageView = findViewById(R.id.i_2)
        val user3: ImageView = findViewById(R.id.i_3)
        val user4: ImageView = findViewById(R.id.i_4)
        val user5: ImageView = findViewById(R.id.i_5)
        userImageList = mutableListOf()
        userImageList.add(user1)
        userImageList.add(user2)
        userImageList.add(user3)
        userImageList.add(user4)
        userImageList.add(user5)

        val com1: ImageView = findViewById(R.id.i_6)
        val com2: ImageView = findViewById(R.id.i_7)
        val com3: ImageView = findViewById(R.id.i_8)
        val com4: ImageView = findViewById(R.id.i_9)
        val com5: ImageView = findViewById(R.id.i_10)
        computerImageList = mutableListOf()
        computerImageList.add(com1)
        computerImageList.add(com2)
        computerImageList.add(com3)
        computerImageList.add(com4)
        computerImageList.add(com5)
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)
        throwBtn = findViewById(R.id.rollbtn)
        scoreBtn = findViewById(R.id.scorebtn)
//        userScore = findViewById(R.id.UScore)
//        computerScore = findViewById(R.id.CScore)


        selectedImageList = mutableListOf()
        if (savedInstanceState != null) {
            val afterUScore = savedInstanceState.getInt("UserScore", 0)
            userScore.setText("" + afterUScore.toString())

            val afterCScore = savedInstanceState.getInt("comScore", 0)
            computerScore.setText("" + afterCScore.toString())

            var humanWin=savedInstanceState.getInt("humanWin",0)
            Uwin.setText(""+humanWin)
            var computerWin=savedInstanceState.getInt("computerWin",0)
            CWin.setText(""+computerWin)
        }


        throwBtn.setOnClickListener {
            localUserScore = 0
            if (optionalRethrow == 0) {
                Throw()
                diceClicked()
                optionalRethrow++
            } else {
                if (optionalRethrow == 1 || optionalRethrow == 2) {
                    reRoll()
                    selectedImageList.clear()
                    for (q in userImageList) {
                        q.clearColorFilter()
                        val toast = Toast.makeText(
                            this,
                            "you have only one re roll available",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                    optionalRethrow++
                    if (optionalRethrow == 3) {
                        val toast =
                            Toast.makeText(this, "your scores are updated!", Toast.LENGTH_SHORT)
                        toast.show()
                        Score()
                        optionalRethrow = 0
                    }
                }
            }
        }


        scoreBtn.setOnClickListener {
            Score()
            attempt = 0
        }


    }

    override fun onBackPressed() {
        // Navigate back to the initial screen of the application
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userWin", userWin)
        intent.putExtra("computerWin", comWin)
        println(userWin)
        println(comWin)
        startActivity(intent)
    }

    fun Score() {
        compReRoll()
        for ((index, num) in total.withIndex()) {
            if (index < 5) {
                hScore = hScore + num
            } else {
                cScore = cScore + num
            }
        }
//        println(total.size)
        total.clear()
        diceImage.clear()
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)
        userScore.setText("" + hScore)
        computerScore.setText("" + cScore)
//        Count++

        if (hScore > targetScore || cScore > targetScore) {
            if (hScore > cScore) {
                userWin++
                println(userWin)
                Uwin = findViewById(R.id.UWin)
                Uwin.setText("" + userWin)
                showResultPopup("User won!", Color.GREEN)
                scoreBtn.isEnabled=false
                throwBtn.isEnabled=false
//                showResultPopup("The game has finished. If you want to restart the game please press back button ",R.color.red)
            } else if (hScore == cScore) {
                humanScore = setImage(userImageList)
                hScore += humanScore
                cmputrScore = setImage(computerImageList)
                cScore += cmputrScore

                userScore.setText("" + hScore)
                computerScore.setText("" + cScore)
                if (hScore > cScore) {
                    showResultPopup("User won!", Color.GREEN)

                } else {
                    showResultPopup("Computer won!", Color.RED)
                }
                scoreBtn.isEnabled=false
                throwBtn.isEnabled=false
            } else {
                comWin++
                CWin= findViewById(R.id.CWin)
                CWin.setText("" + comWin)
                showResultPopup("Computer won!", Color.RED)
                scoreBtn.isEnabled=false
                throwBtn.isEnabled=false
                scoreBtn.setOnClickListener {
//                    showRestartPopup("The game has finished. If you want to restart the game please press back button ",R.color.red)
                }

            }
        }
        throwCount = 0
        optionalRethrow = 0
        selectedImageList.clear()
    }


    fun Throw() {
        humanScore = setImage(userImageList)
        cmputrScore = setImage(computerImageList)
//        CScore += ComputerScore
    }

    fun setImage(UserImageList: MutableList<ImageView>): Int {
        localUserScore = 0
        for (a in UserImageList) {
            var an = ((1..6).random())
            if (an == 1) {
                a.setImageResource(R.drawable.die_face_1)
                diceImage.add(R.drawable.die_face_1)
                localUserScore += an
                total.add(an)
            } else if (an == 2) {
                a.setImageResource(R.drawable.die_face_2)
                diceImage.add(R.drawable.die_face_2)
                localUserScore += an
                total.add(an)
            } else if (an == 3) {
                a.setImageResource(R.drawable.die_face_3)
                diceImage.add(R.drawable.die_face_3)
                localUserScore += an
                total.add(an)
            } else if (an == 4) {
                a.setImageResource(R.drawable.die_face_4)
                diceImage.add(R.drawable.die_face_4)
                localUserScore += an
                total.add(an)
            } else if (an == 5) {
                a.setImageResource(R.drawable.die_face_5)
                diceImage.add(R.drawable.die_face_5)
                localUserScore += an
                total.add(an)
            } else if (an == 6) {
                a.setImageResource(R.drawable.die_face_6)
                diceImage.add(R.drawable.die_face_6)
                localUserScore += an
                total.add(an)
            }
        }
        allUserTotal += localUserScore
        return localUserScore

    }

    // this re-roll method is for the human dices
    fun reRoll(): Int {

        localUserScore = 0

        for ((index, a) in userImageList.withIndex()) {
            if (a !in selectedImageList) {
                var an = ((1..6).random())
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
                } else if (an == 6) {
                    a.setImageResource(R.drawable.die_face_6)
                    localUserScore += an
                    total[index] = an
                }

            }
        }
        return localUserScore
    }


//    to show the pop up messages whether win or loose
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



    fun diceClicked() {
        for (i in userImageList) {
            if (i !in selectedImageList) {
                i.setOnClickListener {
                    selectedImageList.add(i)
                    i.setColorFilter(R.color.purple_500)
                }
            }
        }

    }

    /*
    for the computer re-roll strategy I decided to do with the help of random number
    Computer will be rolled for the first time when we press the throw btn with the human dices
    and there onwards we calling this function compReRoll().
    let's talk brief about this function
    first of all, in this function its generate a random number between 0 and 2.
    and if the computer wants the re-roll option it will take all the 2 options
    if the random number == 1 , only computer will be go into the re-roll strategy
    in  the first re-roll it will store the values a variable called "localCmptScore"
    and we calculate a value by (localCmptScore % 5) and store it into a variable "average"
    and in the second re-roll the computer will go  dice by dice to see if the value of the dice is less than "average"
            if its the case the second re-roll will be happen and the dice will be rolled and and the score will be updated
     */
    fun compReRoll() {

        var cList = mutableListOf<Int>()
        var reRollBoolNum = Random.nextInt(0, 2)
        // if the reRollBoolNum is == 1 then only the computer dice thinks of re rolling
        if (reRollBoolNum == 1) {
            var localCmptScore = 0

            cList.clear()
            var average = 0
            var reRollCount = 0
            while (reRollCount < 2 ) {
                reRollCount++
                for ((index, a) in computerImageList.withIndex()) {
//                    var reRellDice = Random.nextInt(1, 1)
                    val reRellDice = 1
                    if (reRellDice == 1) {
                        var an = ((1..6).random())
                        if (an == 1) {
                            a.setImageResource(R.drawable.die_face_1)
                            localCmptScore += an
                            c1 = an
                            total[index + 5] = an
                            cList.add(c1)
                            if (c1 < average) {
                                a.setColorFilter(R.color.teal_200)
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.clearColorFilter()
                                }
                                val toast = Toast.makeText(
                                    this,
                                    "1 value dice got re-rolled",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }

                        } else if (an == 2) {
                            a.setImageResource(R.drawable.die_face_2)
                            localCmptScore += an
                            c2 = an
                            cList.add(c2)
                            total[index + 5] = an
                            a.setColorFilter(R.color.teal_200)
                            if (c2 < average) {
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.clearColorFilter()
                                }
                                val toast = Toast.makeText(
                                    this,
                                    "value of 2 dice got re-rolled",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }

                        } else if (an == 3) {
                            a.setImageResource(R.drawable.die_face_3)
                            localCmptScore += an
                            c3 = an
                            total[index + 5] = an
                            a.setColorFilter(R.color.teal_200)
                            cList.add(c3)
                            if (c3 < average) {
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.clearColorFilter()
                                }
                                val toast = Toast.makeText(
                                    this,
                                    "value of 3 dice got re-rolled",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }

                        } else if (an == 4) {
                            a.setImageResource(R.drawable.die_face_4)
                            localCmptScore += an
                            c4 = an
                            cList.add(c4)
                            total[index + 5] = an
                            a.setColorFilter(R.color.teal_200)
                            if (c4 < average) {
                                cList.remove(c4)
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.clearColorFilter()
                                }
                                val toast = Toast.makeText(
                                    this,
                                    "value of 4 dice got re-rolled",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }

                        } else if (an == 5) {
                            a.setImageResource(R.drawable.die_face_5)
                            localCmptScore += an
                            c5 = an
                            cList.add(c5)
                            total[index + 5] = an
                            a.setColorFilter(R.color.teal_200)
                            if (c5 < average) {
                                cList.remove(c5)
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.clearColorFilter()
                                }
                                total[index + 5] = an
                                val toast = Toast.makeText(
                                    this,
                                    "value of 5 dice got re-rolled",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }

                        } else if (an == 6) {
                            a.setImageResource(R.drawable.die_face_6)
                            localCmptScore += an
                            c6 = an
                            cList.add(c6)
                            total[index + 5] = an
                            a.setColorFilter(R.color.teal_200)
                            if (c6 < average) {
                                cList.remove(c6)
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        a.setColorFilter(R.color.red)
                                        total[index + 5] = 6
                                    }
                                    a.clearColorFilter()
                                }
                                val toast = Toast.makeText(
                                    this,
                                    "value of 6 dice got re-rolled",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }

                        }

                        a.clearColorFilter()
                    }
                }
                average = localCmptScore / 5

                println("average : $average")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("localUserScore", localUserScore)
        outState.putInt("UserScore", hScore)
        outState.putInt("comScore", cScore)
        outState.putInt("humanWin",userWin)
        outState.putInt("computerWin",comWin)
        var newHumanArray= intArrayOf(10)

        for ((index,x) in diceImage.withIndex()){
            newHumanArray[index]= x
            println(newHumanArray[index])
        }
        outState.putIntArray("humanDiceList",newHumanArray)
    }


}