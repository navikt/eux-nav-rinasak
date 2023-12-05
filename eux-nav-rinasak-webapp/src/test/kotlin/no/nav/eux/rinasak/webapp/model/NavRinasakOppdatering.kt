package no.nav.eux.rinasak.webapp.model

data class NavRinasakOppdatering(
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String? = null,
    val initiellFagsak: InitiellFagsakOppdatering? = null,
    val dokumenter: List<Dokument>? = null,
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
