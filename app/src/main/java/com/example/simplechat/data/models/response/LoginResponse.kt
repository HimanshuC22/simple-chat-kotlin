package com.example.simplechat.data.models.response

import com.example.simplechat.data.models.User
import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse(
    @JsonProperty("valid") val valid: Boolean,
    @JsonProperty("user") val user: User,
    @JsonProperty("accessToken") val accessToken: String,
    @JsonProperty("refreshToken") val refreshToken: String,
)
