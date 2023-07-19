package no.nav.eux.rinasak.webapp.model

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import java.time.OffsetDateTime

data class NavRinasak(
    val rinasakId: String,
    val fagsak: Fagsak? = null,
    val opprettetDato: OffsetDateTime = offsetDateTime1,
)
