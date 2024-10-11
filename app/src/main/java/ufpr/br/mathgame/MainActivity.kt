package ufpr.br.mathgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ufpr.br.mathgame.ui.theme.MathGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathGameTheme {
                MathGameScreen()
            }
        }
    }
}

@Composable
fun MathGameScreen() {
    var questao by remember { mutableStateOf(1) }
    var num1 by remember { mutableStateOf(Random.nextInt(100)) }
    var num2 by remember { mutableStateOf(Random.nextInt(100)) }
    var correta by remember { mutableStateOf(num1 + num2) }
    var respUser by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("") }
    var score by remember { mutableStateOf(0) }
    var backgroundColor by remember { mutableStateOf(Color.White) }

    if (questao <= 5) {
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Questão $questao: $num1 + $num2 = ?")

                Spacer(modifier = Modifier.height(16.dp))

                BasicTextField(
                    value = respUser,
                    onValueChange = { respUser = it },
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val userAns = respUser.toIntOrNull()
                    if (userAns == correta) {
                        feedback = "Correto!"
                        backgroundColor = Color.Green
                        score += 20
                    } else {
                        feedback = "Errado! Resposta correta de : $num1 + $num2 = $correta"
                        backgroundColor = Color.Red
                    }
                    questao++
                    if (questao <= 5) {
                        num1 = Random.nextInt(100)
                        num2 = Random.nextInt(100)
                        correta = num1 + num2
                        respUser = ""
                        backgroundColor = Color.White
                    }
                }) {
                    Text(text = "Responder")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = feedback)
            }
        }
    } else {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Fim do jogo! Sua pontuação: $score / 100")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MathGamePreview() {
    MathGameTheme {
        MathGameScreen()
    }
}
