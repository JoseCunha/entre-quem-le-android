package pt.cmvilareal.entrequemle.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pt.cmvilareal.entrequemle.model.EventCategory
import pt.cmvilareal.entrequemle.model.FestivalInfo
import pt.cmvilareal.entrequemle.model.Session
import pt.cmvilareal.entrequemle.model.Speaker

@Serializable
data class ProgramDataDto(
    val event: EventDto,
    val categories: List<CategoryDto> = emptyList(),
    val sessions: List<SessionDto> = emptyList()
)

@Serializable
data class EventDto(
    val title: String,
    val subtitle: String,
    val dates: DatesDto? = null,
    val location: LocationDto? = null,
    val organizer: String = "",
    val partners: List<String> = emptyList()
) {
    fun toFestivalInfo(): FestivalInfo = FestivalInfo(
        title = title,
        subtitle = subtitle,
        startDate = dates?.start ?: "2026-09-18",
        endDate = dates?.end ?: "2026-09-26",
        venueName = location?.name ?: "Claustros do Palácio do Conde de Amarante (antigo Governo Civil)",
        city = location?.city ?: "Vila Real",
        latitude = location?.coordinates?.latitude ?: 41.2965,
        longitude = location?.coordinates?.longitude ?: -7.7445,
        organizer = organizer,
        partners = partners
    )
}

@Serializable
data class DatesDto(
    val start: String,
    val end: String
)

@Serializable
data class LocationDto(
    val name: String,
    val city: String,
    val country: String? = null,
    val coordinates: CoordinatesDto? = null
)

@Serializable
data class CoordinatesDto(
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    val icon: String? = null,
    val color: String? = null
)

@Serializable
data class SpeakerDto(
    val name: String,
    val role: String,
    val bio: String = ""
) {
    fun toDomain(): Speaker = Speaker(
        name = name,
        role = role,
        bio = bio
    )
}

@Serializable
data class SessionDto(
    val id: String,
    val date: String,
    val time: String,
    val title: String,
    @SerialName("category_id") val categoryId: String,
    val promoter: String = "",
    @SerialName("target_audience") val targetAudience: String = "Geral",
    val description: String = "",
    val moderator: String? = null,
    val curator: String? = null,
    val speakers: List<SpeakerDto> = emptyList()
) {
    fun toDomain(isBookmarked: Boolean = false): Session = Session(
        id = id,
        date = date,
        time = time,
        title = title,
        category = EventCategory.fromId(categoryId),
        promoter = promoter,
        targetAudience = targetAudience,
        description = description,
        moderator = moderator,
        curator = curator,
        speakers = speakers.map { it.toDomain() },
        isBookmarked = isBookmarked
    )
}
