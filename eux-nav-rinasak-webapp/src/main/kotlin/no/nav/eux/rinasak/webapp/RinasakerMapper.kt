package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.model.entity.Fagsak
import no.nav.eux.rinasak.openapi.model.*
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*

val NavRinasakCreateType.navRinasakCreateRequest:
        NavRinasakCreateRequest
    get() = NavRinasakCreateRequest(
        navRinasakUuid = UUID.randomUUID(),
        rinasakId = rinasakId,
        opprettetBruker = opprettetBruker,
        opprettetDato = LocalDateTime.now(),
        fagsak = fagsak.toFagsakCreateRequest()
    )

fun NavRinasakFagsakCreateType?.toFagsakCreateRequest() =
    this?.let {
        NavRinasakCreateRequest.FagsakCreateRequest(
            id = id,
            tema = it.tema,
            system = it.system,
            nr = it.nr,
            type = it.type,
        )
    }

fun List<NavRinasakFinnResponse>.toNavRinasakSearchResponseType() =
    NavRinasakSearchResponseType(
        navRinasaker = map {
            NavRinasakType(
                rinasakId = it.navRinasak.rinasakId,
                opprettetBruker = it.navRinasak.opprettetBruker,
                opprettetDato = OffsetDateTime.now(),
                fagsak = it.fagsak?.toFagsakType()
            )
        }
    )

fun Fagsak.toFagsakType() =
    FagsakType(
        id = id,
        tema = tema,
        system = system,
        nr = nr,
        type = type,
    )
