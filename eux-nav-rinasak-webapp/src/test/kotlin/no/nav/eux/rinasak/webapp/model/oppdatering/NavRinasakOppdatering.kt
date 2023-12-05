package no.nav.eux.rinasak.webapp.model.oppdatering

import no.nav.eux.rinasak.webapp.model.base.Dokument

data class NavRinasakOppdatering(
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String?,
    val initiellFagsak: InitiellFagsakOppdatering?,
    val dokumenter: List<Dokument>?,
)

data class InitiellFagsakOppdatering(
    val id: String,
    val tema: String,
    val system: String?,
    val nr: String?,
    val type: String,
    val fnr: String,
    val arkiv: String,
)
