package no.nav.eux.rinasak.webapp.model

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import no.nav.eux.rinasak.webapp.dataset.sedList
import java.time.LocalDateTime

data class NavRinasakOpprettelse(
    val rinasakId: String = "rinasakId-1",
    val fagsak: Fagsak? = Fagsak(),
    val seder: List<Sed> = sedList,
    val opprettetBruker: String = "bruker",
)

data class Fagsak(
    val id: String = "fagsak-1",
    val tema: String? = "tema",
    val system: String? = "system",
    val nr: String? = "nr",
    val type: String? = "type",
    val opprettetBruker: String = "fagsak-bruker",
    val opprettetDato: LocalDateTime = offsetDateTime1.toLocalDateTime(),
)