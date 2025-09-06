package com.riccardo.imagebrowser.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.riccardo.imagebrowser.R
import com.riccardo.imagebrowser.data.model.Photo
import com.riccardo.imagebrowser.data.model.ProfileImage
import com.riccardo.imagebrowser.data.model.Urls
import com.riccardo.imagebrowser.data.model.User
import com.riccardo.imagebrowser.presentation.LocalNavController
import com.riccardo.imagebrowser.presentation.utils.DetailsScreen
import com.riccardo.imagebrowser.presentation.utils.debugPlaceholder

@Composable
fun ImageItem(photo: Photo) {
    val navController =
    if (LocalInspectionMode.current) {
        rememberNavController()
    } else {
        LocalNavController.current
    }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {

            AsyncImage(
                model = photo.urls.small,
                contentDescription = photo.altDescription,
                placeholder = debugPlaceholder(R.drawable.placeholder),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        Log.d("ImageItem", "ImageItem: ${photo.urls.small}")
                        navController.navigate(DetailsScreen(photo.id))
                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = photo.user.profileImage.medium,
                    contentDescription = photo.user.name,
                    placeholder = debugPlaceholder(R.drawable.placeholder),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = photo.user.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    photo.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }


}


@Preview
@Composable
fun ImageItemPreview() {
    val samplePhoto = Photo(
        id = "SRbhuvsSeR0",
        width = 3000,
        height = 1768,
        description = "Sample description",
        altDescription = "A sign that says need to get the word out",

        urls = Urls(
            raw = "",
            full = "",
            regular = "",
            small = "",
            thumb = "",
            smallS3 = ""
        ),
        user = User(
            id = "-myGpytHnPo",
            username = "jontyson",
            name = "Jon Tyson",
            firstName = "Jon",
            lastName = "Tyson",
            profileImage = ProfileImage(
                medium = "",
            ),
            portfolioUrl = "",
            bio = "",
            location = ""
        )
    )

    ImageItem(photo = samplePhoto)
}