package tn.superhich.covid19watcher.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("Code")
    val code: String,
    @SerializedName("Name")
    val name: String
)