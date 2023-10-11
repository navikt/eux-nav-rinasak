package no.nav.eux.rinasak.webapp.model

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import no.nav.eux.rinasak.webapp.dataset.dokumenterEttElement
import java.time.LocalDateTime

data class NavRinasakOpprettelse(
    val rinasakId: Int = 1,
    val overstyrtEnhetsnummer: String? = "1234",
    val initiellFagsak: InitiellFagsak? = InitiellFagsak(),
    val dokumenter: List<Dokument>? = dokumenterEttElement,
    val opprettetBruker: String = "bruker",
)

data class InitiellFagsak(
    val id: String = "fagsak-1",
    val tema: String? = "AAA",
    val system: String? = "system",
    val nr: String? = "nr",
    val type: String? = "type",
    val opprettetBruker: String = "fagsak-bruker",
    val opprettetDato: LocalDateTime = offsetDateTime1.toLocalDateTime(),
)