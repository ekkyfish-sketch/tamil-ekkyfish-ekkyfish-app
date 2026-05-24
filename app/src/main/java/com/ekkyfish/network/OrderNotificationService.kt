package com.ekkyfish.network

import com.ekkyfish.BuildConfig
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import timber.log.Timber

data class OrderNotificationRequest(
    @SerializedName("owner_phone") val ownerPhone: String,
    @SerializedName("owner_message") val ownerMessage: String,
    @SerializedName("customer_phone") val customerPhone: String,
    @SerializedName("customer_message") val customerMessage: String,
    @SerializedName("source") val source: String = "ekkyfish-android",
    @SerializedName("app_version") val appVersion: String = BuildConfig.VERSION_NAME
)

data class OrderNotificationResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String? = null
)

interface OrderNotificationApi {
    @POST("api/orders/notify")
    suspend fun sendOrderNotification(@Body request: OrderNotificationRequest): Response<OrderNotificationResponse>
}

object OrderNotificationClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.NOTIFICATION_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: OrderNotificationApi = retrofit.create(OrderNotificationApi::class.java)
}

suspend fun sendOrderNotifications(request: OrderNotificationRequest): Boolean {
    return try {
        val response = OrderNotificationClient.api.sendOrderNotification(request)
        val bodySuccess = response.body()?.success == true
        if (!response.isSuccessful || !bodySuccess) {
            Timber.w("Order notification backend rejected request: code=${response.code()}, body=${response.body()?.message}")
        }
        bodySuccess
    } catch (t: Throwable) {
        Timber.e(t, "Failed to send order notifications to backend")
        false
    }
}
