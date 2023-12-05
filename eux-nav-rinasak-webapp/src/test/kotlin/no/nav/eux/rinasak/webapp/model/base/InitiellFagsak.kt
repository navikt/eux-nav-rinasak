package no.nav.eux.rinasak.webapp.model.base

import java.time.LocalDateTime

data class InitiellFagsak(
    val id: String,
    val tema: String,
    val system: String?,
    val nr: String?,
    val type: String,
    val fnr: String,
    val arkiv: String,
    val opprettetBruker: String,
    val opprettetTidspunkt: LocalDateTime,
)
