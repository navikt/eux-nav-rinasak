package no.nav.eux.rinasak.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import no.nav.eux.rinasak.model.dto.NavRinasakPatch
import java.time.LocalDateTime
import java.util.*

@Entity
data class InitiellFagsak(
    @Id
    val navRinasakUuid: UUID,
    val id: String?,
    val tema: String?,
    val system: String?,
    val nr: String?,
    val type: String?,
    val fnr: String?,
    val opprettetBruker: String,
    val opprettetDato: LocalDateTime,
) {
    fun patch(patch: NavRinasakPatch.InitiellFagsakPatch) =
        this.copy(
            id = patch.id ?: id,
            tema = patch.tema ?: tema,
            system = patch.system ?: system,
            nr = patch.nr ?: nr,
            type = patch.type ?: type,
            fnr = patch.fnr ?: fnr
        )
}
