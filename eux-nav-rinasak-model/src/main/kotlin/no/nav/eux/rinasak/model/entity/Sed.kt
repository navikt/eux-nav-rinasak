package no.nav.eux.rinasak.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.*

@Entity
data class Sed(
    @Id
    val sedUuid: UUID,
    val id: String,
    val navRinasakUuid: UUID,
    val dokumentInfoId: String?,
    val type: String?,
    val opprettetBruker: String,
    val opprettetDato: LocalDateTime,
)
