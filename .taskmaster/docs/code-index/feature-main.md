# feature/main

Source root: `app/src/main/java/app/codeg/android/`

## feature/main/MainShell.kt

| Line | Declaration |
|---|---|
| 80 | `enum class HomeTab( val route: String, @StringRes val label: Int, val icon: ImageVector, val selectedIcon: ImageVector, )` |
| 81 |   `val route: String` |
| 83 |   `val icon: ImageVector` |
| 84 |   `val selectedIcon: ImageVector` |
| 93 | `private const val ROUTE_SERVERS` |
| 94 | `private const val ROUTE_EDITOR` |
| 95 | `private const val ROUTE_NEW_TASK` |
| 102 | `val LocalBarsVisible` |
| 111 | `@Composable fun MainShell(appViewModel: AppViewModel, servers: List<ServerProfile>)` |
| 112 |   `val nav` |
| 113 |   `val backStackEntry by nav.currentBackStackEntryAsState()` |
| 114 |   `val currentRoute` |
| 115 |   `val showBottomBar` |
| 116 |   `val tabRoutes` |
| 117 |   `val selectedProfile by appViewModel.selectedProfile.collectAsStateWithLifecycle()` |
| 119 |   `val wide` |
| 126 |   `var barsVisible by remember` |
| 127 |   `val density` |
| 131 |   `val barProgress by animateFloatAsState( targetValue = if (barsVisible) 0f else 1f, animationSpec = tween(durationMillis = 280), label = "bottomBarCollapse", )` |
| 142 |   `val barConnection` |
| 144 |     `object : NestedScrollConnection` |
| 167 |   `val onTab: (HomeTab) -> Unit` |
| 176 |   `val pendingLink by app.codeg.android.core.common.DeepLinkBus.pending.collectAsStateWithLifecycle()` |
| 179 |     `fun toTab(it: HomeTab)` |
| 348 | `@Composable private fun CodegNavRail(currentRoute: String?, onSelect: (HomeTab) -> Unit)` |
| 349 |   `val colors` |
| 387 | `@Composable private fun CodegBottomBar( currentRoute: String?, onSelect: (HomeTab) -> Unit, modifier: Modifier = Modifier, )` |
| 392 |   `val colors` |
