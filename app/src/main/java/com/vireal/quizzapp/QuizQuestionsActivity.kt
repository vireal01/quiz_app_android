package com.vireal.quizzapp

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    private var progressBar: ProgressBar? = null
    private var questionImage: ImageView? = null
    private var progressBarCounter: TextView? = null
    private var tvQuestion: TextView? = null

    private var firstAnswer: TextView? = null
    private var secondAnswer: TextView? = null
    private var thirdAnswer: TextView? = null
    private var fourthAnswer: TextView? = null
    private var submitBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        questionImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progress_bar)
        progressBarCounter = findViewById(R.id.tv_progress_counter)
        tvQuestion = findViewById(R.id.tv_question)

        firstAnswer = findViewById(R.id.tv_answer_one)
        secondAnswer = findViewById(R.id.tv_answer_two)
        thirdAnswer = findViewById(R.id.tv_answer_three)
        fourthAnswer = findViewById(R.id.tv_answer_four)
        submitBtn = findViewById(R.id.submit_btn)
        firstAnswer?.setOnClickListener(this)
        secondAnswer?.setOnClickListener(this)
        thirdAnswer?.setOnClickListener(this)
        fourthAnswer?.setOnClickListener(this)
        submitBtn?.setOnClickListener(this)
        mQuestionsList = Constants.getQuestions()
        setQuestion()
    }

    private fun setQuestion() {
        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        progressBarCounter?.text = "$mCurrentPosition/${progressBar?.max}"
        Constants.TOTAL_QUESTION = mQuestionsList!!.size
        tvQuestion?.text = question.question
        firstAnswer?.text = question.optionOne
        secondAnswer?.text = question.optionTwo
        thirdAnswer?.text = question.optionThree
        fourthAnswer?.text = question.optionFour
        questionImage?.setImageResource(question.image)

        if(mCurrentPosition == mQuestionsList!!.size){
            submitBtn?.text = "finish"
        } else {
            submitBtn?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        firstAnswer?.let { options.add(0, it) }
        secondAnswer?.let { options.add(1, it) }
        thirdAnswer?.let { options.add(2, it) }
        fourthAnswer?.let { options.add(3, it) }

        for(option in options){
            option.setTextColor(ResourcesCompat.getColor(resources, R.color.grayText, null))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(ResourcesCompat.getColor(resources, R.color.purple_500, null))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_answer_one -> {
                firstAnswer?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_answer_two -> {
                secondAnswer?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_answer_three -> {
                thirdAnswer?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_answer_four -> {
                fourthAnswer?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.submit_btn -> {
                defaultOptionsView()
                if(mSelectedOptionPosition == 0 && submitBtn?.text != "next question"){
                    return Toast.makeText(this,"Choose an answer", Toast.LENGTH_LONG).show()
                }
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        } else -> {
                            Toast.makeText(this, "No more questions", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val question: Question? = mQuestionsList?.get(mCurrentPosition - 1)
                    if (mSelectedOptionPosition != question?.correctAnswer) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        Constants.CORRECT_ANSWERS++
                    }
                    answerView(question!!.correctAnswer, R.drawable.correct_option_border_bg)
                    if (mCurrentPosition == mQuestionsList?.size!!) {
                        submitBtn?.text = "finish"
                        submitBtn?.setOnClickListener{
                            val intent = Intent(this, ResultsActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        submitBtn?.text = "next question"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> {
                firstAnswer?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                secondAnswer?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                thirdAnswer?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                fourthAnswer?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}