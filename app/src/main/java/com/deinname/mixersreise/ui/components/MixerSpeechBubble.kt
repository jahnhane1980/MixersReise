package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MixerSpeechBubble(viewModel: MixerViewModel) {
    val dialog = viewModel.activeDialog

    if (dialog != null) {
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 250.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Die Frage von Mixer
                Text(
                    text = dialog.question,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Die Antwort-Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    dialog.options.forEach { (label, reaction) ->
                        Button(
                            onClick = { viewModel.selectDialogOption(reaction) },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = label, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}