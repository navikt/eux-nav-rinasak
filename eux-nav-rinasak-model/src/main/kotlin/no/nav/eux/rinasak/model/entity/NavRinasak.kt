package no.nav.eux.rinasak.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import no.nav.eux.rinasak.model.dto.NavRinasakPatch
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

@Entity
data class NavRinasak(
    @Id
    val navRinasakUuid: UUID,
    val rinasakId: Int,
    val overstyrtEnhetsnummer: String?,
    val opprettetBruker: String = "ukjent",
    val opprettetTidspunkt: LocalDateTime = now(),
) {
    fun patch(patch: NavRinasakPatch) =
        this.copy(
            overstyrtEnhetsnummer = patch.overstyrtEnhetsnummer ?: overstyrtEnhetsnummer
        )
}
