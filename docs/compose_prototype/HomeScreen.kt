package pt.cmvilareal.entrequemle.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.cmvilareal.entrequemle.model.EventCategory
import pt.cmvilareal.entrequemle.model.Screen
import pt.cmvilareal.entrequemle.model.Session
import pt.cmvilareal.entrequemle.ui.components.SessionCard

/**
 * Ecrã Principal da Aplicação "Entre Quem Lê" - Feira do Livro de Vila Real.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    sessions: List<Session>,
    onSessionClick: (Session) -> Unit,
    onBookmarkToggle: (Session) -> Unit,
    bookmarkedIds: Set<String>,
    currentScreen: Screen = Screen.Home,
    onNavigate: (Screen) -> Unit = {}
) {
    val festivalDays = listOf(
        "2026-09-18" to "Sex, 18",
        "2026-09-19" to "Sáb, 19",
        "2026-09-20" to "Dom, 20",
        "2026-09-21" to "Seg, 21",
        "2026-09-22" to "Ter, 22",
        "2026-09-23" to "Qua, 23",
        "2026-09-24" to "Qui, 24",
        "2026-09-25" to "Sex, 25",
        "2026-09-26" to "Sáb, 26"
    )

    var selectedDate by remember { mutableStateOf("2026-09-19") } // Dia corrente / destaque inicial
    var selectedCategory by remember { mutableStateOf<String?>("todos") }

    val filteredSessions = remember(sessions, selectedDate, selectedCategory) {
        sessions.filter { session ->
            val matchesDate = session.date == selectedDate
            val matchesCategory = selectedCategory == null || selectedCategory == "todos" || session.category.id == selectedCategory
            matchesDate && matchesCategory
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Entre Quem Lê",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Feira do Livro de Vila Real",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    IconButton(onClick = { onNavigate(Screen.Info) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Informações sobre a Feira do Livro e Localização"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
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
            // Seletor Horizontal de Dias da Feira (9 dias)
            ScrollableTabRow(
                selectedTabIndex = festivalDays.indexOfFirst { it.first == selectedDate }.coerceAtLeast(0),
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                divider = {}
            ) {
                festivalDays.forEach { (dateKey, label) ->
                    Tab(
                        selected = selectedDate == dateKey,
                        onClick = { selectedDate = dateKey },
                        text = {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = if (selectedDate == dateKey) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Chips de Filtro por Categoria Temática
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == "todos",
                    onClick = { selectedCategory = "todos" },
                    label = { Text("Todos") }
                )
                EventCategory.entries.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat.id,
                        onClick = { selectedCategory = cat.id },
                        label = { Text(cat.label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Lista de Sessões
            if (filteredSessions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sem sessões agendadas para os filtros selecionados.",
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
