# core/common

Source root: `app/src/main/java/app/codeg/android/`

## core/common/AppLocale.kt

| Line | Declaration |
|---|---|
| 14 | `enum class AppLanguage(val storageKey: String, val title: String, val locale: Locale?)` |
| 19 |   `companion object` |
| 20 |     `fun fromKey(key: String?): AppLanguage` |
| 29 | `object LocaleManager` |
| 30 |   `private const val PREFS` |
| 31 |   `private const val KEY` |
| 33 |   `fun current(context: Context): AppLanguage` |
| 38 |   `fun set(context: Context, language: AppLanguage)` |
| 44 |   `fun wrap(base: Context): Context` |

## core/common/Base64Url.kt

| Line | Declaration |
|---|---|
| 14 | `object Base64Url` |
| 15 |   `private val encoder` |
| 17 |   `fun encode(value: String): String` |

## core/common/DeepLink.kt

| Line | Declaration |
|---|---|
| 13 | `sealed interface DeepLinkRoute` |
| 14 |   `data class OpenTab(val tab: String) : DeepLinkRoute` |
| 15 |   `data class OpenConversation(val id: Int) : DeepLinkRoute` |
| 16 |   `data class OpenProject(val id: Int) : DeepLinkRoute` |
| 17 |   `data class OpenSettings(val slug: String) : DeepLinkRoute` |
| 19 |   `companion object` |
| 20 |     `fun parse(uri: Uri?): DeepLinkRoute?` |
| 42 | `object DeepLinkBus` |
| 43 |   `private val _pending` |
| 44 |   `val pending: StateFlow<DeepLinkRoute?>` |
| 46 |   `fun dispatch(uri: Uri?)` |
| 51 |   `fun consume(route: DeepLinkRoute)` |

## core/common/DispatcherProvider.kt

| Line | Declaration |
|---|---|
| 11 | `interface DispatcherProvider` |
| 12 |   `val io: CoroutineDispatcher` |
| 13 |   `val default: CoroutineDispatcher` |
| 14 |   `val main: CoroutineDispatcher` |
| 17 | `object DefaultDispatcherProvider : DispatcherProvider` |
| 18 |   `override val io: CoroutineDispatcher get()` |
| 19 |   `override val default: CoroutineDispatcher get()` |
| 20 |   `override val main: CoroutineDispatcher get()` |

## core/common/RelativeTime.kt

| Line | Declaration |
|---|---|
| 12 | `object RelativeTime` |
| 13 |   `private val monthDay: DateTimeFormatter` |
| 20 |   `fun compact(instant: Instant, now: Instant = Instant.now()): String` |
