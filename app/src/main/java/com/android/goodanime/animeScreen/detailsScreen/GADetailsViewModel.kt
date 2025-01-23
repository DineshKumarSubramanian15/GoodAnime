package com.android.goodanime.animeScreen.detailsScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.goodanime.animeScreen.homeScreen.GAHomeViewModel
import com.android.goodanime.animeScreen.homeScreen.GATopAnimeData
import com.android.goodanime.common.GALog
import com.android.goodanime.common.api.GARetrofit
import com.android.goodanime.common.api.response.GAAnimeDetails
import com.android.goodanime.common.api.response.GAGenres
import com.android.goodanime.common.api.response.GAImages
import com.android.goodanime.main.GANavigationArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The view model for the details screen
 */
@HiltViewModel
class GADetailsViewModel @Inject constructor(
    private val retrofit: GARetrofit,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val TAG = GADetailsViewModel::class.java.simpleName
    val uiState: MutableStateFlow<GADetailsScreenUiState> =
        MutableStateFlow(GADetailsScreenUiState())

    init {
        getAnimeDetails(animeId = savedStateHandle.get<Int>(GANavigationArguments.AnimeId))
    }

    /**
     *
     */
    private fun getAnimeDetails(animeId: Int?) {
        if (animeId == null) {
            uiState.update { it.copy(error = "Invalid anime id") }
            return
        }
        uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = retrofit.apiService.getAnimeDetails(animeId = animeId)
            if (result.isSuccessful) {
                val anime = result.body()?.data
                uiState.update { it.copy(animeDetails = getTopAnimeData(anime), isLoading = false) }
            } else {
                uiState.update { it.copy(error = "Something went wrong, please try again", isLoading = false) }
            }
        }
    }

    /**
     * Get the top anime data
     */
    private fun getTopAnimeData(animeDetails: GAAnimeDetails?): GAAnimeDetailsData {
       return GAAnimeDetailsData(
           malId = animeDetails?.malId,
           url = animeDetails?.url,
           images = animeDetails?.images,
           approved = animeDetails?.approved,
           titleEnglish = animeDetails?.titleEnglish,
           title = animeDetails?.title,
           rating = animeDetails?.rating,
           score = animeDetails?.score,
           scoredBy = animeDetails?.scoredBy,
           rank = animeDetails?.rank,
           episodes = animeDetails?.episodes,
           trailerUrl = animeDetails?.trailer?.embedUrl,
           synopsis = animeDetails?.synopsis,
           genres = animeDetails?.genres,
           trailerImage = animeDetails?.trailer?.images?.imageUrl
       )
    }
}

/**
 * The UI state for the home screen
 */
data class GADetailsScreenUiState(
    val isLoading: Boolean = false,
    val animeDetails: GAAnimeDetailsData? = null,
    val error: String? = null,
)

/**
 * The data class for the top anime data
 */
data class GAAnimeDetailsData(
    val malId: Int?,
    val url: String?,
    val images: GAImages?,
    val approved: Boolean?,
    val titleEnglish: String?,
    val title: String?,
    val rating: String?,
    val score: Double?,
    val scoredBy: Int?,
    val rank: Int?,
    val episodes: Int?,
    val trailerUrl: String?,
    val synopsis: String?,
    val genres: List<GAGenres>?,
    val trailerImage: String?
)