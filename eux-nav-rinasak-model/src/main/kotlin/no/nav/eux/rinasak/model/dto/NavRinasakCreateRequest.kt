package no.nav.eux.rinasak.model.dto

import no.nav.eux.rinasak.model.entity.Fagsak
import no.nav.eux.rinasak.model.entity.NavRinasak
import java.time.LocalDateTime
import java.util.*

data class NavRinasakCreateRequest(
    val navRinasakUuid: UUID,
    val rinasakId: String,
    val opprettetBruker: String,
    val opprettetDato: LocalDateTime,
    val fagsak: FagsakCreateRequest?
) {
    data class FagsakCreateRequest(
        val id: String?,
        val tema: String?,
        val system: String?,
        val nr: String?,
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
}
