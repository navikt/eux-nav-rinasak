package no.nav.eux.rinasak.webapp.model

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import java.time.OffsetDateTime

data class NavRinasak(
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String?,
    val initiellFagsak: InitiellFagsak? = null,
    val dokumenter: List<Dokument>? = null,
    val opprettetTidspunkt: OffsetDateTime = offsetDateTime1,
)
