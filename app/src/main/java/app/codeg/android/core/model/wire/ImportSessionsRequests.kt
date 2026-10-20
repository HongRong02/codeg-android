package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class ImportSessionKey(val agentType: String, val externalId: String)

@Serializable
data class ImportSelectedSessionsBody(val selections: List<ImportSessionKey>)
