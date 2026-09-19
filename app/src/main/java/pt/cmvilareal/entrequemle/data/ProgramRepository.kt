package pt.cmvilareal.entrequemle.data

import android.content.Context
import kotlinx.serialization.json.Json
import pt.cmvilareal.entrequemle.model.FestivalInfo
import pt.cmvilareal.entrequemle.model.Session
import pt.cmvilareal.entrequemle.model.Speaker

data class ProgramData(
    val festivalInfo: FestivalInfo,
    val sessions: List<Session>,
    val speakers: List<Speaker>
)

class ProgramRepository(private val json: Json = Json { ignoreUnknownKeys = true }) {

    fun parseJson(jsonString: String): ProgramData {
        val dto = json.decodeFromString<ProgramDataDto>(jsonString)
        val sessions = dto.sessions.map { it.toDomain() }
        val speakersMap = mutableMapOf<String, Speaker>()
        sessions.forEach { session ->
            session.speakers.forEach { speaker ->
                if (!speakersMap.containsKey(speaker.name)) {
                    speakersMap[speaker.name] = speaker
                }
            }
        }
        val sortedSpeakers = speakersMap.values.sortedBy { it.name }
        return ProgramData(
            festivalInfo = dto.event.toFestivalInfo(),
            sessions = sessions,
            speakers = sortedSpeakers
        )
    }

    fun loadFromAssets(context: Context, fileName: String = "program_data.json"): ProgramData {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return parseJson(jsonString)
    }
}
