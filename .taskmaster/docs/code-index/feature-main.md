# feature/main

Source root: `app/src/main/java/app/codeg/android/`

## feature/main/MainShell.kt

| Line | Declaration |
|---|---|
| 86 | `enum class HomeTab( val route: String, @StringRes val label: Int, val icon: ImageVector, val selectedIcon: ImageVector, )` |
| 87 |   `val route: String` |
| 89 |   `val icon: ImageVector` |
| 90 |   `val selectedIcon: ImageVector` |
| 99 | `private const val ROUTE_SERVERS` |
| 100 | `private const val ROUTE_EDITOR` |
| 101 | `private const val ROUTE_NEW_TASK` |
| 102 | `private const val ROUTE_AUTOMATIONS` |
| 109 | `val LocalBarsVisible` |
| 118 | `@Composable fun MainShell(appViewModel: AppViewModel, servers: List<ServerProfile>)` |
| 119 |   `val nav` |
| 120 |   `val backStackEntry by nav.currentBackStackEntryAsState()` |
| 121 |   `val currentRoute` |
| 122 |   `val showBottomBar` |
| 123 |   `val tabRoutes` |
| 124 |   `val selectedProfile by appViewModel.selectedProfile.collectAsStateWithLifecycle()` |
| 126 |   `val wide` |
| 133 |   `var barsVisible by remember` |
| 134 |   `val density` |
| 138 |   `val barProgress by animateFloatAsState( targetValue = if (barsVisible) 0f else 1f, animationSpec = tween(durationMillis = 280), label = "bottomBarCollapse", )` |
| 149 |   `val barConnection` |
| 151 |     `object : NestedScrollConnection` |
| 174 |   `val onTab: (HomeTab) -> Unit` |
| 183 |   `val pendingLink by app.codeg.android.core.common.DeepLinkBus.pending.collectAsStateWithLifecycle()` |
| 186 |     `fun toTab(it: HomeTab)` |
| 378 | `@Composable private fun CodegNavRail(currentRoute: String?, onSelect: (HomeTab) -> Unit)` |
| 393 | `@Composable private fun CodegBottomBar(currentRoute: String?, onSelect: (HomeTab) -> Unit, modifier: Modifier = Modifier)` |
