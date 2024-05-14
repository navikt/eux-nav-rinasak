package no.nav.eux.rinasak.webapp.model.base

data class SedJournalstatusPutTestModel(
    val rinasakId: Int,
    val sedId: java.util.UUID,
    val sedVersjon: Int,
    val sedJournalstatus: String
)
