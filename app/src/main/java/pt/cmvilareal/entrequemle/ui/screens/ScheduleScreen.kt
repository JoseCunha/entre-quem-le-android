package pt.cmvilareal.entrequemle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.cmvilareal.entrequemle.model.Screen
import pt.cmvilareal.entrequemle.model.Session
import pt.cmvilareal.entrequemle.ui.components.SessionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    sessions: List<Session>,
    onSessionClick: (Session) -> Unit,
    onBookmarkToggle: (Session) -> Unit,
    bookmarkedIds: Set<String>,
    currentScreen: Screen = Screen.Schedule,
    onNavigate: (Screen) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredSessions = remember(sessions, searchQuery) {
        if (searchQuery.isBlank()) {
            sessions
        } else {
            sessions.filter { session ->
                session.title.contains(searchQuery, ignoreCase = true) ||
                    session.description.contains(searchQuery, ignoreCase = true) ||
                    session.promoter.contains(searchQuery, ignoreCase = true) ||
                    session.moderator?.contains(searchQuery, ignoreCase = true) == true ||
                    session.speakers.any { it.name.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Programa Completo",
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Pesquisar por livro, autor ou tema...") },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                singleLine = true
            )

            if (filteredSessions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhuma sessão encontrada para a pesquisa.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredSessions, key = { it.id }) { session ->
                        SessionCard(
                            session = session,
                            isBookmarked = bookmarkedIds.contains(session.id),
                            onBookmarkToggle = onBookmarkToggle,
                            onClick = onSessionClick
                        )
                    }
                }
            }
        }
    }
}
