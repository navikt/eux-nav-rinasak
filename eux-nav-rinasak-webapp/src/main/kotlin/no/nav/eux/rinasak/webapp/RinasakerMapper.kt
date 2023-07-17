package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.NavRinasak
import no.nav.eux.rinasak.model.NavRinasakCreateRequest
import no.nav.eux.rinasak.openapi.model.NavRinasakCreateType
import no.nav.eux.rinasak.openapi.model.NavRinasakSearchResponseType
import no.nav.eux.rinasak.openapi.model.NavRinasakType
import java.time.LocalDateTime
import java.time.OffsetDateTime

val NavRinasakCreateType.navRinasakCreateRequest:
        NavRinasakCreateRequest
    get() = NavRinasakCreateRequest(
        rinasakId = this.rinasakId,
        opprettetBruker = this.opprettetBruker,
        opprettetDato = LocalDateTime.now()
    )

fun List<NavRinasak>.toNavRinasakSearchResponseType() =
    NavRinasakSearchResponseType(
        navRinasaker = this.map {
            NavRinasakType(
                rinasakId = it.rinasakId,
                opprettetBruker = it.opprettetBruker,
                opprettetDato = OffsetDateTime.now()
            )
        }
    )
