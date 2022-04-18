package com.nyt.nytnews.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyt.nytnews.R
import com.nyt.nytnews.ui.composables.forwardingPainter
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.screens.newsfeed.LoadingItem
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.DoubleSeparation
import java.io.File


@Composable
fun Profile(navigationAction: NytNavigationAction, viewModel: ProfileViewModel) {

    val user by viewModel.user.collectAsState(null)

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        user?.let { user ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(File(user.imagePath))
                    .crossfade(true)
                    .build(),
                placeholder = forwardingPainter(
                    painterResource(id = R.drawable.ic_user),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                ),
                error = forwardingPainter(
                    painterResource(id = R.drawable.ic_user),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                ),
                contentDescription = stringResource(R.string.profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .size(250.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(BaseSeparation))
            ProfileProperty(label = stringResource(id = R.string.tf_name), value = user.name)
            ProfileProperty(label = stringResource(id = R.string.tf_email), value = user.email)
            ProfileProperty(
                label = stringResource(id = R.string.tf_dob),
                value = user.dob.ifEmpty { stringResource(R.string.nil) },
            )
        } ?: run {
            LoadingItem(fullPage = true)
        }
    }
}

@Composable
fun ProfileProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(start = DoubleSeparation, end = DoubleSeparation)) {
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f))
        Spacer(modifier = Modifier.height(BaseSeparation))
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
        )
        Spacer(modifier = Modifier.height(BaseSeparation))
        Text(
            text = value,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(BaseSeparation))
    }
}