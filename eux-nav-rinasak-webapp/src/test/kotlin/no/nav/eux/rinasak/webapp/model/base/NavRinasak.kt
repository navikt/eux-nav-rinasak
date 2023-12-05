package no.nav.eux.rinasak.webapp.model.base

import java.time.OffsetDateTime

data class NavRinasak(
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String?,
    val initiellFagsak: InitiellFagsak?,
    val dokumenter: List<Dokument>?,
    val opprettetBruker: String,
    val opprettetTidspunkt: OffsetDateTime,
)
