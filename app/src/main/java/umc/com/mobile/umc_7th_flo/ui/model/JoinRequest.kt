package umc.com.mobile.umc_7th_flo.ui.model

import com.google.gson.annotations.SerializedName

data class JoinRequest(
    @SerializedName(value = "name")
    var name: Boolean,

    @SerializedName(value = "email")
    var email: String,

    @SerializedName(value = "password")
    var password: String
)
