package pt.cmvilareal.entrequemle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.cmvilareal.entrequemle.model.Screen
import pt.cmvilareal.entrequemle.model.Speaker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsScreen(
    speakers: List<Speaker>,
    currentScreen: Screen = Screen.Authors,
    onNavigate: (Screen) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Autores & Convidados",
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(speakers, key = { it.name }) { speaker ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = speaker.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = speaker.role,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (speaker.bio.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = speaker.bio,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
