package com.android.goodanime.animeScreen.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goodanime.common.api.GARetrofit
import com.android.goodanime.common.api.response.GAAnimeDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The view model for the home screen
 */
@HiltViewModel
class GAHomeViewModel  @Inject constructor(
    private val retrofit: GARetrofit
) : ViewModel() {
    private val TAG = GAHomeViewModel::class.java.simpleName
    val uiState: MutableStateFlow<GAHomeScreenUiState> =
        MutableStateFlow(GAHomeScreenUiState())
    private var pageNumber = 1
    private var isLastPage = false

    /**
     * Get the top anime from the API
     */
    fun getAnime() {
        uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = retrofit.apiService.getTopAnime(page = 1)
            if (result.isSuccessful) {
                val anime = result.body()
                isLastPage = anime?.pagination?.hasNextPage == false
                uiState.update { it.copy(anime = getTopAnimeData(anime?.data), isLoading = false) }
            } else {
                uiState.update { it.copy(error = "Something went wrong, please try again", isLoading = false) }
            }
        }
    }

    /**
     * Fetch the next page of the top anime
     */
    fun getAnimeNextPage() {
        if (isLastPage) return
        uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = retrofit.apiService.getTopAnime(page = pageNumber + 1)
            if (result.isSuccessful) {
                val anime = result.body()
                isLastPage = anime?.pagination?.hasNextPage == false
                uiState.update { it.copy(anime = uiState.value.anime + getTopAnimeData(anime?.data), isLoading = false) }
                pageNumber++
            } else {
                uiState.update { it.copy(error = "Something went wrong, please try again", isLoading = false) }
            }
        }
    }

    /**
     * Get the top anime data
     */
    private fun getTopAnimeData(anime: List<GAAnimeDetails>?): List<GATopAnimeData> {
        return anime?.map {
            GATopAnimeData(
                title = it.title,
                imageUrl = it.images.jpg.imageUrl,
                score = it.score,
                rank = it.rank,
                numberOfEpisodes = it.episodes,
                rating = it.rating,
                animeId = it.malId
            )
        } ?: emptyList()
    }
}

/**
 * The UI state for the home screen
 */
data class GAHomeScreenUiState(
    val isLoading: Boolean = false,
    val anime: List<GATopAnimeData> = emptyList(),
    val error: String? = null,
)

/**
 * The data class for the top anime data
 */
data class GATopAnimeData(
    val title: String,
    val imageUrl: String,
    val score: Double,
    val rank: Int,
    val numberOfEpisodes: Int,
    val rating: String,
    val animeId: Int
)