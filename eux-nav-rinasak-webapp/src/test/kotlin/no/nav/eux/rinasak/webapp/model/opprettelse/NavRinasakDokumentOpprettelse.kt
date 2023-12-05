package no.nav.eux.rinasak.webapp.model.opprettelse

import java.util.*

data class NavRinasakDokumentOpprettelse(
    val rinasakId: Int,
    val sedId: UUID,
    val sedVersjon: Int,
    val sedType: String,
    val dokumentInfoId: String?,
)
