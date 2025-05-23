package no.nav.eux.rinasak.webapp.model.base

import java.time.LocalDateTime

data class Fagsak(
    val tema: String,
    val type: String,
    val system: String?,
    val nr: String?,
    val fnr: String,
    val opprettetBruker: String,
    val opprettetTidspunkt: LocalDateTime,
)
