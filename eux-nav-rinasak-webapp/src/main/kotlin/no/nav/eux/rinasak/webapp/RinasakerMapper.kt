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

fun toNavRinasakCreateRequest(createType: NavRinasakCreateType, bruker: String) =
    NavRinasakCreateRequest(
        navRinasakUuid = randomUUID(),
        rinasakId = createType.rinasakId,
        overstyrtEnhetsnummer = createType.overstyrtEnhetsnummer,
        opprettetBruker = bruker,
        opprettetTidspunkt = LocalDateTime.now(),
        initiellFagsak = createType.initiellFagsak.toInitiellFagsakCreateRequest(),
        dokumenter = createType.dokumenter.toDokumentCreateRequests()
    )

val NavRinasakPatchType.navRinasakPatch
    get() = NavRinasakPatch(
        rinasakId = rinasakId,
        overstyrtEnhetsnummer = overstyrtEnhetsnummer,
        initiellFagsak = initiellFagsak.toInitiellFagsakPatchRequest(),
        dokumenter = dokumenter.toDokumentPatchRequests()
    )

val NavRinasakSearchCriteriaType.navRinasakFinnRequest
    get() = NavRinasakFinnRequest(
        rinasakId = rinasakId
    )

fun NavRinasakFinnResponse.toNavRinasakType() =
    NavRinasakType(
        rinasakId = navRinasak.rinasakId,
        overstyrtEnhetsnummer = navRinasak.overstyrtEnhetsnummer,
        opprettetBruker = navRinasak.opprettetBruker,
        opprettetTidspunkt = navRinasak.opprettetTidspunkt.atOffset(UTC),
        initiellFagsak = initiellFagsak?.toInitiellFagsakType(),
        dokumenter = dokumenter?.map { it.toDokumentType() }
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
    NavRinasakSearchResponseType(navRinasaker = map { it.toNavRinasakType() })

fun Dokument.toDokumentType() =
    DokumentType(
        sedId = sedId,
        sedVersjon = sedVersjon,
        sedType = sedType,
        dokumentInfoId = dokumentInfoId,
        opprettetBruker = opprettetBruker,
        opprettetTidspunkt = opprettetTidspunkt.atOffset(UTC),
    )

fun InitiellFagsak.toInitiellFagsakType() =
    FagsakType(
        id = id,
        tema = tema,
        system = system,
        nr = nr,
        type = type,
        fnr = fnr,
        arkiv = arkiv,
        opprettetBruker = opprettetBruker,
        opprettetTidspunkt = opprettetTidspunkt.atOffset(UTC),
    )
