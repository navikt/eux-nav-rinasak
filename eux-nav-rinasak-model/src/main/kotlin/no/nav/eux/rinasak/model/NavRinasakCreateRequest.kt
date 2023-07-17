package no.nav.eux.rinasak.model

import java.time.LocalDateTime

class NavRinasakCreateRequest(
    val rinasakId: String,
    val opprettetBruker: String,
    val opprettetDato: LocalDateTime
) {
    val navRinasak =
        NavRinasak(
            rinasakId = rinasakId,
            opprettetBruker = opprettetBruker,
            opprettetDato = opprettetDato,
        )
}
