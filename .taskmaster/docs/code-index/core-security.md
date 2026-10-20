# core/security

Source root: `app/src/main/java/app/codeg/android/`

## core/security/KeystoreSecretStore.kt

| Line | Declaration |
|---|---|
| 24 | `class KeystoreSecretStore @Inject constructor( @ApplicationContext context: Context, ) : SecretStore` |
| 28 |   `private val prefs` |
| 30 |   `override fun put(key: String, value: String)` |
| 39 |   `override fun get(key: String): String?` |
| 51 |   `override fun remove(key: String)` |
| 55 |   `private fun secretKey(): SecretKey` |
| 73 |   `private companion object` |

## core/security/SecretStore.kt

| Line | Declaration |
|---|---|
| 7 | `interface SecretStore` |
| 8 |   `fun put(key: String, value: String)` |
| 9 |   `fun get(key: String): String?` |
| 10 |   `fun remove(key: String)` |
