# core/datastore

Source root: `app/src/main/java/app/codeg/android/`

## core/datastore/AppSettingsStore.kt

| Line | Declaration |
|---|---|
| 13 | `enum class ThemeMode(val id: String)` |
| 18 |   `companion object` |
| 19 |     `fun from(id: String?): ThemeMode` |
| 24 | `data class AppSettings( val themeMode: ThemeMode = ThemeMode.SYSTEM, val accentId: String = DEFAULT_ACCENT, )` |
| 25 |   `val themeMode: ThemeMode` |
| 26 |   `val accentId: String` |
| 28 |   `companion object` |
| 38 | `class AppSettingsStore @Inject constructor( private val dataStore: DataStore<Preferences>, )` |
| 39 |   `private val dataStore: DataStore<Preferences>` |
| 41 |   `val settings: Flow<AppSettings>` |
| 48 |   `suspend fun setThemeMode(mode: ThemeMode)` |
| 52 |   `suspend fun setAccent(accentId: String)` |
| 56 |   `private companion object` |

## core/datastore/RecentSearchesStore.kt

| Line | Declaration |
|---|---|
| 17 | `class RecentSearchesStore @Inject constructor( private val dataStore: DataStore<Preferences>, )` |
| 18 |   `private val dataStore: DataStore<Preferences>` |
| 20 |   `private val json` |
| 22 |   `val searches: Flow<List<String>>` |
| 24 |   `suspend fun add(term: String)` |
| 35 |   `suspend fun clear()` |
| 39 |   `private fun decode(raw: String?): List<String>` |
| 42 |   `private companion object` |

## core/datastore/SelectorPrefsStore.kt

| Line | Declaration |
|---|---|
| 19 | `data class SelectorPrefs( val modeId: String? = null, val configValues: Map<String, String>? = null, )` |
| 20 |   `val modeId: String?` |
| 21 |   `val configValues: Map<String, String>?` |
| 38 | `class SelectorPrefsStore @Inject constructor( private val dataStore: DataStore<Preferences>, )` |
| 39 |   `private val dataStore: DataStore<Preferences>` |
| 41 |   `private val json` |
| 44 |   `suspend fun prefs(agent: AgentType): SelectorPrefs` |
| 47 |   `suspend fun saveMode(agent: AgentType, modeId: String)` |
| 51 |   `suspend fun saveConfig(agent: AgentType, configId: String, valueId: String)` |
| 54 |   `private suspend fun all(): Map<String, SelectorPrefs>` |
| 56 |   `private suspend fun update(agent: AgentType, mutate: (SelectorPrefs) -> SelectorPrefs)` |
| 64 |   `private fun decode(raw: String?): Map<String, SelectorPrefs>` |
| 67 |   `private companion object` |

## core/datastore/ServerProfile.kt

| Line | Declaration |
|---|---|
| 11 | `data class ServerProfile( val id: String, val name: String, val baseUrl: String, )` |
| 12 |   `val id: String` |
| 13 |   `val name: String` |
| 14 |   `val baseUrl: String` |

## core/datastore/ServerStore.kt

| Line | Declaration |
|---|---|
| 21 | `class ServerStore @Inject constructor( private val dataStore: DataStore<Preferences>, )` |
| 22 |   `private val dataStore: DataStore<Preferences>` |
| 24 |   `private val json` |
| 26 |   `val profiles: Flow<List<ServerProfile>>` |
| 29 |   `val selectedId: Flow<String?>` |
| 33 |   `suspend fun upsert(profile: ServerProfile)` |
| 44 |   `suspend fun delete(id: String)` |
| 55 |   `suspend fun select(id: String?)` |
| 61 |   `private fun decode(raw: String?): List<ServerProfile>` |
| 65 |   `private companion object` |
