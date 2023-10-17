package no.nav.eux.rinasak.model.dto

import no.nav.eux.rinasak.model.entity.InitiellFagsak
import java.time.LocalDateTime
import java.util.*

data class NavRinasakPatch(
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String?,
    val initiellFagsak: InitiellFagsakPatch?,
    val dokumenter: List<DokumentPatch>?,
) {
    data class InitiellFagsakPatch(
        val id: String?,
        val tema: String?,
        val system: String?,
        val nr: String?,
        val type: String?,
        val fnr: String?,
    )

    data class DokumentPatch(
        val sedId: UUID,
        val sedVersjon: Int,
        val sedType: String?,
        val dokumentUuid: UUID,
        val dokumentInfoId: String?,
    )

    fun initiellFagsakEntity(navRinasakUuid: UUID, opprettetBruker: String, opprettetDato: LocalDateTime) =
        this.initiellFagsak?.let {
            InitiellFagsak(
                navRinasakUuid = navRinasakUuid,
                id = it.id,
                tema = it.tema,
                system = it.system,
                nr = it.nr,
                type = it.type,
                fnr = it.fnr,
                opprettetBruker = opprettetBruker,
                opprettetDato = opprettetDato
            )
        }
}
