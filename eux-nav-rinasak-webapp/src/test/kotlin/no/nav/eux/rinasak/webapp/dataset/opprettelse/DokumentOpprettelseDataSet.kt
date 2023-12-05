package no.nav.eux.rinasak.webapp.dataset.opprettelse

import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.common.uuid2
import no.nav.eux.rinasak.webapp.common.uuid3
import no.nav.eux.rinasak.webapp.model.opprettelse.DokumentOpprettelse

val dokumentOpprettelse1 = DokumentOpprettelse(
    sedId = uuid1,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000001",
)

val dokumentOpprettelse1Oppdatert = DokumentOpprettelse(
    sedId = uuid1,
    sedVersjon = 1,
    sedType = "oppdatert",
    dokumentInfoId = "000000011",
)

val dokumentOpprettelse2 = DokumentOpprettelse(
    sedId = uuid2,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000002",
)

val dokumentOpprettelse3 = DokumentOpprettelse(
    sedId = uuid3,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000003",
)

val dokumenterOpprettelseEttElement = listOf(dokumentOpprettelse1)
