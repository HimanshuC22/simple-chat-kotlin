package com.example.simplechat.data.models.response

import com.fasterxml.jackson.annotation.JsonProperty

class BaseResponse<T>(
    @JsonProperty("success") val success: Boolean?,
    @JsonProperty("message") val message: String?,
    @JsonProperty("data") val data: T?,
) {

}