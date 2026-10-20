# di

Source root: `app/src/main/java/app/codeg/android/`

## di/NetworkModule.kt

| Line | Declaration |
|---|---|
| 17 | `object NetworkModule` |
| 30 |   `fun provideHttpClient(): HttpClient` |

## di/SecurityModule.kt

| Line | Declaration |
|---|---|
| 13 | `abstract class SecurityModule` |
| 17 |   `abstract fun bindSecretStore(impl: KeystoreSecretStore): SecretStore` |

## di/StorageModule.kt

| Line | Declaration |
|---|---|
| 19 | `object StorageModule` |
| 24 |   `fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences>` |
| 31 |   `fun provideDispatchers(): DispatcherProvider` |
