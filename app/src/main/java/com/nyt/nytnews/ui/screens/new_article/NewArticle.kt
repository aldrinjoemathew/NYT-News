package com.nyt.nytnews.ui.screens.new_article

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nyt.nytnews.R
import com.nyt.nytnews.ui.composables.ProgressButton
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.HalfBaseSeparation
import com.nyt.nytnews.ui.theme.NytTheme

@Composable
fun NewArticle(navigationAction: NytNavigationAction, viewModel: NewArticleViewModel) {
    val scrollState = rememberScrollState()
    var title by rememberSaveable { mutableStateOf("") }
    var abstractContent by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    val createArticleInProgress by viewModel.createArticleInProgress.collectAsState()

    if (createArticleInProgress == NewArticleViewModel.ArticleCreationProgress.Complete)
        LaunchedEffect(key1 = createArticleInProgress, block = {
            navigationAction.popBackstack()
        })

    Scaffold(
        modifier = Modifier.padding(HalfBaseSeparation),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigationAction.popBackstack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colors.surface,
                ),
                title = {
                    Text(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(BaseSeparation),
                        text = "Create a new article!",
                        style = MaterialTheme.typography.h6
                    )
                })
        }
    ) { padding ->
        Column {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .weight(1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                NewArticleLabel(labelText = "Title")
                NewArticleField(
                    modifier = Modifier.height(50.dp),
                    value = title,
                    onValueChange = { title = it },
                )
                NewArticleLabel(labelText = "Content abstract")
                NewArticleField(
                    modifier = Modifier.height(100.dp),
                    value = abstractContent,
                    onValueChange = { abstractContent = it },
                )
                NewArticleLabel(labelText = "Content")
                NewArticleField(
                    modifier = Modifier.height(300.dp),
                    value = content,
                    onValueChange = { content = it },
                )
            }
            ProgressButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = isValidArticle(title, abstractContent, content),
                text = stringResource(R.string.save),
                onClick = { isLoading ->
                    if (!isLoading && isValidArticle(title, abstractContent, content))
                        viewModel.createArticle(title, abstractContent, content)
                },
                loading = createArticleInProgress == NewArticleViewModel.ArticleCreationProgress.Loading
            )
        }
    }
}

fun isValidArticle(title: String, abstractContent: String, content: String): Boolean {
    return title.isNotEmpty() && abstractContent.isNotEmpty() && content.isNotEmpty()
}

@Composable
fun NewArticleLabel(labelText: String) {
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.01f)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(BaseSeparation),
            text = labelText,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun NewArticleField(modifier: Modifier, value: String, onValueChange: (String) -> Unit) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
fun NewArticleFieldItemDarkPreview() {
    NytTheme(
        darkTheme = true
    ) {
        Column {
            NewArticleLabel(labelText = "Title")
            NewArticleField(
                modifier = Modifier.height(300.dp),
                value = "India wins tri-series",
                onValueChange = {})
        }
    }
}

@Preview
@Composable
fun NewArticleFieldItemLightPreview() {
    NytTheme(
        darkTheme = false
    ) {
        Column {
            NewArticleLabel(labelText = "Title")
            NewArticleField(
                modifier = Modifier,
                value = "India wins tri-series",
                onValueChange = {},
            )
        }
    }
}