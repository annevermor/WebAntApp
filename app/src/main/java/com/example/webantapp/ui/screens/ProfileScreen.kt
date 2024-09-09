package com.example.webantapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.webantapp.navigation.NavigationDestination
import com.example.webantapp.ui.theme.darkGrey
import com.example.webantapp.ui.theme.grey
import com.example.webantapp.ui.theme.purple
import com.example.webantapp.ui.theme.white

object ProfileScreenDestination : NavigationDestination {
    override val route = "profile"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(containerColor = white,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = white
                ),
                title = {
                Text(text = stringResource(R.string.profile), fontSize = 24.sp)
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
            Row(modifier = Modifier.fillMaxWidth().padding(30.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = stringResource(R.string.photo),
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(100.dp))
                )
                Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                    Text(text = "User Name", fontSize = 24.sp)
                    Text(text = "20.01.2000", color = darkGrey)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.93f)
                    .background(purple)
                    .size(height = 1.dp, width = 1.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}