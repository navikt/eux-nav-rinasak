package no.nav.eux.rinasak.webapp.model.opprettelse

import java.util.*

data class DokumentOpprettelse(
    val sedId: UUID,
    val sedVersjon: Int,
    val sedType: String?,
    val dokumentInfoId: String?,
)
