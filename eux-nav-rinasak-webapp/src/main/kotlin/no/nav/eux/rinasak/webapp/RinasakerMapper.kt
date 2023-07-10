package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.NavRinasak
import no.nav.eux.rinasak.model.NavRinasakCreateRequest
import no.nav.eux.rinasak.openapi.model.NavRinasakCreateType
import no.nav.eux.rinasak.openapi.model.NavRinasakSearchResponseType
import no.nav.eux.rinasak.openapi.model.NavRinasakType
import java.time.LocalDateTime.now
import java.time.OffsetDateTime

val NavRinasakCreateType.navRinasakCreateRequest:
        NavRinasakCreateRequest
    get() = NavRinasakCreateRequest(
        navRinasak = NavRinasak(
            rinasakId = this.rinasakId,
            opprettetDato = now()
        )
    )

fun List<NavRinasak>.toNavRinasakSearchResponseType() =
    NavRinasakSearchResponseType(
        rinasaker = this.map {
            NavRinasakType(
                rinasakId = it.rinasakId,
                opprettetDato = OffsetDateTime.now()
            )
        }
    )
