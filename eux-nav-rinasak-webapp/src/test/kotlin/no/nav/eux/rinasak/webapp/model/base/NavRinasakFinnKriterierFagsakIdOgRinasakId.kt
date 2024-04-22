package no.nav.eux.rinasak.webapp.model.base

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import java.time.OffsetDateTime

data class NavRinasakFinnKriterierFagsakIdOgRinasakId(
    val rinasakId: Int = 1,
    val fagsakId: String = "fagsak-1",
    val opprettetTidspunkt: OffsetDateTime = offsetDateTime1,
)
