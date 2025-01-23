package com.android.goodanime.common.api.response

import com.google.gson.annotations.SerializedName

/**
 * The response class for the top anime API.
 */
data class GATopAnimeResponse(
    @SerializedName("pagination")
    val pagination: GAPagination,

    @SerializedName("data")
    val data: List<GAAnimeDetails>,
)

/**
 * The pagination data class.
 */
data class GAPagination(
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("items")
    val items: GAPaginationItems,
)

/**
 * The pagination items data class.
 */
data class GAPaginationItems(
    @SerializedName("count")
    val data: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("per_page")
    val perPage: Int,
)

data class GAAnimeDetailsResponse(
    @SerializedName("data")
    val data: GAAnimeDetails,
)

/**
 * The top anime data class.
 */
data class GAAnimeDetails(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("images")
    val images: GAImages,
    @SerializedName("approved")
    val approved: Boolean,
    @SerializedName("title_english")
    val titleEnglish: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("scored_by")
    val scoredBy: Int,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("episodes")
    val episodes: Int,
    @SerializedName("trailer")
    val trailer: GATrailer? = null,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("genres")
    val genres: List<GAGenres>,
)

data class GAGenres(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String,
)

/**
 * The anime trailer data class.
 */
data class GATrailer(
    @SerializedName("youtube_id")
    val youtubeId: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    @SerializedName("images")
    val images: DAAnimeImage
)

/**
 * The anime images data class.
 */
data class GAImages(
    @SerializedName("jpg")
    val jpg: DAAnimeImage,
    @SerializedName("webp")
    val webp: DAAnimeImage,
)

/**
 * The anime image data class.
 */
data class DAAnimeImage(
    @SerializedName("image_url")
    val imageUrl: String,
)