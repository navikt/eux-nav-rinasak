package no.nav.eux.rinasak.webapp.model

import no.nav.eux.rinasak.webapp.dataset.dokumenterEttElement

data class NavRinasakOpprettelse(
    val rinasakId: Int = 1,
    val overstyrtEnhetsnummer: String? = "1234",
    val initiellFagsak: InitiellFagsak? = InitiellFagsak(),
    val dokumenter: List<Dokument>? = dokumenterEttElement,
    val opprettetBruker: String = "bruker",
)
