package pt.cmvilareal.entrequemle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.cmvilareal.entrequemle.model.EventCategory
import pt.cmvilareal.entrequemle.model.Screen
import pt.cmvilareal.entrequemle.model.Session
import pt.cmvilareal.entrequemle.ui.components.SessionCard

/**
 * Ecrã Principal da Aplicação "Entre Quem Lê" - 3.ª Edição (18 a 26 Setembro 2026).
 * Look & Feel oficial com cabeçalho em Azul Noturno e destaques em Amarelo Limão.
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

    var selectedDate by remember { mutableStateOf("2026-09-19") } // Destaque: Rui Zink, Luís Moreira Gonçalves, etc.
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "ENTRE QUEM LÊ",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFE4EF00), // Amarelo Limão vibrante da capa
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = MaterialTheme.shapes.extraSmall,
                                color = Color(0xFF1D5FA7)
                            ) {
                                Text(
                                    text = "3.ª EDIÇÃO",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Feira do Livro de Vila Real • Claustros do Palácio",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D2E50), // Azul Noturno Oficial
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { onNavigate(Screen.Info) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Informações sobre a Feira e Palácio do Conde de Amarante"
                        )
                    }
                }
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
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Faixa de Aviso de Participação Gratuita (Brochura p. 2)
            Surface(
                color = Color(0xFF1D5FA7),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "TODOS OS EVENTOS SÃO DE PARTICIPAÇÃO GRATUITA",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            // Seletor Horizontal de 9 Dias (18 a 26 de Setembro de 2026)
            ScrollableTabRow(
                selectedTabIndex = festivalDays.indexOfFirst { it.first == selectedDate }.coerceAtLeast(0),
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
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
                                fontWeight = if (selectedDate == dateKey) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedDate == dateKey) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Barra de Chips de Categorias Oficiais da Brochura
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
                    label = { Text("Todos (${sessions.count { it.date == selectedDate }})") }
                )

                EventCategory.entries.forEach { cat ->
                    val count = sessions.count { it.date == selectedDate && it.category == cat }
                    if (count > 0 || selectedCategory == cat.id) {
                        FilterChip(
                            selected = selectedCategory == cat.id,
                            onClick = { selectedCategory = cat.id },
                            label = { Text("${cat.label} ($count)") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Lista de Sessões Filtradas
            if (filteredSessions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sem atividades agendadas para os filtros selecionados.",
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
