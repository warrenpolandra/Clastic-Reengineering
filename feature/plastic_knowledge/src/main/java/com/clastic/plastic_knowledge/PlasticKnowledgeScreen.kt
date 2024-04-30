package com.clastic.plastic_knowledge

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.PlasticKnowledge
import com.clastic.plastic_knowledge.component.ProductCard
import com.clastic.ui.BannerWithGradient
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.RecycleTag
import com.clastic.ui.theme.ClasticTheme

@Composable
fun PlasticKnowledgeScreen(
    plasticId: String,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: PlasticKnowledgeViewModel = hiltViewModel<PlasticKnowledgeViewModel>()
    val plasticKnowledge by viewModel.plasticKnowledge.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchPlasticKnowledgeById(
            plasticId = plasticId,
            onFetchFailed = { showToast(context, it) }
        )
    }

    PlasticKnowledgeScreenContent(
        isLoading = isLoading,
        plasticKnowledge = plasticKnowledge,
        navigateToHome = navigateToHome,
        modifier = modifier
    )
}

@Composable
private fun PlasticKnowledgeScreenContent(
    isLoading: Boolean,
    navigateToHome: () -> Unit,
    plasticKnowledge: PlasticKnowledge,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.plastic_knowledge),
                onBackPressed = navigateToHome
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    BannerWithGradient(imageUrl = plasticKnowledge.coverUrl)
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = plasticKnowledge.name,
                            style = MaterialTheme.typography.h4.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            textAlign = TextAlign.Start
                        )
                    }
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        RecycleTag(tag = listOf(plasticKnowledge.tag))
                    }
                }
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Box(modifier = Modifier.padding(18.dp)) {
                        PlasticDescription(plasticKnowledge.description)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .background(Color.LightGray)
                    )
                    Box(modifier = Modifier.padding(18.dp)) {
                        Column {
                            Text(
                                text = stringResource(R.string.product_sample),
                                style = MaterialTheme.typography.h5.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            LazyRow(state = rememberLazyListState()) {
                                items(
                                    items = plasticKnowledge.productList,
                                    key = { it.name }
                                ) { product -> ProductCard(product) }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp))
                }
            }
        }
    }
}

@Composable
private fun PlasticDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
        Text(
            text = description,
            style = MaterialTheme.typography.subtitle1.copy(
                lineHeight = 25.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
private fun PlasticKnowledgeScreenPreview() {
    ClasticTheme {
        PlasticKnowledgeScreenContent(
            isLoading = true,
            plasticKnowledge = PlasticKnowledge(
                tag = "HDPE",
                name = "HYDRO DPE",
                description = "Description",
                colorHex = "FFFFFF",
                coverUrl = "HTTP",
                logoUrl = "HTTP",
                productList = emptyList()
            ),
            navigateToHome = {}
        )
    }
}