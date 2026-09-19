package pt.cmvilareal.entrequemle.model

/**
 * Categorias temáticas das sessões da Feira do Livro "Entre Quem Lê".
 */
enum class EventCategory(val id: String, val label: String, val colorHex: Long) {
    CONVERSAS("conversas", "Conversas & Debates", 0xFF8C3827),
    LIVROS("livros", "Apresentação de Livros", 0xFFD9822B),
    INFANTIL("infantil", "Infantil & Oficinas", 0xFF4A6746),
    POESIA("poesia", "Poesia & Recitais", 0xFF633B48),
    MUSICA("musica", "Música & Espetáculos", 0xFF205FA6);

    companion object {
        fun fromId(id: String): EventCategory =
            entries.find { it.id == id } ?: CONVERSAS
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
 * Modelo de Sessão de Evento.
 */
data class Session(
    val id: String,
    val date: String, // YYYY-MM-DD
    val time: String, // HH:mm
    val title: String,
    val category: EventCategory,
    val promoter: String,
    val targetAudience: String,
    val description: String,
    val moderator: String? = null,
    val curator: String? = null,
    val speakers: List<Speaker> = emptyList(),
    val isBookmarked: Boolean = false
) {
    val displayTime: String get() = time
    val hasChildrenAudience: Boolean get() = category == EventCategory.INFANTIL
}

/**
 * Informações do Evento e Recinto.
 */
data class FestivalInfo(
    val title: String = "Entre Quem Lê",
    val subtitle: String = "Encontro Literário & Feira do Livro de Vila Real 2026",
    val startDate: String = "2026-09-18",
    val endDate: String = "2026-09-26",
    val venueName: String = "Claustros do Palácio do Conde de Amarante (antigo Governo Civil)",
    val city: String = "Vila Real",
    val latitude: Double = 41.2965,
    val longitude: Double = -7.7445,
    val organizer: String = "Câmara Municipal de Vila Real",
    val partners: List<String> = listOf("Teatro de Vila Real", "Projeto Nortear", "Granada Cidade Criativa UNESCO")
)

/**
 * Ecrãs de Navegação Principal da Aplicação Android.
 */
sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Hoje")
    data object Schedule : Screen("schedule", "Programa")
    data object Authors : Screen("authors", "Autores")
    data object Bookmarks : Screen("bookmarks", "Agenda")
    data object Info : Screen("info", "Info")
}
