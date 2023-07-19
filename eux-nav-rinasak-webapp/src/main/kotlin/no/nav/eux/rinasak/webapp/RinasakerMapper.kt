package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.model.entity.Fagsak
import no.nav.eux.rinasak.model.entity.Sed
import no.nav.eux.rinasak.openapi.model.*
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.UUID.randomUUID

val NavRinasakCreateType.navRinasakCreateRequest:
        NavRinasakCreateRequest
    get() = NavRinasakCreateRequest(
        navRinasakUuid = randomUUID(),
        rinasakId = rinasakId,
        opprettetBruker = opprettetBruker,
        opprettetDato = LocalDateTime.now(),
        fagsak = fagsak.toFagsakCreateRequest(),
        seder = seder.sedCreateRequests()
    )

fun List<NavRinasakSedCreateType>?.sedCreateRequests() =
    this?.let { sedCreateTypes -> sedCreateTypes.map { it.toSedCreateRequest() } }
        ?: emptyList()

fun NavRinasakSedCreateType.toSedCreateRequest() =
    NavRinasakCreateRequest.SedCreateRequest(
        sedUuid = randomUUID(),
        id = id!!,
        dokumentInfoId = dokumentInfoId,
        type = type,
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
        navRinasaker = map { finnResponse ->
            NavRinasakType(
                rinasakId = finnResponse.navRinasak.rinasakId,
                opprettetBruker = finnResponse.navRinasak.opprettetBruker,
                opprettetDato = OffsetDateTime.now(),
                fagsak = finnResponse.fagsak?.toFagsakType(),
                seder = finnResponse.seder?.map { it.toNavRinasakSedCreateType() }
            )
        }
    )

fun Sed.toNavRinasakSedCreateType() =
    NavRinasakSedCreateType(
        id = id,
        type = type,
        dokumentInfoId = dokumentInfoId
    )

fun Fagsak.toFagsakType() =
    FagsakType(
        id = id,
        tema = tema,
        system = system,
        nr = nr,
        type = type,
    )
