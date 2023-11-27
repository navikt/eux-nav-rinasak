package no.nav.eux.rinasak.webapp.dataset

import no.nav.eux.rinasak.webapp.model.InitiellFagsak
import no.nav.eux.rinasak.webapp.model.NavRinasakOpprettelse

val navRinasakOpprettelseEnkel1 = NavRinasakOpprettelse(
    rinasakId = 1,
    overstyrtEnhetsnummer = "1234",
    initiellFagsak = null,
    dokumenter = null,
    opprettetBruker = "bruker"
)

val navRinasakOpprettelseEnkel2 = NavRinasakOpprettelse(
    rinasakId = 2,
    initiellFagsak = null,
    dokumenter = null,
    opprettetBruker = "bruker"
)

val navRinasakOpprettelseEnkel3 = NavRinasakOpprettelse(
    rinasakId = 3,
    initiellFagsak = null,
    dokumenter = null,
    opprettetBruker = "bruker"
)

val navRinasakOpprettelseInvalid = NavRinasakOpprettelse(
    rinasakId = 4,
    initiellFagsak = InitiellFagsak(fnr = "invalid"),
    dokumenter = null,
    opprettetBruker = "bruker"
)
