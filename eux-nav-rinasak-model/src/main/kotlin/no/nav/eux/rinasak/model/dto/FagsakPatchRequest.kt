package no.nav.eux.rinasak.model.dto

import no.nav.eux.rinasak.model.entity.Fagsak
import java.time.LocalDateTime
import java.util.*

data class FagsakPatchRequest(
    val tema: String,
    val system: String?,
    val nr: String?,
    val type: String,
    val fnr: String,
    val bruker: String,
) {
    fun toFagsak(navRinasakUuid: UUID) =
        Fagsak(
            navRinasakUuid = navRinasakUuid,
            tema = tema,
            system = system,
            nr = nr,
            type = type,
            fnr = fnr,
            opprettetBruker = bruker,
            opprettetTidspunkt = LocalDateTime.now(),
        )
}
