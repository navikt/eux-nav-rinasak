package no.nav.eux.rinasak.webapp.dataset.opprettelse

import no.nav.eux.rinasak.webapp.model.opprettelse.NavRinasakOpprettelse


val navRinasakOpprettelse = NavRinasakOpprettelse(
    rinasakId = 1,
    overstyrtEnhetsnummer = "1234",
    initiellFagsak = initiellFagsakOpprettelse,
    dokumenter = dokumenterOpprettelseEttElement,
)

val navRinasakOpprettelseEnkel1 = NavRinasakOpprettelse(
    rinasakId = 1,
    overstyrtEnhetsnummer = "1234",
    initiellFagsak = null,
    dokumenter = null,
)

val navRinasakOpprettelseEnkel2 = NavRinasakOpprettelse(
    rinasakId = 2,
    initiellFagsak = null,
    dokumenter = null,
)

val navRinasakOpprettelseEnkel3 = NavRinasakOpprettelse(
    rinasakId = 3,
    initiellFagsak = null,
    dokumenter = null,
)

val navRinasakOpprettelseInvalid = NavRinasakOpprettelse(
    rinasakId = 4,
    initiellFagsak = initiellFagsakOpprettelse.copy(fnr = "invalid"),
    dokumenter = null,
)
