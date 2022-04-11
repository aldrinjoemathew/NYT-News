package com.nyt.nytnews.ui.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.nyt.nytnews.ui.theme.*

@Composable
fun RoundedCornerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = {
            onClick()
        }, modifier = modifier,
        shape = NytShapes.medium,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.Black,
            ),
            modifier = Modifier
                .padding(HalfBaseSeparation),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SmallRoundedCornerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = {
            onClick()
        }, modifier = modifier
            .wrapContentSize()
            .padding(
                PaddingValues(
                    horizontal = BaseAndHalfSeparation,
                    vertical = HalfBaseSeparation
                )
            ),
        shape = NytShapes.medium,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.Black,
                fontSize = TextSizeSmallPlus
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ButtonToggleGroup(
    modifier: Modifier,
    items: List<String>,
    selectedItem: Int = 0,
    onSelected: (Int) -> Unit
) {
    val cornerRadius: Dp = BaseSeparation
    val getBorderShape: (Int) -> RoundedCornerShape = { index ->
        when (index) {
            0 -> RoundedCornerShape(
                topStart = cornerRadius,
                bottomStart = cornerRadius,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            )
            items.size - 1 -> RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = cornerRadius,
                bottomEnd = cornerRadius
            )
            else -> RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            )
        }
    }

    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = { onSelected(index) },
                modifier = Modifier
                    .offset((-1 * index).dp, 0.dp)
                    .zIndex(if (selectedItem == index) 1f else 0f)
                    .fillMaxHeight()
                    .width(IntrinsicSize.Min),
                shape = getBorderShape(index),
                border = BorderStroke(
                    width = 1.dp,
                    color =
                    if (selectedItem == index)
                        MaterialTheme.colors.primary
                    else Color.Gray
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor =
                    if (selectedItem == index)
                        MaterialTheme.colors.primary.copy(alpha = 0.1f)
                    else Color.White
                )
            ) {
                Text(
                    text = item,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = TextSizeSmallPlus
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    width: Dp = 70.dp,
    height: Dp = 40.dp,
    loading: Boolean = false,
    onClick: (Boolean) -> Unit
) {
    var contentWidth by remember {
        mutableStateOf(width)
    }
    var contentHeight by remember {
        mutableStateOf(height)
    }
    if (loading) {
        contentWidth = height
        contentHeight = height
    } else {
        contentWidth = width
        contentHeight = height
    }
    Box(
        modifier = modifier
            .size(
                width = sizeAnimationMethod(
                    targetValue = contentWidth,
                    durationMillis = 250
                ), height = sizeAnimationMethod(
                    targetValue = contentHeight,
                    durationMillis = 250
                )
            )
            .clip(
                if (loading) CircleShape
                else RoundedCornerShape(HalfBaseSeparation)
            )
            .clickable {
                onClick(loading)
            }
            .background(MaterialTheme.colors.primary)
            .padding(BaseSeparation),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = loading,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = snap())
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(contentHeight - HalfBaseSeparation),
                color = MaterialTheme.colors.onPrimary,
                strokeWidth = 2.dp,
            )
        }
        AnimatedVisibility(
            modifier = Modifier.wrapContentSize(),
            visible = !loading, enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 1000,
                    delayMillis = 250,
                    easing = LinearOutSlowInEasing
                )
            ), exit = fadeOut()
        ) {
            Text(
                text = "Button",
                style = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = TextSizeNormal
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    text: String,
    loading: Boolean = false,
    onClick: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .requiredHeight(MinimumButtonHeight)
            .clip(
                if (loading) CircleShape
                else RoundedCornerShape(HalfBaseSeparation)
            )
            .clickable {
                onClick(loading)
            }
            .background(MaterialTheme.colors.primary)
            .padding(BaseSeparation)
            .animateContentSize(tween()),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(DoubleAndHalfSeparation),
                color = MaterialTheme.colors.onPrimary,
                strokeWidth = 2.dp,
            )
        } else {
            Text(
                modifier = modifier,
                text = text,
                style = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = TextSizeNormal
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}