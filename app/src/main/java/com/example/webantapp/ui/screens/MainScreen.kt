package com.example.webantapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.webantapp.R
import com.example.webantapp.model.Photo
import com.example.webantapp.model.Urls
import com.example.webantapp.model.User
import com.example.webantapp.navigation.NavigationDestination
import com.example.webantapp.network.photoRepository
import com.example.webantapp.ui.theme.black
import com.example.webantapp.ui.theme.darkGrey
import com.example.webantapp.ui.theme.grey
import com.example.webantapp.ui.theme.purple
import com.example.webantapp.view_models.MainScreenViewModel
import com.example.webantapp.view_models.PhotoUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


object MainScreenDestination : NavigationDestination {
    override val route = "home"
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
val viewModel = MainScreenViewModel(photoRepository = photoRepository)


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainScreen(
    navigateToPhoto: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WebAntTextField()
        WebAntTabSwitch()
        WebAntPhotos(
            uiState = viewModel.photoUiState,
            onCardClicked = { navigateToPhoto() }
        )

    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun WebAntPhotos(uiState: PhotoUiState, onCardClicked: () -> Unit) {
    when (uiState) {
        is PhotoUiState.Loading -> LoadingScreen()
        is PhotoUiState.Error -> ErrorScreen()
        is PhotoUiState.Success -> PhotoList(photos = uiState.photos, onCardClicked = onCardClicked)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PhotoList(photos: List<Photo>, onCardClicked: () -> Unit) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.getPhotos()
        delay(1500)
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    Box(modifier = Modifier.pullRefresh(state)){LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = photos, key = { photo -> photo.id }) { photo ->
            PhotoCard(photo, onCardClicked)
        }
    }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))}

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PhotoCard(photo: Photo, onCardClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clickable {
                onCardClicked()
                viewModel.selectCurrentPhoto(photo)
            },
        colors = CardDefaults.cardColors(containerColor = grey)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.urls?.small)
                .crossfade(true).build(),
            error = painterResource(id = R.drawable.error_ant),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.photo),
            modifier = Modifier.fillMaxSize()
        )
    }

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun LoadingScreen() {

    LaunchedEffect(key1 = true) {
        viewModel.rotateIcon()
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.loading_circle),
            contentDescription = stringResource(R.string.loading_),
            tint = darkGrey,
            modifier = Modifier
                .size(50.dp)
                .rotate(viewModel.loadingDegrees.value)
        )
        Text(text = stringResource(R.string.loading), color = darkGrey)
    }
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ErrorScreen() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.error_ant),
                contentDescription = stringResource(R.string.error),
                tint = darkGrey,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )
            Text(text = stringResource(R.string.sorry), fontSize = 22.sp, color = darkGrey)
            Text(text = stringResource(R.string.there_is_no_pictures), color = darkGrey)
            Text(text = stringResource(R.string.please_come_back_later), color = darkGrey)
        }
    }

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun WebAntTextField() {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = viewModel.textInput,
        onValueChange = { textInput ->
            viewModel.updateTextInput(textInput)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            if (viewModel.textInput.isBlank()) {
                viewModel.getPhotos()
            } else {
                viewModel.getPhotosOnQuery()
            }
            keyboardController?.hide()
        }),
        placeholder = { Text(text = stringResource(R.string.search)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(id = R.string.search),
                modifier = Modifier.padding(start = 15.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = grey,
            unfocusedContainerColor = grey,
            disabledContainerColor = grey,
            focusedIndicatorColor = grey,
            unfocusedIndicatorColor = grey,
            disabledIndicatorColor = grey,
        ),
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun WebAntTabSwitch() {
    Row(
        modifier = Modifier
            .padding(vertical = 18.dp)
            .size(height = 40.dp, width = 500.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .fillMaxHeight()
                .clickable { viewModel.openNewTab() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            if (viewModel.isNewTabOpened) {
                WebAntText(text = stringResource(R.string.new_text))
                UnderLine(purple)
            } else {
                WebAntText(text = stringResource(R.string.new_text), color = darkGrey)
                UnderLine(grey)
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { viewModel.openPopularTab() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (viewModel.isPopularTabOpened) {
                WebAntText(text = stringResource(R.string.popular))
                UnderLine(purple)
            } else {
                WebAntText(text = stringResource(R.string.popular), color = darkGrey)
                UnderLine(grey)
            }
        }
    }
}

@Composable
fun WebAntText(text: String, color: Color = black, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = modifier,
        color = color
    )
}

@Composable
fun UnderLine(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .size(height = 2.dp, width = 1000.dp)
    )
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(navigateToPhoto = {}, modifier = Modifier)
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ErrorScreenPreview() {
    val mockData = List(100) { Photo(id = it.toString(), urls = Urls(), user = User()) }
    PhotoList(mockData, onCardClicked = { TODO() })
}