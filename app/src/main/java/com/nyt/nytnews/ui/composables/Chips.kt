package com.nyt.nytnews.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    val chipColor = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
    Surface(
        modifier = Modifier.padding(HalfBaseSeparation),
        elevation = BaseSeparation,
        shape = MaterialTheme.shapes.large,
        color = chipColor,
        border = BorderStroke(1.dp, contentColorFor(backgroundColor = chipColor))
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
                color = contentColorFor(chipColor),
                modifier = Modifier.padding(BaseSeparation)
            )
        }
    }
}

@Composable
fun ChipGroup(
    modifier: Modifier,
    chipItems: List<String> = emptyList(),
    selectedItem: String? = null,
    onSelectedChanged: (String) -> Unit = {},
) {
    FlowRow(
        modifier = modifier
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
                isSelected = selectedItem == item,
                onSelectionChanged = {
                    onSelectedChanged(it)
                },
            )
        }
    }
}