package no.nav.eux.rinasak.webapp.dataset.oppdatering

import no.nav.eux.rinasak.webapp.common.uuid4
import no.nav.eux.rinasak.webapp.model.opprettelse.NavRinasakDokumentOpprettelse

val navRinasakDokumentOpprettelse = NavRinasakDokumentOpprettelse(
    rinasakId = 1,
    sedId = uuid4,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000111",
)
