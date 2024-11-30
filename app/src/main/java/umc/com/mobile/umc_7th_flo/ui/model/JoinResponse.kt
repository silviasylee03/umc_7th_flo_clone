package umc.com.mobile.umc_7th_flo.ui.model

import com.google.gson.annotations.SerializedName

data class JoinResponse(
    @SerializedName(value = "isSuccess")
    val isSuccess: Boolean,

    @SerializedName(value = "code")
    val code: String,

    @SerializedName(value = "message")
    val message: String,

    @SerializedName(value = "result")
    val result: JoinResult
)
data class JoinResult(
    @SerializedName(value = "memberId")
    val memberId: Int,

    @SerializedName(value = "createdAt")
    val createdAt: String,

    @SerializedName(value = "updatedAt")
    val updatedAt: String
)
