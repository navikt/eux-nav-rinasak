package no.nav.eux.rinasak.webapp.dataset.opprettelse

import no.nav.eux.rinasak.webapp.model.opprettelse.NavRinasakOpprettelse

val navRinasakOpprettelse = NavRinasakOpprettelse(
    rinasakId = 1,
    overstyrtEnhetsnummer = "1234",
    initiellFagsak = initiellFagsakOpprettelse,
    dokumenter = listOf(dokumentOpprettelse),
)

val navRinasakOpprettelseUtenRelasjoner = NavRinasakOpprettelse(
    rinasakId = 1,
    overstyrtEnhetsnummer = "1234",
    initiellFagsak = null,
    dokumenter = null,
)

val navRinasakOpprettelseMedUgyldigFnr = NavRinasakOpprettelse(
    rinasakId = 4,
    overstyrtEnhetsnummer = "1234",
    initiellFagsak = initiellFagsakOpprettelse.copy(fnr = "invalid"),
    dokumenter = null,
)
