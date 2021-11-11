package com.optisolu.vpm.seals

import com.optisolu.vpm.models.Data

sealed class VideosRequestResult {
    data class Success(val dataList: List<Data>): VideosRequestResult()
    data class Failure(val errorMsg: String): VideosRequestResult()
}