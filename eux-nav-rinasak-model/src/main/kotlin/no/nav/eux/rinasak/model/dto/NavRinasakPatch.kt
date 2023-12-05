package no.nav.eux.rinasak.model.dto

import no.nav.eux.rinasak.model.entity.Dokument
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
        val tema: String,
        val system: String?,
        val nr: String?,
        val type: String,
        val fnr: String,
        val arkiv: String,
    ) {
        fun entity(navRinasakUuid: UUID, opprettetBruker: String, opprettetTidspunkt: LocalDateTime) =
            this.let {
                InitiellFagsak(
                    navRinasakUuid = navRinasakUuid,
                    id = it.id,
                    tema = it.tema,
                    system = it.system,
                    nr = it.nr,
                    type = it.type,
                    fnr = it.fnr,
                    arkiv = it.arkiv,
                    opprettetBruker = opprettetBruker,
                    opprettetTidspunkt = opprettetTidspunkt
                )
            }
    }

    data class DokumentPatch(
        val sedId: UUID,
        val sedVersjon: Int,
        val sedType: String,
        val dokumentUuid: UUID,
        val dokumentInfoId: String?,
    ) {
        fun entity(navRinasakUuid: UUID) =
            Dokument(
                dokumentUuid = dokumentUuid,
                sedId = sedId,
                sedVersjon = sedVersjon,
                navRinasakUuid = navRinasakUuid,
                dokumentInfoId = dokumentInfoId,
                sedType = sedType,
            )
    }
}
