package no.nav.eux.rinasak.webapp.model

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import java.time.LocalDateTime

data class InitiellFagsak(
    val id: String = "fagsak-1",
    val tema: String = "AAA",
    val system: String? = "system",
    val nr: String? = "nr",
    val type: String = "type",
    val fnr: String = "03028700000",
    val arkiv: String = "PSAK",
    val opprettetBruker: String = "fagsak-bruker",
    val opprettetTidspunkt: LocalDateTime = offsetDateTime1.toLocalDateTime(),
)
