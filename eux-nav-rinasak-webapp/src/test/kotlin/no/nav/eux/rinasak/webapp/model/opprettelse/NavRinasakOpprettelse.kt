package no.nav.eux.rinasak.webapp.model.opprettelse

import no.nav.eux.rinasak.webapp.dataset.opprettelse.dokumenterOpprettelseEttElement

data class NavRinasakOpprettelse(
    val rinasakId: Int = 1,
    val overstyrtEnhetsnummer: String? = "1234",
    val initiellFagsak: InitiellFagsakOpprettelse?,
    val dokumenter: List<DokumentOpprettelse>? = dokumenterOpprettelseEttElement,
)
