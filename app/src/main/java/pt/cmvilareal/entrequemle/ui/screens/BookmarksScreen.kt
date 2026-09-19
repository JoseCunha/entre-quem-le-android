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
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = currentScreen == Screen.Home,
                    onClick = { onNavigate(Screen.Home) },
                    icon = { Icon(Icons.Default.Today, contentDescription = null) },
                    label = { Text("Hoje") }
                )
                NavigationBarItem(
                    selected = currentScreen == Screen.Schedule,
                    onClick = { onNavigate(Screen.Schedule) },
                    icon = { Icon(Icons.Default.Event, contentDescription = null) },
                    label = { Text("Programa") }
                )
                NavigationBarItem(
                    selected = currentScreen == Screen.Authors,
                    onClick = { onNavigate(Screen.Authors) },
                    icon = { Icon(Icons.Default.People, contentDescription = null) },
                    label = { Text("Autores") }
                )
                NavigationBarItem(
                    selected = currentScreen == Screen.Bookmarks,
                    onClick = { onNavigate(Screen.Bookmarks) },
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = null) },
                    label = { Text("Agenda") }
                )
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
