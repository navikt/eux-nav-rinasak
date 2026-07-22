package no.nav.eux.rinasak.webapp.model.opprettelse

data class NavRinasakOpprettelse(
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String?,
    val initiellFagsak: InitiellFagsakOpprettelse?,
    val dokumenter: List<DokumentOpprettelse>?,
)
