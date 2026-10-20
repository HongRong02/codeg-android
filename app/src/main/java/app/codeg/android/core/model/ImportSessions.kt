package app.codeg.android.core.model

import kotlinx.serialization.Serializable
import app.codeg.android.core.model.wire.ImportSessionKey

@Serializable
data class ImportScanSession(
    val externalId: String,
    val agentType: String,
    val title: String? = null,
    val startedAt: String,
    val status: String,
) {
    val key: ImportSessionKey get() = ImportSessionKey(agentType, externalId)
}

@Serializable
data class ImportScanFolder(val path: String, val sessions: List<ImportScanSession>)

@Serializable
data class ImportScanResult(val folders: List<ImportScanFolder>, val noFolderCount: Int = 0)

@Serializable
data class ImportSessionsResult(
    val imported: Int,
    val updated: Int = 0,
    val skipped: Int = 0,
    val notFound: Int = 0,
    val failed: Int = 0,
    val errors: List<String> = emptyList(),
)

fun List<ImportScanSession>.importableKeys(): List<ImportSessionKey> =
    filter { it.status == "new" }.map { it.key }.distinct()
