package pt.cmvilareal.entrequemle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.cmvilareal.entrequemle.data.ProgramData
import pt.cmvilareal.entrequemle.data.ProgramRepository
import pt.cmvilareal.entrequemle.model.FestivalInfo
import pt.cmvilareal.entrequemle.model.Screen
import pt.cmvilareal.entrequemle.model.Session
import pt.cmvilareal.entrequemle.ui.screens.*
import pt.cmvilareal.entrequemle.ui.theme.EntreQuemLeTheme

class MainActivity : ComponentActivity() {

    private val repository = ProgramRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val programData: ProgramData = try {
            repository.loadFromAssets(this)
        } catch (_: Exception) {
            ProgramData(
                festivalInfo = FestivalInfo(),
                sessions = emptyList(),
                speakers = emptyList()
            )
        }

        setContent {
            EntreQuemLeTheme {
                MainApp(programData)
            }
        }
    }
}

@Composable
fun MainApp(programData: ProgramData) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    var bookmarkedIds by remember { mutableStateOf(setOf<String>()) }
    var selectedSessionForDetail by remember { mutableStateOf<Session?>(null) }

    val onBookmarkToggle: (Session) -> Unit = { session ->
        bookmarkedIds = if (bookmarkedIds.contains(session.id)) {
            bookmarkedIds - session.id
        } else {
            bookmarkedIds + session.id
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentScreen) {
            Screen.Home -> {
                HomeScreen(
                    sessions = programData.sessions,
                    onSessionClick = { selectedSessionForDetail = it },
                    onBookmarkToggle = onBookmarkToggle,
                    bookmarkedIds = bookmarkedIds,
                    currentScreen = Screen.Home,
                    onNavigate = { currentScreen = it }
                )
            }
            Screen.Schedule -> {
                ScheduleScreen(
                    sessions = programData.sessions,
                    onSessionClick = { selectedSessionForDetail = it },
                    onBookmarkToggle = onBookmarkToggle,
                    bookmarkedIds = bookmarkedIds,
                    currentScreen = Screen.Schedule,
                    onNavigate = { currentScreen = it }
                )
            }
            Screen.Authors -> {
                AuthorsScreen(
                    speakers = programData.speakers,
                    currentScreen = Screen.Authors,
                    onNavigate = { currentScreen = it }
                )
            }
            Screen.Bookmarks -> {
                BookmarksScreen(
                    sessions = programData.sessions,
                    bookmarkedIds = bookmarkedIds,
                    onSessionClick = { selectedSessionForDetail = it },
                    onBookmarkToggle = onBookmarkToggle,
                    currentScreen = Screen.Bookmarks,
                    onNavigate = { currentScreen = it }
                )
            }
            Screen.Info -> {
                InfoScreen(
                    festivalInfo = programData.festivalInfo,
                    onBack = { currentScreen = Screen.Home }
                )
            }
        }

        // Dialog de detalhes da sessão
        selectedSessionForDetail?.let { session ->
            AlertDialog(
                onDismissRequest = { selectedSessionForDetail = null },
                title = {
                    Text(
                        text = session.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "${session.date} às ${session.time} • ${session.category.label}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Local: ${session.venue}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                        if (session.requiresRegistration) {
                            Text(
                                text = "Requer inscrição prévia: entrequemle@cm-vilareal.pt",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        session.targetAudience?.let { audience ->
                            if (audience != "Geral") {
                                Text(
                                    text = "Público: $audience",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        session.moderator?.let {
                            Text(
                                text = "Moderação: $it",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        if (session.description.isNotBlank()) {
                            Text(
                                text = session.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { selectedSessionForDetail = null }) {
                        Text("Fechar")
                    }
                },
                dismissButton = {
                    val isBookmarked = bookmarkedIds.contains(session.id)
                    TextButton(onClick = {
                        onBookmarkToggle(session)
                    }) {
                        Text(if (isBookmarked) "Remover da Agenda" else "Guardar na Agenda")
                    }
                }
            )
        }
    }
}
