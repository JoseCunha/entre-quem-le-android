package pt.cmvilareal.entrequemle.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.cmvilareal.entrequemle.model.Session

/**
 * Cartão de Sessão do Evento - Look & Feel Oficial da Brochura 2026.
 *
 * Características Visuais:
 * - Badges temáticas com as cores oficiais (Conversa em Ouro, Livro em Laranja, Recital em Carvão, etc.).
 * - Títulos fortes em Azul Noturno Oficial (#0D2E50).
 * - Subtítulos e oradores em Azul Cerúleo (#1D5FA7).
 * - Sinalização de "Requer inscrição" e locais especiais (Oficina das Artes / Vila Velha).
 * - Acessibilidade WCAG 2.2: Touch target 48x48dp, contraste elevado e semântica TalkBack.
 */
@Composable
fun SessionCard(
    session: Session,
    isBookmarked: Boolean,
    onBookmarkToggle: (Session) -> Unit,
    onClick: (Session) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick(session) },
                onClickLabel = "Ver detalhes de ${session.title}"
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Linha superior: Hora, Categoria da Brochura, Alertas e Favorito
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    // Badge de Horário (Azul Noturno com texto branco)
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.defaultMinSize(minWidth = 50.dp)
                    ) {
                        Text(
                            text = session.time,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    // Badge de Categoria Oficial (Cores fiéis à p. 2 da brochura)
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(session.category.colorHex)
                    ) {
                        Text(
                            text = session.category.label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(session.category.textHex),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    // Tag de Inscrição Obrigatória
                    if (session.requiresRegistration) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.errorContainer
                        ) {
                            Text(
                                text = "Requer inscrição",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                // Botão de Favorito acessível (min 48x48dp)
                val bookmarkDesc = if (isBookmarked) {
                    "Remover ${session.title} dos favoritos da minha agenda"
                } else {
                    "Guardar ${session.title} nos favoritos da minha agenda"
                }

                IconButton(
                    onClick = { onBookmarkToggle(session) },
                    modifier = Modifier
                        .size(48.dp)
                        .semantics {
                            contentDescription = bookmarkDesc
                            role = Role.Button
                        }
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = null,
                        tint = if (isBookmarked) Color(0xFFF5BA13) else MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Título Principal em Azul Noturno
            Text(
                text = session.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Oradores e Moderação em Azul Cerúleo
            val participants = buildList {
                addAll(session.speakers.map { it.name })
                session.moderator?.let { add("Moderação: $it") }
            }.joinToString(" • ")

            if (participants.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = participants,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.tertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Descrição e Localização Específica
            if (session.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = session.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Localização (se não for os Claustros principais)
            if (!session.venue.contains("Claustros", ignoreCase = true)) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Local: ${session.venue}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
