package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.utils

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.example.tiktaktoe_jet.ui.theme.Dimensions
import com.example.tiktaktoe_jet.ui.theme.smallDimensions

@Composable
fun ProvideAppUtils(
    dimensions: Dimensions,
    orientation: Orientation,
    content: @Composable () ->Unit
) {
    val dimSet = remember{dimensions}
    val orientation = remember{orientation}
    CompositionLocalProvider(
        LocalAppDimens provides dimSet,
        LocalOrientationMode provides orientation,
        content = content
    )
}

val LocalAppDimens = compositionLocalOf {
    smallDimensions
}

val LocalOrientationMode = compositionLocalOf {
    Orientation.Vertical
}