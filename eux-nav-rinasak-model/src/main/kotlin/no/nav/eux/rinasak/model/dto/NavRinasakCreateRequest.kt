package no.nav.eux.rinasak.model.dto

import no.nav.eux.rinasak.model.entity.Fagsak
import no.nav.eux.rinasak.model.entity.NavRinasak
import no.nav.eux.rinasak.model.entity.Sed
import java.time.LocalDateTime
import java.util.*

data class NavRinasakCreateRequest(
    val navRinasakUuid: UUID,
    val rinasakId: String,
    val opprettetBruker: String,
    val opprettetDato: LocalDateTime,
    val fagsak: FagsakCreateRequest?,
    val seder: List<SedCreateRequest>,
) {
    data class FagsakCreateRequest(
        val id: String?,
        val tema: String?,
        val system: String?,
        val nr: String?,
        val type: String?,
    )

    data class SedCreateRequest(
        val id: String,
        val sedUuid: UUID,
        val dokumentInfoId: String?,
        val type: String?,
    )

    val navRinasakEntity =
        NavRinasak(
            navRinasakUuid = navRinasakUuid,
            rinasakId = rinasakId,
            opprettetBruker = opprettetBruker,
            opprettetDato = opprettetDato,
        )

    val fagsakEntity =
        this.fagsak?.let {
            Fagsak(
                navRinasakUuid = this.navRinasakUuid,
                id = it.id,
                tema = it.tema,
                system = it.system,
                nr = it.nr,
                type = it.type,
                opprettetBruker = this.opprettetBruker,
                opprettetDato = this.opprettetDato
            )
        }

    val sedEntities =
        this.seder
            .map {
                Sed(
                    sedUuid = it.sedUuid,
                    id = it.id,
                    navRinasakUuid = this.navRinasakUuid,
                    dokumentInfoId = it.dokumentInfoId,
                    type = it.type,
                    opprettetBruker = this.opprettetBruker,
                    opprettetDato = this.opprettetDato,
                )
            }
}
