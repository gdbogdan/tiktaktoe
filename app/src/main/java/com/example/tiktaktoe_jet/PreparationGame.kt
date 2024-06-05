package com.example.tiktaktoe_jet

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simonsays_jet.R

@Composable
fun PreparationGame(repository: PreparationRepository) {
    val preparationViewModel: PreparationGameViewModel = viewModel(factory = PreparationGameViewModelFactory(repository))
    val sharedViewModel: SharedViewModel = viewModel() // Using a shared ViewModel

    val aliasText by preparationViewModel.aliasText.collectAsState()
    val time by preparationViewModel.time.collectAsState()
    val difficulty by preparationViewModel.difficulty.collectAsState()

    val context = LocalContext.current // Obtener el contexto actual

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.ajuste), modifier = Modifier.size(50.dp), contentDescription = null)
                Text(text = stringResource(id = R.string.Conf), fontSize = 24.sp, modifier = Modifier.padding(start = 10.dp), fontWeight = FontWeight.Bold)
            }
            TextField(
                value = aliasText,
                onValueChange = {
                    preparationViewModel.setAliasText(it)
                    sharedViewModel.setAliasText(it) // Update the shared ViewModel
                },
                label = { Text("Alias") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.graella),
                    contentDescription = null,
                    Modifier.size(25.dp)
                )
                Text(
                    text = stringResource(id = R.string.Dificultad),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            DifficultySelection(difficulty) { newDifficulty ->
                preparationViewModel.setDifficulty(newDifficulty)
                sharedViewModel.setDifficulty(newDifficulty) // Update the shared ViewModel
            }

            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = time,
                    onCheckedChange = {
                        preparationViewModel.setTime(it)
                        sharedViewModel.setTime(it) // Update the shared ViewModel
                    }
                )
                Text(text = "Time")
            }

            Button(
                onClick = {
                    preparationViewModel.saveData()
                    Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()

                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Save and Continue")
            }
        }
    }
}

@Composable
fun DifficultySelection(selectedDifficulty: String, onDifficultySelected: (String) -> Unit) {
    val difficulties = listOf("individual", "multijugador")
    Row {
        difficulties.forEach { difficulty ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = difficulty == selectedDifficulty,
                    onClick = { onDifficultySelected(difficulty) }
                )
                Text(text = difficulty, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
