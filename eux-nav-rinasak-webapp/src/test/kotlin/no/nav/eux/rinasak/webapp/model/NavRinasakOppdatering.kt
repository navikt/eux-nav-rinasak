package no.nav.eux.rinasak.webapp.model

import no.nav.eux.rinasak.webapp.dataset.dokumenterEttElement

data class NavRinasakOppdatering(
    val rinasakId: Int = 1,
    val overstyrtEnhetsnummer: String? = "1234",
    val initiellFagsak: InitiellFagsakOppdatering? = InitiellFagsakOppdatering(),
    val dokumenter: List<Dokument>? = dokumenterEttElement,
)

data class InitiellFagsakOppdatering(
    val id: String = "fagsak-1",
    val tema: String? = "AAA",
    val system: String? = "system",
    val nr: String? = "nr",
    val type: String? = "type",
    val fnr: String? = "03028700000",
)