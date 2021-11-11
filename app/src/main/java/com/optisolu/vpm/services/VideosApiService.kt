package com.optisolu.vpm.services

import android.util.Log
import com.optisolu.vpm.models.VideoModel
import com.optisolu.vpm.seals.VideosRequestResult
import com.optisolu.vpm.utils.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class VideosApiService {
    companion object {
        private const val TAG = "VideosApiService"
    }

    private lateinit var client: HttpClient

    operator fun invoke(): VideosApiService {
        if (!this::client.isInitialized) {
            client = HttpClient(Android) {
                install(JsonFeature) {
                    serializer = GsonSerializer()
                }
            }
        }

        return this
    }

    private fun isClientInitialised(): Boolean {
        return this::client.isInitialized
    }

    internal suspend fun getVideosData(pageNumber: Int): VideosRequestResult {
        return client.getVideosData(pageNumber)
    }

    private suspend fun HttpClient.getVideosData(pageNumber: Int): VideosRequestResult {
        val errorResponseMsg = "Unable to fetch videos"
        return try {
            Log.i(TAG, "getVideosData: ${isClientInitialised()} $pageNumber")
            if (isClientInitialised()) {
                val response: HttpResponse =
                    get(Constants.VIDEOS_API_BASE_URL + "users?page=$pageNumber") {
                        contentType(ContentType.Application.Json)
                    }

                Log.i(TAG, "getVideosData: ${response.status}")

                return if (response.status == HttpStatusCode.OK) {
                    val videoModelList: VideoModel = response.receive()
                    VideosRequestResult.Success(videoModelList.data)
                } else {
                    Log.i(TAG, "requestPostLocation: Failure :: ${response.receive<String>()}")
                    VideosRequestResult.Failure(errorResponseMsg)
                }
            } else {
                VideosRequestResult.Failure(errorResponseMsg)
            }
        } catch (e: ClientRequestException) {

            Log.i(TAG, "requestPostLocation: ${e.response}")

            return VideosRequestResult.Failure(errorResponseMsg)
        }
        catch (e: Exception) {
            Log.i(TAG, "getVideosData: ${e.localizedMessage}")

            return VideosRequestResult.Failure(errorResponseMsg)
        }
    }
}