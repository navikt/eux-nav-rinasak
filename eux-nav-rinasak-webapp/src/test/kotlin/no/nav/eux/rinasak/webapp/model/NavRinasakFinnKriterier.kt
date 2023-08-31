package no.nav.eux.rinasak.webapp.model

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import java.time.OffsetDateTime

data class NavRinasakFinnKriterier(
    val rinasakId: Int = 1,
    val opprettetDato: OffsetDateTime = offsetDateTime1,
)
