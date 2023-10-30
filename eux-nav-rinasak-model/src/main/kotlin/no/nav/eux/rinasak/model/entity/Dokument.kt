package no.nav.eux.rinasak.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

@Entity
data class Dokument(
    @Id
    val dokumentUuid: UUID,
    val navRinasakUuid: UUID,
    val sedId: UUID,
    val sedVersjon: Int,
    val sedType: String,
    val dokumentInfoId: String?,
    @Column(updatable = false)
    val opprettetBruker: String = "",
    @Column(updatable = false)
    val opprettetDato: LocalDateTime = now(),
)
