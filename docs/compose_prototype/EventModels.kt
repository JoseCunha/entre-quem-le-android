package pt.cmvilareal.entrequemle.model

/**
 * Categorias oficiais de eventos da 3.ª Edição (conforme brochura oficial).
 */
enum class EventCategory(
    val id: String,
    val label: String,
    val colorHex: Long,
    val textHex: Long
) {
    CONVERSA("conversa", "Conversa", 0xFFF5BA13, 0xFF1A1A1A),
    LIVRO("livro", "Apresentação de livro", 0xFFE67E22, 0xFFFFFFFF),
    RECITAL("recital", "Recital", 0xFF263238, 0xFFFFFFFF),
    WORKSHOP("workshop", "Curso / Workshop", 0xFF4CAF50, 0xFFFFFFFF),
    FAMILIAS("familias", "Famílias", 0xFFD81B60, 0xFFFFFFFF),
    ESCOLAS("escolas", "Escolas", 0xFFFF6F00, 0xFFFFFFFF),
    LEITURA("leitura", "Leitura", 0xFF1E88E5, 0xFFFFFFFF),
    INSTITUCIONAL("institucional", "Institucional", 0xFFCFD8DC, 0xFF263238);

    companion object {
        fun fromId(id: String): EventCategory =
            entries.find { it.id.equals(id, ignoreCase = true) } ?: CONVERSA
    }
}

/**
 * Modelo de Interveniente / Autor / Moderador.
 */
data class Speaker(
    val name: String,
    val role: String,
    val bio: String = "",
    val avatarUrl: String? = null
)

/**
 * Modelo de Sessão de Evento Oficial 2026.
 */
data class Session(
    val id: String,
    val date: String, // YYYY-MM-DD
    val time: String, // HH:mm
    val title: String,
    val category: EventCategory,
    val venue: String = "Claustros do Palácio do Conde de Amarante",
    val requiresRegistration: Boolean = false,
    val targetAudience: String? = null,
    val description: String = "",
    val moderator: String? = null,
    val speakers: List<Speaker> = emptyList(),
    val isBookmarked: Boolean = false
) {
    val displayTime: String get() = time
    val isChildrenAudience: Boolean get() = category == EventCategory.FAMILIAS || category == EventCategory.ESCOLAS
}

/**
 * Metadados Oficiais do Evento e Recinto (Brochura 2026).
 */
data class FestivalInfo(
    val edition: String = "3.ª Edição",
    val title: String = "Entre Quem Lê",
    val subtitle: "Feira do Livro de Vila Real 2026",
    val motto: String = "Um encontro sem fronteiras",
    val startDate: String = "2026-09-18",
    val endDate: String = "2026-09-26",
    val venueName: String = "Claustros do Palácio do Conde de Amarante (antigo Governo Civil)",
    val address: String = "Avenida Carvalho Araújo / Rua Dr. Cândido Sotto Mayor",
    val city: String = "Vila Real",
    val latitude: Double = 41.2965,
    val longitude: Double = -7.7445,
    val registrationEmail: String = "entrequemle@cm-vilareal.pt",
    val costNotice: String = "Todos os eventos são de participação gratuita",
    val organizer: String = "Município de Vila Real",
    val partners: List<String> = listOf(
        "Livrarias da cidade (FNAC, Bertrand, Branco, Livros e Narrativas)",
        "Associação Cultura Adentro",
        "Teatro de Vila Real",
        "Projeto Nortear",
        "Granada Cidade Criativa da Literatura UNESCO"
    )
)

/**
 * Rotas e Destinos de Navegação Principal.
 */
sealed class Screen(val route: String, val title: String, val iconName: String) {
    data object Home : Screen("home", "Hoje", "Today")
    data object Schedule : Screen("schedule", "Programa", "Calendar")
    data object Authors : Screen("authors", "Autores", "People")
    data object Bookmarks : Screen("bookmarks", "Agenda", "Bookmark")
    data object Info : Screen("info", "Info", "Info")
}
