package no.nav.eux.rinasak.webapp.model.opprettelse

data class InitiellFagsakOpprettelse(
    val id: String,
    val tema: String,
    val system: String?,
    val nr: String?,
    val type: String,
    val fnr: String,
    val arkiv: String,
)
