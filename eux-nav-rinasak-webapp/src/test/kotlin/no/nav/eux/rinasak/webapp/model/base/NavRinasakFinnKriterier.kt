package no.nav.eux.rinasak.webapp.model.base

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import java.time.OffsetDateTime

data class NavRinasakFinnKriterier(
    val rinasakId: Int = 1,
    val opprettetTidspunkt: OffsetDateTime = offsetDateTime1,
)
