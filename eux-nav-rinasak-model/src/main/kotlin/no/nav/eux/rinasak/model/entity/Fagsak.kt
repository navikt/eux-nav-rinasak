package no.nav.eux.rinasak.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

@Entity
data class Fagsak(
    @Id
    val navRinasakUuid: UUID,
    val type: String,
    val tema: String,
    val system: String?,
    val nr: String?,
    val fnr: String,
    @Column(updatable = false)
    val opprettetBruker: String = "ukjent",
    @Column(updatable = false)
    val opprettetTidspunkt: LocalDateTime = now(),
    @Column(insertable = false)
    val endretBruker: String = "ukjent",
    @Column(insertable = false)
    val endretTidspunkt: LocalDateTime = now(),
)
