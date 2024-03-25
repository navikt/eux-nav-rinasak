package no.nav.eux.rinasak.webapp.model.base

import java.time.LocalDateTime
import java.util.*

data class SedJournalstatusTestModel(
    val sedId: UUID,
    val sedVersjon: Int,
    val sedJournalstatus: Status,
    val endretBruker: String,
    val endretTidspunkt: LocalDateTime,
    val opprettetBruker: String,
    val opprettetTidspunkt: LocalDateTime,
) {

    enum class Status {
        JOURNALFOERT, UKJENT
    }
}
