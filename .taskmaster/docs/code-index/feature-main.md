# feature/main

Source root: `app/src/main/java/app/codeg/android/`

## feature/main/MainShell.kt

| Line | Declaration |
|---|---|
| 76 | `enum class HomeTab( val route: String, @StringRes val label: Int, val icon: ImageVector, val selectedIcon: ImageVector, )` |
| 77 |   `val route: String` |
| 79 |   `val icon: ImageVector` |
| 80 |   `val selectedIcon: ImageVector` |
| 89 | `private const val ROUTE_SERVERS` |
| 90 | `private const val ROUTE_EDITOR` |
| 91 | `private const val ROUTE_NEW_TASK` |
| 92 | `private const val ROUTE_AUTOMATIONS` |
| 99 | `val LocalBarsVisible` |
| 108 | `@Composable fun MainShell(appViewModel: AppViewModel, servers: List<ServerProfile>)` |
| 109 |   `val nav` |
| 110 |   `val backStackEntry by nav.currentBackStackEntryAsState()` |
| 111 |   `val currentRoute` |
| 112 |   `val showBottomBar` |
| 113 |   `val tabRoutes` |
| 114 |   `val selectedProfile by appViewModel.selectedProfile.collectAsStateWithLifecycle()` |
| 116 |   `val wide` |
| 123 |   `var barsVisible by remember` |
| 124 |   `val density` |
| 128 |   `val barProgress by animateFloatAsState( targetValue = if (barsVisible) 0f else 1f, animationSpec = tween(durationMillis = 280), label = "bottomBarCollapse", )` |
| 139 |   `val barConnection` |
| 141 |     `object : NestedScrollConnection` |
| 164 |   `val onTab: (HomeTab) -> Unit` |
| 173 |   `val pendingLink by app.codeg.android.core.common.DeepLinkBus.pending.collectAsStateWithLifecycle()` |
| 176 |     `fun toTab(it: HomeTab)` |
| 198 |   `var bottomBarHeight by remember` |
| 199 |   `val toastClearance` |
| 383 | `@Composable private fun CodegNavRail(currentRoute: String?, onSelect: (HomeTab) -> Unit)` |
| 398 | `@Composable private fun CodegBottomBar(currentRoute: String?, onSelect: (HomeTab) -> Unit, modifier: Modifier = Modifier)` |
