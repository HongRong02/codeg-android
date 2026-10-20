# feature/main

Source root: `app/src/main/java/app/codeg/android/`

## feature/main/MainShell.kt

| Line | Declaration |
|---|---|
| 81 | `enum class HomeTab( val route: String, @StringRes val label: Int, val icon: ImageVector, val selectedIcon: ImageVector, )` |
| 82 |   `val route: String` |
| 84 |   `val icon: ImageVector` |
| 85 |   `val selectedIcon: ImageVector` |
| 94 | `private const val ROUTE_SERVERS` |
| 95 | `private const val ROUTE_EDITOR` |
| 96 | `private const val ROUTE_NEW_TASK` |
| 97 | `private const val ROUTE_AUTOMATIONS` |
| 104 | `val LocalBarsVisible` |
| 113 | `@Composable fun MainShell(appViewModel: AppViewModel, servers: List<ServerProfile>)` |
| 114 |   `val nav` |
| 115 |   `val backStackEntry by nav.currentBackStackEntryAsState()` |
| 116 |   `val currentRoute` |
| 117 |   `val showBottomBar` |
| 118 |   `val tabRoutes` |
| 119 |   `val selectedProfile by appViewModel.selectedProfile.collectAsStateWithLifecycle()` |
| 121 |   `val wide` |
| 128 |   `var barsVisible by remember` |
| 129 |   `val density` |
| 133 |   `val barProgress by animateFloatAsState( targetValue = if (barsVisible) 0f else 1f, animationSpec = tween(durationMillis = 280), label = "bottomBarCollapse", )` |
| 144 |   `val barConnection` |
| 146 |     `object : NestedScrollConnection` |
| 169 |   `val onTab: (HomeTab) -> Unit` |
| 178 |   `val pendingLink by app.codeg.android.core.common.DeepLinkBus.pending.collectAsStateWithLifecycle()` |
| 181 |     `fun toTab(it: HomeTab)` |
| 367 | `@Composable private fun CodegNavRail(currentRoute: String?, onSelect: (HomeTab) -> Unit)` |
| 368 |   `val colors` |
| 406 | `@Composable private fun CodegBottomBar( currentRoute: String?, onSelect: (HomeTab) -> Unit, modifier: Modifier = Modifier, )` |
| 411 |   `val colors` |
