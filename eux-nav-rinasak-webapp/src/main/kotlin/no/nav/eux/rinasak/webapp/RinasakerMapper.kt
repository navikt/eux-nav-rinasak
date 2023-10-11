package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.model.entity.InitiellFagsak
import no.nav.eux.rinasak.openapi.model.*
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.UUID.randomUUID

val NavRinasakCreateType.navRinasakCreateRequest:
        NavRinasakCreateRequest
    get() = NavRinasakCreateRequest(
        navRinasakUuid = randomUUID(),
        rinasakId = rinasakId,
        overstyrtEnhetsnummer = overstyrtEnhetsnummer,
        opprettetBruker = opprettetBruker,
        opprettetDato = LocalDateTime.now(),
        initiellFagsak = initiellFagsak.toInitiellFagsakCreateRequest(),
        dokumenter = dokumenter.toDokumentCreateRequests()
    )

val NavRinasakSearchCriteriaType.toNavRinasakFinnRequest:
        NavRinasakFinnRequest
    get() = NavRinasakFinnRequest(
        rinasakId = rinasakId
    )

fun List<NavRinasakDokumentCreateType>?.toDokumentCreateRequests() =
    this?.let { sedCreateTypes -> sedCreateTypes.map { it.toDokumentCreateRequest() } }
        ?: emptyList()

fun NavRinasakDokumentCreateType.toDokumentCreateRequest() =
    NavRinasakCreateRequest.DokumentCreateRequest(
        dokumentUuid = randomUUID(),
        sedId = sedId!!,
        dokumentInfoId = dokumentInfoId,
        sedType = sedType,
    )

fun NavRinasakInitiellFagsakCreateType?.toInitiellFagsakCreateRequest() =
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
                overstyrtEnhetsnummer = finnResponse.navRinasak.overstyrtEnhetsnummer,
                opprettetBruker = finnResponse.navRinasak.opprettetBruker,
                opprettetDato = OffsetDateTime.now(),
                initiellFagsak = finnResponse.initiellFagsak?.toInitiellFagsakType(),
                dokumenter = finnResponse.dokumenter?.map { it.toDokumentType() }
            )
        }
    )

fun Dokument.toDokumentType() =
    DokumentType(
        sedId = sedId,
        sedType = sedType,
        dokumentInfoId = dokumentInfoId
    )

fun InitiellFagsak.toInitiellFagsakType() =
    FagsakType(
        id = id,
        tema = tema,
        system = system,
        nr = nr,
        type = type,
    )
