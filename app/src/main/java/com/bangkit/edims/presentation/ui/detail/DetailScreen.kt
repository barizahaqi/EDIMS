package com.bangkit.edims.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.edims.R
import com.bangkit.edims.core.utils.DateConverter
import com.bangkit.edims.data.Result
import com.bangkit.edims.database.Product
import com.bangkit.edims.presentation.components.loading.ShowLoading
import com.bangkit.edims.presentation.components.shimmer.AnimatedShimmer
import com.bangkit.edims.presentation.theme.Beige
import com.bangkit.edims.presentation.theme.PaleLeaf
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel,
    id: Int,
    showSnackbar: (String, SnackbarDuration) -> Unit,
    navigateBack: (Product?) -> Unit,
) {
    val result by detailViewModel.result.collectAsState()
    var buttonStatus by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.detail_title))
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable{
                                navigateBack(null)
                            }
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable(
                                enabled = buttonStatus
                            ) {
                                val product = (result as Result.Success<Product>).data
                                showSnackbar("Deleting...", SnackbarDuration.Short)
                                navigateBack(product)
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(PaleLeaf)
            )
        }
    ) { innerPadding ->
        when (result) {
            Result.Loading -> {
                buttonStatus = false
                ShowLoading(modifier.fillMaxSize())
                LaunchedEffect(key1 = Unit) {
                    delay(500)
                    detailViewModel.getProductById(id)
                }
            }

            is Result.Success -> {
                buttonStatus = true
                val item = (result as Result.Success<Product>).data
                val image = item.image
                val name = item.name
                val category = item.category
                val date = DateConverter.convertMillisToString(item.dueDateMillis)
                DetailContent(
                    modifier = modifier.padding(innerPadding),
                    image = image,
                    name = name,
                    date = date,
                    category = category,
                )
            }

            is Result.Error -> {
                buttonStatus = false
                val errorMessage = (result as Result.Error).errorMessage
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    image: String,
    name: String,
    date: String,
    category: String,
) {
    var showShimmer by remember {
        mutableStateOf(true)
    }
    var isDialogVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Beige)
            .scrollable(orientation = Orientation.Horizontal, state = rememberScrollState())
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "Product Image",
            modifier = Modifier
                .background(AnimatedShimmer(showShimmer = showShimmer))
                .height(200.dp)
                .fillMaxWidth()
                .clickable { isDialogVisible = true },
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_placeholder),
            onSuccess = {
                showShimmer = false
            },
            onError = {
                showShimmer = false
            },
            error = painterResource(R.drawable.ic_placeholder)
        )
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
            ProductInfo(
                icon = Icons.Default.Category,
                label = stringResource(id = R.string.detail_label_category),
                value = category,
                style = MaterialTheme.typography.bodyLarge
            )
            ProductInfo(
                icon = Icons.Default.DateRange,
                label = stringResource(id = R.string.detail_label_date_expired),
                value = date,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    if (isDialogVisible) {
        ShowFullImage(
            image = image,
            onDismiss = {
                isDialogVisible = false
            }
        )
    }
}

@Composable
private fun ShowFullImage(
    image: String,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
        content = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clickable {
                        onDismiss()
                    },
                error = painterResource(R.drawable.ic_placeholder)
            )
        }
    )
}

@Composable
private fun ProductInfo(icon: ImageVector, label: String, value: String, style: TextStyle) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
        )
        Column {
            Text(
                text = label,
                color = Color.Gray,
                style = style
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                color = Color.Black,
                style = style
            )
        }
    }
}
