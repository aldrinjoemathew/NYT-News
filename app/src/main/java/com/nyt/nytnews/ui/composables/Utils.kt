package com.nyt.nytnews.ui.composables

import androidx.annotation.DimenRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun sizeAnimationMethod(targetValue: Dp, durationMillis: Int): Dp {
    val size by animateDpAsState(
        targetValue = targetValue,
        tween(
            durationMillis = durationMillis,
            easing = LinearOutSlowInEasing
        )
    )
    return size
}

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp