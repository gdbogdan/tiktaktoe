package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simonsays_jet.R
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.GameViewModel

@Composable
fun Log(modifier: Modifier = Modifier, viewModel: GameViewModel = viewModel()) {
    val moves by viewModel.moves.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = stringResource(id = R.string.Logs), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(moves) { move ->
                Text(text = move)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
