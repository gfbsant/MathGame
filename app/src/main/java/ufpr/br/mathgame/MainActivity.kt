package ufpr.br.mathgame

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvMessage: TextView
    private lateinit var etAnswer: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button

    private var num1 = 0
    private var num2 = 0
    private var correctAnswer = 0
    private var currentQuestion = 1
    private var score = 0
    private val totalQuestions = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tvExpression)
        tvMessage = findViewById(R.id.tvExpression)
        etAnswer = findViewById(R.id.etAnswer)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)

        generateNewExpression()

        btnSubmit.setOnClickListener {
            checkAnswer()
        }

        btnNext.setOnClickListener {
            if (currentQuestion < totalQuestions) {
                currentQuestion++
                resetForNextQuestion()
                generateNewExpression()
            } else {
                showFinalScore()
            }
        }
    }

    private fun generateNewExpression() {
        val random = Random()
        num1 = random.nextInt(100)  // Gera número entre 0 e 99
        num2 = random.nextInt(100)

        correctAnswer = num1 + num2
        tvExpression.text = "$num1 + $num2"
    }

    private fun checkAnswer() {
        val answerText = etAnswer.text.toString().trim()

        if (answerText.isNotEmpty()) {
            val userAnswer = answerText.toInt()

            if (userAnswer == correctAnswer) {
                score += 20
                window.decorView.setBackgroundColor(Color.GREEN)
                tvMessage.text = "Correto!"
            } else {
                window.decorView.setBackgroundColor(Color.RED)
                tvMessage.text = "Errado! Resposta correta: $correctAnswer"
            }

            tvMessage.visibility = View.VISIBLE
            btnSubmit.visibility = View.GONE
            btnNext.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "Por favor, insira uma resposta!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetForNextQuestion() {
        window.decorView.setBackgroundColor(Color.WHITE)
        etAnswer.text.clear()
        tvMessage.visibility = View.GONE
        btnSubmit.visibility = View.VISIBLE
        btnNext.visibility = View.GONE
    }

    private fun showFinalScore() {
        val resultMessage = "Sua pontuação final: $score / 100"
        tvExpression.text = resultMessage
        tvMessage.visibility = View.GONE
        etAnswer.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        btnNext.visibility = View.GONE
    }
}
