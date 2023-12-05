package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.model.dto.NavRinasakPatch
import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.model.entity.InitiellFagsak
import no.nav.eux.rinasak.openapi.model.*
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.UUID.randomUUID

val NavRinasakCreateType.navRinasakCreateRequest:
        NavRinasakCreateRequest
    get() = NavRinasakCreateRequest(
        navRinasakUuid = randomUUID(),
        rinasakId = rinasakId,
        overstyrtEnhetsnummer = overstyrtEnhetsnummer,
        opprettetBruker = opprettetBruker ?: "ukjent",
        opprettetDato = LocalDateTime.now(),
        initiellFagsak = initiellFagsak.toInitiellFagsakCreateRequest(),
        dokumenter = dokumenter.toDokumentCreateRequests()
    )

val NavRinasakPatchType.navRinasakPatch:
        NavRinasakPatch
    get() = NavRinasakPatch(
        rinasakId = rinasakId,
        overstyrtEnhetsnummer = overstyrtEnhetsnummer,
        initiellFagsak = initiellFagsak.toInitiellFagsakPatchRequest(),
        dokumenter = dokumenter.toDokumentPatchRequests()
    )

val NavRinasakSearchCriteriaType.navRinasakFinnRequest:
        NavRinasakFinnRequest
    get() = NavRinasakFinnRequest(
        rinasakId = rinasakId
    )

fun List<NavRinasakDokumentCreateType>?.toDokumentCreateRequests() =
    this?.let { sedCreateTypes -> sedCreateTypes.map { it.toDokumentCreateRequest() } }
        ?: emptyList()

fun List<NavRinasakDokumentPatchType>?.toDokumentPatchRequests() =
    this?.let { sedCreateTypes -> sedCreateTypes.map { it.toDokumentPatchRequest() } }
        ?: emptyList()

fun NavRinasakDokumentCreateType.toDokumentCreateRequest() =
    NavRinasakCreateRequest.DokumentCreateRequest(
        dokumentUuid = randomUUID(),
        sedId = sedId,
        sedVersjon = sedVersjon,
        dokumentInfoId = dokumentInfoId,
        sedType = sedType,
    )

fun NavRinasakDokumentPatchType.toDokumentPatchRequest() =
    NavRinasakPatch.DokumentPatch(
        dokumentUuid = randomUUID(),
        sedId = sedId,
        sedVersjon = sedVersjon,
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
            fnr = it.fnr,
            arkiv = it.arkiv
        )
    }

fun NavRinasakInitiellFagsakPatchType?.toInitiellFagsakPatchRequest() =
    this?.let {
        NavRinasakPatch.InitiellFagsakPatch(
            id = id,
            tema = it.tema,
            system = it.system,
            nr = it.nr,
            type = it.type,
            fnr = it.fnr,
            arkiv = it.arkiv,
        )
    }

fun List<NavRinasakFinnResponse>.toNavRinasakSearchResponseType() =
    NavRinasakSearchResponseType(
        navRinasaker = map { finnResponse ->
            NavRinasakType(
                rinasakId = finnResponse.navRinasak.rinasakId,
                overstyrtEnhetsnummer = finnResponse.navRinasak.overstyrtEnhetsnummer,
                opprettetBruker = finnResponse.navRinasak.opprettetBruker,
                opprettetDato = finnResponse.navRinasak.opprettetDato.atOffset(UTC),
                initiellFagsak = finnResponse.initiellFagsak?.toInitiellFagsakType(),
                dokumenter = finnResponse.dokumenter?.map { it.toDokumentType() }
            )
        }
    )

fun Dokument.toDokumentType() =
    DokumentType(
        sedId = sedId,
        sedVersjon = sedVersjon,
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
        fnr = fnr,
        arkiv = arkiv
    )
