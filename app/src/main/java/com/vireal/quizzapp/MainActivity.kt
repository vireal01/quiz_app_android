
package com.vireal.quizzapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startQuizBtn: Button = findViewById(R.id.startQuizButton)
        val nameInputField: EditText = findViewById(R.id.nameInputField)
        startQuizBtn.setOnClickListener{
            if(nameInputField.text.isEmpty()){
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_LONG).show()
            } else {
                Constants.USER_NAME = nameInputField.text.toString()
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}