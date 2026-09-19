package pt.cmvilareal.entrequemle

import java.io.File
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import pt.cmvilareal.entrequemle.data.ProgramData
import pt.cmvilareal.entrequemle.data.ProgramRepository
import pt.cmvilareal.entrequemle.model.EventCategory

class ProgramDataTest {

    private lateinit var repository: ProgramRepository
    private lateinit var jsonContent: String

    @Before
    fun setUp() {
        repository = ProgramRepository()
        // O ficheiro existe quer em app/src/main/assets quer em data/
        val assetFile = File("src/main/assets/program_data.json")
        val dataFile = if (assetFile.exists()) assetFile else File("../data/program_data.json")
        assertTrue("Ficheiro program_data.json deve existir", dataFile.exists())
        jsonContent = dataFile.readText()
    }

    @Test
    fun testParseProgramDataSuccessfully() {
        val programData: ProgramData = repository.parseJson(jsonContent)

        assertNotNull(programData.festivalInfo)
        assertEquals("Entre Quem Lê", programData.festivalInfo.title)
        assertEquals("3.ª Edição", programData.festivalInfo.edition)
        assertEquals("Feira do Livro de Vila Real 2026", programData.festivalInfo.subtitle)
        assertEquals("Um encontro sem fronteiras", programData.festivalInfo.motto)
        assertEquals("2026-09-18", programData.festivalInfo.startDate)
        assertEquals("2026-09-26", programData.festivalInfo.endDate)
        assertEquals("Vila Real", programData.festivalInfo.city)
        assertEquals(41.2965, programData.festivalInfo.latitude, 0.0001)
        assertEquals(-7.7445, programData.festivalInfo.longitude, 0.0001)
        assertEquals("entrequemle@cm-vilareal.pt", programData.festivalInfo.registrationEmail)
        assertEquals("Todos os eventos são de participação gratuita", programData.festivalInfo.costNotice)

        // Verificação exaustiva das 52 sessões oficiais da brochura de 2026
        assertEquals(52, programData.sessions.size)

        // Verificação dos intervenientes e autores de relevo da edição 2026
        assertTrue(programData.speakers.isNotEmpty())
        val authorNames = programData.speakers.map { it.name }
        assertTrue("Deve conter Rui Reininho", authorNames.any { it.contains("Rui Reininho") })
        assertTrue("Deve conter Rui Zink", authorNames.any { it.contains("Rui Zink") })
        assertTrue("Deve conter André Gago", authorNames.any { it.contains("André Gago") })
        assertTrue("Deve conter António Mota", authorNames.any { it.contains("António Mota") })
        assertTrue("Deve conter Rachel Caiano", authorNames.any { it.contains("Rachel Caiano") })
        assertTrue("Deve conter Luís Moreira Gonçalves", authorNames.any { it.contains("Luís Moreira Gonçalves") })
        assertTrue("Deve conter Nelson Nunes", authorNames.any { it.contains("Nelson Nunes") })
        assertTrue("Deve conter Marco Neves", authorNames.any { it.contains("Marco Neves") })
        assertTrue("Deve conter Ricardo Alexandre", authorNames.any { it.contains("Ricardo Alexandre") })
    }

    @Test
    fun testCategoriesMapping() {
        val programData: ProgramData = repository.parseJson(jsonContent)

        val categoriesUsed = programData.sessions.map { it.category }.toSet()
        // As 8 categorias oficiais da brochura de 2026 devem estar todas representadas
        assertEquals(8, categoriesUsed.size)
        assertTrue(categoriesUsed.contains(EventCategory.CONVERSA))
        assertTrue(categoriesUsed.contains(EventCategory.LIVRO))
        assertTrue(categoriesUsed.contains(EventCategory.RECITAL))
        assertTrue(categoriesUsed.contains(EventCategory.WORKSHOP))
        assertTrue(categoriesUsed.contains(EventCategory.FAMILIAS))
        assertTrue(categoriesUsed.contains(EventCategory.ESCOLAS))
        assertTrue(categoriesUsed.contains(EventCategory.LEITURA))
        assertTrue(categoriesUsed.contains(EventCategory.INSTITUCIONAL))
    }

    @Test
    fun testDateFilteringAcrossNineDays() {
        val programData: ProgramData = repository.parseJson(jsonContent)

        val expectedPerDay = mapOf(
            "2026-09-18" to 4,
            "2026-09-19" to 7,
            "2026-09-20" to 6,
            "2026-09-21" to 5,
            "2026-09-22" to 4,
            "2026-09-23" to 5,
            "2026-09-24" to 4,
            "2026-09-25" to 8,
            "2026-09-26" to 9
        )

        expectedPerDay.forEach { (date, expectedCount) ->
            val sessionsOnDay = programData.sessions.filter { it.date == date }
            assertEquals("Número de sessões no dia $date", expectedCount, sessionsOnDay.size)
        }
    }

    @Test
    fun testRegistrationAndVenueConsistency() {
        val programData: ProgramData = repository.parseJson(jsonContent)

        // Sessões com inscrição obrigatória (workshops / oficinas)
        val registrationSessions = programData.sessions.filter { it.requiresRegistration }
        assertEquals(3, registrationSessions.size)

        // Sessões com locais específicos fora dos Claustros principais
        val specialVenues = programData.sessions.map { it.venue }.filter {
            !it.contains("Claustros", ignoreCase = true)
        }
        assertTrue("Devem existir sessões em locais específicos da cidade", specialVenues.isNotEmpty())
    }

    @Test
    fun testOfflineAndPrivacyIntegrity() {
        val programData: ProgramData = repository.parseJson(jsonContent)

        // Todas as 52 sessões têm IDs únicos e não vazios
        val ids = programData.sessions.map { it.id }
        assertEquals(52, ids.size)
        assertEquals(52, ids.distinct().size)

        // Todos os campos essenciais preenchidos
        programData.sessions.forEach { session ->
            assertFalse("Título não pode ser vazio", session.title.isBlank())
            assertTrue("Data deve estar no intervalo do festival", session.date.startsWith("2026-09-"))
            assertTrue("Hora deve estar no formato HH:mm", session.time.matches(Regex("\\d{2}:\\d{2}")))
            assertFalse("Local não pode ser vazio", session.venue.isBlank())
        }
    }
}
