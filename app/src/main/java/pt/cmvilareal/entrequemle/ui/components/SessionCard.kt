package pt.cmvilareal.entrequemle.ui.components

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pt.cmvilareal.entrequemle.model.Session

/**
 * Cartão de Sessão do Evento - Desenhado segundo WCAG 2.2 e Material Design 3.
 *
 * Acessibilidade:
 * - Touch target mínimo de 48x48dp no botão de favoritos (WCAG 2.5.5 / 2.5.8).
 * - Semântica TalkBack explícita para o botão de ação e para o cartão completo.
 * - Rácio de contraste superior a 4.5:1 em todos os pares texto/fundo.
 * - Suporte a expansão dinâmica de tipografia (Dynamic Type / Font Scale).
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
                onClickLabel = "Ver detalhes da sessão ${session.title}"
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Linha superior: Hora, Categoria, Promotor e Botão Favorito
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Badge de Horário
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.defaultMinSize(minWidth = 54.dp)
                    ) {
                        Text(
                            text = session.time,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // Categoria
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = session.category.label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }

                    // Promotor especial (ex: FNAC, Cultura a Dentro)
                    if (session.promoter.contains("FNAC", ignoreCase = true) ||
                        session.promoter.contains("Cultura a Dentro", ignoreCase = true)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = session.promoter,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Botão de Favorito acessível (min 48x48dp touch target)
                val bookmarkDesc = if (isBookmarked) {
                    "Remover ${session.title} da minha agenda de favoritos"
                } else {
                    "Guardar ${session.title} na minha agenda de favoritos"
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
                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Título da Sessão (Serif literário)
            Text(
                text = session.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Público-alvo (se específico, ex: crianças ou turmas)
            if (session.targetAudience != "Geral") {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Público: ${session.targetAudience}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Descrição sumária
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

            // Oradores / Intervenientes
            if (session.speakers.isNotEmpty() || session.moderator != null) {
                Spacer(modifier = Modifier.height(8.dp))
                val participants = buildList {
                    addAll(session.speakers.map { it.name })
                    session.moderator?.let { add("Moderação: $it") }
                }.joinToString(" • ")

                Text(
                    text = participants,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
