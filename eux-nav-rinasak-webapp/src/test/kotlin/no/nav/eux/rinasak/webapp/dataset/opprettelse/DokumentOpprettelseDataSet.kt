package no.nav.eux.rinasak.webapp.dataset.opprettelse

import no.nav.eux.rinasak.webapp.common.forventetSedId
import no.nav.eux.rinasak.webapp.model.opprettelse.DokumentOpprettelse
import java.util.*

val dokumentOpprettelse = DokumentOpprettelse(
    sedId = forventetSedId,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000001",
)

val nyttDokumentSedId: UUID = UUID.fromString("00000000-0000-0000-0000-000000000004")

val nyttDokumentOpprettelse = DokumentOpprettelse(
    sedId = nyttDokumentSedId,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000111",
)
