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
import androidx.compose.ui.graphics.Color
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
                    session.venue.contains(searchQuery, ignoreCase = true) ||
                    session.category.label.contains(searchQuery, ignoreCase = true) ||
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
                placeholder = { Text("Pesquisar por livro, autor, local ou tema...") },
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
