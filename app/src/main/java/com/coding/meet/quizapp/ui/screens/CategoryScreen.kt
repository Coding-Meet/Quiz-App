package com.coding.meet.quizapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coding.meet.quizapp.data.api.TriviaApiClient

data class CategoryItem(
    val name: String,
    val id: Int
)

@Composable
fun CategoryScreen(
    onCategorySelected: (Int) -> Unit
) {
    val categories = remember {
        listOf(
            CategoryItem("General Knowledge", TriviaApiClient.CATEGORY_GENERAL_KNOWLEDGE),
            CategoryItem("Science", TriviaApiClient.CATEGORY_SCIENCE),
            CategoryItem("Computers", TriviaApiClient.CATEGORY_COMPUTERS),
            CategoryItem("Mathematics", TriviaApiClient.CATEGORY_MATHEMATICS),
            CategoryItem("Mythology", TriviaApiClient.CATEGORY_MYTHOLOGY),
            CategoryItem("Sports", TriviaApiClient.CATEGORY_SPORTS),
            CategoryItem("Geography", TriviaApiClient.CATEGORY_GEOGRAPHY),
            CategoryItem("History", TriviaApiClient.CATEGORY_HISTORY),
            CategoryItem("Politics", TriviaApiClient.CATEGORY_POLITICS),
            CategoryItem("Art", TriviaApiClient.CATEGORY_ART)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Select Category",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategorySelected(category.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    category: CategoryItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
} 