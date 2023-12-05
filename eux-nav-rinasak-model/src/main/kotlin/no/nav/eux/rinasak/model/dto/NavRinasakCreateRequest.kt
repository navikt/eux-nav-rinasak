package no.nav.eux.rinasak.model.dto

import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.model.entity.InitiellFagsak
import no.nav.eux.rinasak.model.entity.NavRinasak
import java.time.LocalDateTime
import java.util.*

data class NavRinasakCreateRequest(
    val navRinasakUuid: UUID,
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String?,
    val opprettetBruker: String,
    val opprettetTidspunkt: LocalDateTime,
    val initiellFagsak: FagsakCreateRequest?,
    val dokumenter: List<DokumentCreateRequest>,
) {
    data class FagsakCreateRequest(
        val id: String?,
        val tema: String,
        val system: String?,
        val nr: String?,
        val type: String,
        val fnr: String,
        val arkiv: String
    )

    data class DokumentCreateRequest(
        val sedId: UUID,
        val sedVersjon: Int,
        val sedType: String,
        val dokumentUuid: UUID,
        val dokumentInfoId: String?,
    )

    val navRinasakEntity =
        NavRinasak(
            navRinasakUuid = navRinasakUuid,
            rinasakId = rinasakId,
            overstyrtEnhetsnummer = overstyrtEnhetsnummer,
            opprettetBruker = opprettetBruker,
            opprettetTidspunkt = opprettetTidspunkt,
        )

    val initiellFagsakEntity =
        this.initiellFagsak?.let {
            InitiellFagsak(
                navRinasakUuid = this.navRinasakUuid,
                id = it.id,
                tema = it.tema,
                system = it.system,
                nr = it.nr,
                type = it.type,
                fnr = it.fnr,
                arkiv = it.arkiv,
                opprettetBruker = this.opprettetBruker,
                opprettetTidspunkt = this.opprettetTidspunkt
            )
        }

    val dokumentEntities =
        this.dokumenter
            .map {
                Dokument(
                    dokumentUuid = it.dokumentUuid,
                    sedId = it.sedId,
                    sedVersjon = it.sedVersjon,
                    navRinasakUuid = this.navRinasakUuid,
                    dokumentInfoId = it.dokumentInfoId,
                    sedType = it.sedType,
                    opprettetBruker = this.opprettetBruker,
                    opprettetTidspunkt = this.opprettetTidspunkt,
                )
            }
}
