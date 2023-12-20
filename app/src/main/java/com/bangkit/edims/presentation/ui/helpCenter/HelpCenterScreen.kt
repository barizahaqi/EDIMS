package com.bangkit.edims.presentation.ui.helpCenter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bangkit.edims.presentation.components.faq.ExpandableQuestion
import com.bangkit.edims.presentation.components.faq.FaqData
import com.bangkit.edims.presentation.theme.PaleLeaf
import com.bangkit.edims.presentation.theme.Tacao

@Composable
fun HelpCenterScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    HelpCenterContent(
        modifier, navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpCenterContent(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    val faqList = FaqData.faqList

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Help Center",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable {
                                navigateBack()
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PaleLeaf)
            )
        }
    ) {
        Box {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Frequently Asked Questions",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(faqList) { item ->
                        ExpandableQuestion(
                            question = item.question,
                            answer = item.answer
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = Tacao,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(
                    text = "Contact Us",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}