package app.codeg.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StashEntry(
    @SerialName("ref_name") val refName: String,
    val message: String = "",
    val date: String = "",
)
