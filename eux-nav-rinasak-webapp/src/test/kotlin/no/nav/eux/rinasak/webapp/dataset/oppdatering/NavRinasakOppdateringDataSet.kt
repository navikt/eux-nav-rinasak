package no.nav.eux.rinasak.webapp.dataset.oppdatering

import no.nav.eux.rinasak.webapp.common.forventetSedId
import no.nav.eux.rinasak.webapp.model.oppdatering.DokumentOppdatering
import no.nav.eux.rinasak.webapp.model.oppdatering.InitiellFagsakOppdatering
import no.nav.eux.rinasak.webapp.model.oppdatering.NavRinasakOppdatering
import java.util.UUID

val tilagtDokumentSedId: UUID = UUID.fromString("00000000-0000-0000-0000-000000000003")

private val eksisterendeDokumentOppdatering = DokumentOppdatering(
    sedId = forventetSedId,
    sedVersjon = 1,
    sedType = "oppdatert",
    dokumentInfoId = "000000011",
)

private val tilagtDokument = DokumentOppdatering(
    sedId = tilagtDokumentSedId,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000003",
)

val initiellFagsakOppdatering = InitiellFagsakOppdatering(
    id = "fagsak-1",
    tema = "BBB",
    system = "oppdatertSystem",
    nr = "oppdatertNr",
    type = "endret",
    fnr = "03028700001",
    arkiv = "PSAK",
)

val navRinasakOppdatering = NavRinasakOppdatering(
    rinasakId = 1,
    overstyrtEnhetsnummer = "5678",
    initiellFagsak = initiellFagsakOppdatering,
    dokumenter = listOf(eksisterendeDokumentOppdatering, tilagtDokument)
)
