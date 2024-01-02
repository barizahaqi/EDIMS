package com.bangkit.edims.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.edims.R
import com.bangkit.edims.core.utils.ProductFilterType
import com.bangkit.edims.data.User
import com.bangkit.edims.database.Product
import com.bangkit.edims.presentation.components.card.CardItem
import com.bangkit.edims.presentation.components.card.ExpandableCard
import com.bangkit.edims.presentation.theme.Beige
import com.bangkit.edims.presentation.theme.PaleLeaf

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    homeViewModel: HomeViewModel,
) {
    val badProducts by homeViewModel.getFilteredProducts(ProductFilterType.BAD_PRODUCTS)
        .collectAsState(
            initial = emptyList()
        )
    val almostProducts by homeViewModel.getFilteredProducts(ProductFilterType.ALMOST_PRODUCTS)
        .collectAsState(
            initial = emptyList()
        )
    val goodProducts by homeViewModel.getFilteredProducts(ProductFilterType.GOOD_PRODUCTS)
        .collectAsState(
            initial = emptyList()
        )
    val freshProducts by homeViewModel.getFilteredProducts(ProductFilterType.FRESH_PRODUCTS)
        .collectAsState(
            initial = emptyList()
        )
    val userData = User(null, null, null, null)

    HomeContent(
        modifier = modifier,
        userData = userData,
        badProducts = badProducts,
        almostProducts = almostProducts,
        goodProducts = goodProducts,
        freshProducts = freshProducts,
        navigateToDetail = navigateToDetail,
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    userData: User,
    badProducts: List<Product>,
    almostProducts: List<Product>,
    goodProducts: List<Product>,
    freshProducts: List<Product>,
    navigateToDetail: (Int) -> Unit,
) {
    val imageProfile = userData.imageProfile
    val profileUsername = userData.username

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PaleLeaf)
    ) {
        ProfileBar(
            profileIcon = imageProfile,
            profileUsername = profileUsername,
        )
        CardContent(
            modifier = Modifier
                .fillMaxHeight(),
            badProducts = badProducts,
            almostProducts = almostProducts,
            goodProducts = goodProducts,
            freshProducts = freshProducts,
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
private fun ProfileBar(
    modifier: Modifier = Modifier,
    profileIcon: String?,
    profileUsername: String?,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(PaleLeaf)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profileIcon)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile Image",
                    placeholder = painterResource(R.drawable.ic_user),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit,
                    error = painterResource(R.drawable.ic_user),
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
            ) {
                Text(
                    text = "Hello",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = profileUsername ?: "Guest",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun CardContent(
    modifier : Modifier = Modifier,
    badProducts: List<Product>,
    almostProducts: List<Product>,
    goodProducts: List<Product>,
    freshProducts: List<Product>,
    navigateToDetail: (Int) -> Unit,
) {
    val cardItems = listOf(
        CardItem(
            id = 1,
            title = "Gone Bad",
            icon = R.drawable.ic_status_bad,
            color = Color.Red,
            listItem = badProducts
        ),
        CardItem(
            id = 2,
            title = "Expire Soon",
            icon = R.drawable.ic_status_almost,
            color = Color.Yellow,
            listItem = almostProducts
        ),
        CardItem(
            id = 3,
            title = "Good",
            icon = R.drawable.ic_status_good,
            color = Color.Cyan,
            listItem = goodProducts
        ),
        CardItem(
            id = 4,
            title = "Fresh",
            icon = R.drawable.ic_status_fresh,
            color = Color.Green,
            listItem = freshProducts
        )
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            .background(Beige),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(16.dp)
        )
        LazyColumn {
            items(cardItems){item ->
                ExpandableCard(
                    listItem = item.listItem,
                    icon = painterResource(item.icon),
                    title = item.title,
                    color = item.color,
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }
}