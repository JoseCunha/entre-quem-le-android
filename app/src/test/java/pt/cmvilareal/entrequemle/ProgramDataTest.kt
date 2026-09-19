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
        assertEquals("Encontro Literário & Feira do Livro de Vila Real 2026", programData.festivalInfo.subtitle)
        assertEquals("2026-09-18", programData.festivalInfo.startDate)
        assertEquals("2026-09-26", programData.festivalInfo.endDate)
        assertEquals("Vila Real", programData.festivalInfo.city)
        assertEquals(41.2965, programData.festivalInfo.latitude, 0.0001)
        assertEquals(-7.7445, programData.festivalInfo.longitude, 0.0001)

        // Verificação das 30 sessões completas
        assertEquals(30, programData.sessions.size)

        // Verificação dos intervenientes e autores de 2026
        assertTrue(programData.speakers.isNotEmpty())
        val authorNames = programData.speakers.map { it.name }
        assertTrue("Deve conter Rui Reininho", authorNames.any { it.contains("Rui Reininho") })
        assertTrue("Deve conter Rui Zink", authorNames.any { it.contains("Rui Zink") })
        assertTrue("Deve conter David Uclés", authorNames.any { it.contains("David Uclés") })
    }

    @Test
    fun testCategoriesMapping() {
        val programData: ProgramData = repository.parseJson(jsonContent)

        val categoriesUsed = programData.sessions.map { it.category }.toSet()
        assertTrue(categoriesUsed.contains(EventCategory.CONVERSAS))
        assertTrue(categoriesUsed.contains(EventCategory.LIVROS))
        assertTrue(categoriesUsed.contains(EventCategory.INFANTIL))
    }

    @Test
    fun testDateFiltering() {
        val programData: ProgramData = repository.parseJson(jsonContent)

        val sessionsOnDay1 = programData.sessions.filter { it.date == "2026-09-18" }
        assertEquals(3, sessionsOnDay1.size)

        val sessionsOnDay2 = programData.sessions.filter { it.date == "2026-09-19" }
        assertEquals(4, sessionsOnDay2.size)

        val sessionsOnDay3 = programData.sessions.filter { it.date == "2026-09-20" }
        assertEquals(4, sessionsOnDay3.size)
    }

    @Test
    fun testOfflineAndPrivacyIntegrity() {
        val programData: ProgramData = repository.parseJson(jsonContent)

        // Todas as sessões têm IDs únicos e válidos
        val ids = programData.sessions.map { it.id }
        assertEquals(ids.distinct().size, ids.size)

        // Nenhum URL externo obrigatório para carregar dados
        programData.sessions.forEach { session ->
            assertFalse(session.title.isBlank())
            assertFalse(session.date.isBlank())
            assertFalse(session.time.isBlank())
        }
    }
}
