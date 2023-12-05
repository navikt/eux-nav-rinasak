package no.nav.eux.rinasak.webapp.model.base

import java.time.LocalDateTime
import java.util.*

data class Dokument(
    val sedId: UUID,
    val sedVersjon: Int,
    val sedType: String?,
    val dokumentInfoId: String?,
    val opprettetBruker: String,
    val opprettetTidspunkt: LocalDateTime,
)
