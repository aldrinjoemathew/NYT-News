package com.nyt.nytnews.ui.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.HalfBaseSeparation

@Composable
fun Chip(
    name: String = "Chip",
    isSelected: Boolean = false,
    onSelectionChanged: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(HalfBaseSeparation),
        elevation = BaseSeparation,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) Color.LightGray else MaterialTheme.colors.primary
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            )
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(BaseSeparation)
            )
        }
    }
}

@Composable
fun ChipGroup(
    chipItems: List<String> = emptyList(),
    selectedCar: String? = null,
    onSelectedChanged: (String) -> Unit = {},
) {
    FlowRow(
        modifier = Modifier
            .padding(BaseSeparation)
            .fillMaxWidth()
            .verticalScroll(ScrollState(0)),
        mainAxisSpacing = HalfBaseSeparation,
        crossAxisSpacing = HalfBaseSeparation,
        mainAxisAlignment = MainAxisAlignment.Center,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        lastLineMainAxisAlignment = MainAxisAlignment.Center, mainAxisSize = SizeMode.Expand,
    ) {
        chipItems.forEach { item ->
            Chip(
                name = item,
                isSelected = selectedCar == item,
                onSelectionChanged = {
                    onSelectedChanged(it)
                },
            )
        }
    }
}