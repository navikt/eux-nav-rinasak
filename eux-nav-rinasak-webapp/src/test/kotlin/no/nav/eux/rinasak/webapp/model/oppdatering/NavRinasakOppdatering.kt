package no.nav.eux.rinasak.webapp.model.oppdatering

import java.util.UUID

data class NavRinasakOppdatering(
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String?,
    val initiellFagsak: InitiellFagsakOppdatering?,
    val dokumenter: List<DokumentOppdatering>?,
)

data class DokumentOppdatering(
    val sedId: UUID,
    val sedVersjon: Int,
    val sedType: String,
    val dokumentInfoId: String?,
)

data class InitiellFagsakOppdatering(
    val id: String?,
    val tema: String,
    val system: String?,
    val nr: String?,
    val type: String,
    val fnr: String,
    val arkiv: String,
)
