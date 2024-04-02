package no.nav.eux.rinasak.webapp.model.base

import no.nav.eux.rinasak.openapi.model.SedJournalstatus

data class SedJournalstatusPutTestModel(
    val sedId: java.util.UUID,
    val sedVersjon: Int,
    val sedJournalstatus: SedJournalstatus
)
