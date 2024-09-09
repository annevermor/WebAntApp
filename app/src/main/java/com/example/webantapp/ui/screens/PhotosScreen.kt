package com.example.webantapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.webantapp.R
import com.example.webantapp.model.Photo
import com.example.webantapp.model.Urls
import com.example.webantapp.navigation.NavigationDestination
import com.example.webantapp.ui.theme.black
import com.example.webantapp.ui.theme.darkGrey
import com.example.webantapp.ui.theme.grey
import com.example.webantapp.ui.theme.white

object PhotosScreenDestination : NavigationDestination {
    override val route = "photos"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosScreen() {
    Scaffold(
        containerColor = darkGrey,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(vertical = 80.dp, horizontal = 15.dp),
                containerColor = black,
                contentColor = white,
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Add, contentDescription = stringResource(
                            R.string.add_photo
                        )
                    )
                },
                onClick = { /*TODO*/ })
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = white
                ), title = {
                    Text(text = stringResource(R.string.all_photos), fontSize = 24.sp)
                })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    paddingValues
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkGrey)
                    .size(height = 1.dp, width = 1.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(Photo().urls?.regular)
                    .crossfade(true).build(),
                error = painterResource(id = R.drawable.error_ant),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.photo),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .background(color = white)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.select_photo),
                    modifier = Modifier.padding(12.dp),
                    fontSize = 20.sp
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 0.dp,
                        bottom = paddingValues.calculateTopPadding()
                    )
                ) {
                    val mockData =
                        List(100) { Photo(id = it.toString()) }
                    items(items = mockData, key = { photo -> photo.id }) { photo ->
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                                .clickable {
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
                }
            }
        }

    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PhotosScreenPreview() {
    PhotosScreen()
}