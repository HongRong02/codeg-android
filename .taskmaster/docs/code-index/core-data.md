# core/data

Source root: `app/src/main/java/app/codeg/android/`

## core/data/ServerRepository.kt

| Line | Declaration |
|---|---|
| 26 | `class ServerRepository @Inject constructor( private val serverStore: ServerStore, private val secretStore: SecretStore, private val factory: CodegClientFactory, private val dispatchers: DispatcherProvider, )` |
| 27 |   `private val serverStore: ServerStore` |
| 28 |   `private val secretStore: SecretStore` |
| 29 |   `private val factory: CodegClientFactory` |
| 30 |   `private val dispatchers: DispatcherProvider` |
| 32 |   `val profiles: Flow<List<ServerProfile>>` |
| 33 |   `val selectedId: Flow<String?>` |
| 38 |   `private val _conversationsChanged` |
| 39 |   `val conversationsChanged: SharedFlow<Unit>` |
| 41 |   `fun notifyConversationsChanged()` |
| 45 |   `private val _foldersChanged` |
| 46 |   `val foldersChanged: SharedFlow<Unit>` |
| 48 |   `fun notifyFoldersChanged()` |
| 50 |   `val selectedProfile: Flow<ServerProfile?>` |
| 54 |   `suspend fun token(id: String): String?` |
| 58 |   `suspend fun save(profile: ServerProfile, token: String)` |
| 63 |   `suspend fun delete(id: String)` |
| 68 |   `suspend fun select(id: String?)` |
| 71 |   `suspend fun client(profile: ServerProfile): CodegClient?` |
| 77 |   `suspend fun eventStream(profile: ServerProfile): EventStream?` |
| 83 |   `fun transientClient(baseUrl: String, token: String): CodegClient` |
