package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.deinname.mixersreise.MixerWorldScreen
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.ui.components.MixerToolBar
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun HomeScreen(
    viewModel: MixerViewModel,
    onOpenMap: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MixerTopBar(
            level = viewModel.level,
            hearts = viewModel.totalHearts,
            onOpenMap = onOpenMap,
            onOpenSettings = onOpenSettings
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MixerWorldScreen(viewModel = viewModel)
        }

        MixerToolBar(viewModel = viewModel)
    }
}