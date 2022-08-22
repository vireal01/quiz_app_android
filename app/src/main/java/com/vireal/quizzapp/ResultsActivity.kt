package com.vireal.quizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val finishQuizBtn: Button = findViewById(R.id.finishQuiz)
        val tvUsername: TextView = findViewById(R.id.userName)
        val tvScore: TextView = findViewById(R.id.score)


        tvUsername.text = Constants.USER_NAME
        tvScore.text = "Your score is ${Constants.CORRECT_ANSWERS} out of ${Constants.TOTAL_QUESTION}"
        finishQuizBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Constants.CORRECT_ANSWERS = 0
        }

    }
}