package no.nav.eux.rinasak.webapp.model.opprettelse

data class FagsakOpprettelse(
    val tema: String,
    val type: String,
    val system: String?,
    val nr: String?,
    val fnr: String,
)
