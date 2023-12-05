package no.nav.eux.rinasak.webapp.dataset

import no.nav.eux.rinasak.webapp.model.InitiellFagsakOppdatering
import no.nav.eux.rinasak.webapp.model.NavRinasakOppdatering

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
    dokumenter = listOf(dokument1Oppdatert, dokument3)
)
