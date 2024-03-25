package no.nav.eux.rinasak.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

@Entity
data class SedJournalstatus(
    @Id
    val sedJournalstatusUuid: UUID,
    val sedId: UUID,
    val sedVersjon: Int,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val endretBruker: String = "ukjent",
    val endretTidspunkt: LocalDateTime = now(),
    @Column(updatable = false)
    val opprettetBruker: String = "ukjent",
    @Column(updatable = false)
    val opprettetTidspunkt: LocalDateTime = now(),
) {

    enum class Status {
        JOURNALFOERT, UKJENT
    }
}
