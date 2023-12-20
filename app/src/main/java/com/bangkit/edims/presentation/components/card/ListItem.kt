package com.bangkit.edims.presentation.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.edims.R
import com.bangkit.edims.core.utils.DateConverter
import com.bangkit.edims.presentation.components.shimmer.AnimatedShimmer
import com.bangkit.edims.presentation.theme.Shapes

@Composable
fun ListItem(
    name: String,
    image: String,
    dueDateMillis: Long,
    modifier: Modifier = Modifier
) {
    val remainingTime = DateConverter.remainingDays(dueDateMillis)
    val remainingHours = DateConverter.remainingHours(dueDateMillis)

    val labelRemaining by remember {
        mutableStateOf(
            when {
                remainingTime < 0 -> "Expired"
                remainingHours < 0 -> "Expired"
                remainingTime < 1 -> {
                    "$remainingHours hours"
                }
                remainingTime < 30 -> "$remainingTime days"
                remainingTime < 90 -> ">1 Month"
                else -> ">3 Months"
            }
        )
    }

    var showShimmer by remember {
        mutableStateOf(true)
    }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = Shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(60.dp)
                    .width(80.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(Shapes.medium)
                        .padding(5.dp)
                        .background(AnimatedShimmer(showShimmer = showShimmer)),
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    onSuccess = {
                        showShimmer = false
                    },
                    onError = {
                        showShimmer = false
                    },
                    error = painterResource(R.drawable.ic_placeholder),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = name,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = labelRemaining,
                maxLines = 2,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(12.dp)
            )
        }
    }
}