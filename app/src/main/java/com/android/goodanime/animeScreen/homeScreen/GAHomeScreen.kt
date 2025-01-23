package com.android.goodanime.animeScreen.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.goodanime.main.GAMainDestinations
import com.android.goodanime.ui.components.GAListView
import com.android.goodanime.ui.components.GALoadingIndicator

@Composable
fun GAHomeScreen(
    viewModel: GAHomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAnime()
    }

    if(uiState.isLoading) GALoadingIndicator()

    GAHomeScreenUi(
        uiState = uiState,
        onClickAnime = { animeId -> navController.navigate(GAMainDestinations.DetailsScreen.route.plus("/${animeId}")) },
        onFetchNextPage = viewModel::getAnimeNextPage
    )
}

@Composable
private fun GAHomeScreenUi(
    listState: LazyListState = rememberLazyListState(),
    uiState: GAHomeScreenUiState,
    onClickAnime: (Int) -> Unit,
    onFetchNextPage: () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            GAListView(
                listState = listState,
                listContent = {
                    GAAnimeCard(
                        title = this.title,
                        episodes = this.numberOfEpisodes,
                        score = this.score,
                        posterUrl = this.imageUrl,
                        animeId = this.animeId,
                        rating = this.rating,
                        onClick = onClickAnime
                    )
                },
                onFetchNextPage = onFetchNextPage,
                list = uiState.anime,
                isLoading = uiState.isLoading,
            )
        }
    }
}