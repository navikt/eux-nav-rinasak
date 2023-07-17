package no.nav.eux.rinasak.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class NavRinasak(
    @Id
    val rinasakId: String,
    val opprettetBruker: String,
    val opprettetDato: LocalDateTime,
)
