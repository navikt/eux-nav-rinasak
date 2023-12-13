package no.nav.eux.rinasak.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

@Entity
data class InitiellFagsak(
    @Id
    val navRinasakUuid: UUID,
    val id: String?,
    val tema: String,
    val system: String?,
    val nr: String?,
    val type: String,
    val fnr: String,
    val arkiv: String,
    @Column(updatable = false)
    val opprettetBruker: String = "ukjent",
    @Column(updatable = false)
    val opprettetTidspunkt: LocalDateTime = now(),
)
