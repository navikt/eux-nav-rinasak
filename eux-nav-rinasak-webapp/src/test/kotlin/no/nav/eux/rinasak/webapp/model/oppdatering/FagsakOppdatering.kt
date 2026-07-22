package no.nav.eux.rinasak.webapp.model.oppdatering

data class FagsakOppdatering(
    val tema: String,
    val type: String,
    val system: String?,
    val nr: String?,
    val fnr: String,
)
