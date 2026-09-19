package pt.cmvilareal.entrequemle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pt.cmvilareal.entrequemle.model.Screen
import pt.cmvilareal.entrequemle.model.Session
import pt.cmvilareal.entrequemle.ui.components.SessionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    sessions: List<Session>,
    bookmarkedIds: Set<String>,
    onSessionClick: (Session) -> Unit,
    onBookmarkToggle: (Session) -> Unit,
    currentScreen: Screen = Screen.Bookmarks,
    onNavigate: (Screen) -> Unit = {}
) {
    val bookmarkedSessions = sessions.filter { bookmarkedIds.contains(it.id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "A Minha Agenda",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D2E50),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0D2E50),
                contentColor = Color.White
            ) {
                val navItems = listOf(
                    Triple(Screen.Home, "Hoje", Icons.Default.Today),
                    Triple(Screen.Schedule, "Programa", Icons.Default.Event),
                    Triple(Screen.Authors, "Autores", Icons.Default.People),
                    Triple(Screen.Bookmarks, "Agenda", Icons.Default.Bookmark),
                    Triple(Screen.Info, "Info", Icons.Default.Info)
                )

                navItems.forEach { (screen, label, icon) ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick = { onNavigate(screen) },
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF0D2E50),
                            selectedTextColor = Color(0xFFE4EF00),
                            indicatorColor = Color(0xFFE4EF00),
                            unselectedIconColor = Color.White.copy(alpha = 0.7f),
                            unselectedTextColor = Color.White.copy(alpha = 0.7f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        if (bookmarkedSessions.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Ainda não guardou nenhuma sessão.",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toque no ícone de marcador nas sessões do programa para criar a sua agenda personalizada da feira.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bookmarkedSessions, key = { it.id }) { session ->
                    SessionCard(
                        session = session,
                        isBookmarked = true,
                        onBookmarkToggle = onBookmarkToggle,
                        onClick = onSessionClick
                    )
                }
            }
        }
    }
}
