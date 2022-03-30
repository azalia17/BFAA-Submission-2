package com.azalia.bfaa_submission_2.model

import com.squareup.moshi.Json

data class SearchResponse(
    @field:Json(name = "items")
    val items: List<User>
)
