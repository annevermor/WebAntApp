package com.example.webantapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

object PhotoDescScreenDestination : NavigationDestination {
    override val route = "Photos"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PhotoDesc(photo: Photo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
        // verticalArrangement = Arrangement.SpaceBetween,
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(shape = RectangleShape, colors = CardDefaults.cardColors(containerColor = grey)) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo.urls?.regular)
                    .crossfade(true).build(),
                error = painterResource(id = R.drawable.error_ant),
                contentScale = ContentScale.Crop,
                contentDescription = "Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = "Image Name",
                fontSize = 36.sp,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = photo.user.name, color = darkGrey, fontSize = 20.sp)
                Text(text = convertDate(photo.date), color = darkGrey, fontSize = 20.sp)
            }
            Text(
                text = photo.description,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun convertDate(isoDate: String): String {
    val format = SimpleDateFormat("dd.MM.yyyy")
    try {
        val date = Date.from(Instant.parse(isoDate))
        return format.format(date)
    } catch (e: Exception) {
        throw IllegalArgumentException("Некорректный формат даты")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PhotoScreenPreview() {
    PhotoDesc(photo = Photo())
}
