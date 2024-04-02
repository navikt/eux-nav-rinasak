package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.common.toEnum
import no.nav.eux.rinasak.model.entity.SedJournalstatus
import no.nav.eux.rinasak.openapi.model.SedJournalstatusSearchResponseType
import no.nav.eux.rinasak.openapi.model.SedJournalstatusType
import java.time.ZoneOffset.UTC
import no.nav.eux.rinasak.openapi.model.SedJournalstatus as SedJournalstatusOpenApiEnum

fun List<SedJournalstatus>.toSedJournalstatusSearchResponseType() =
    SedJournalstatusSearchResponseType(
        sedJournalstatuser = toSedJournalstatuser()
    )

fun List<SedJournalstatus>.toSedJournalstatuser() =
    this.map { it.toSedJournalstatus() }

fun SedJournalstatus.toSedJournalstatus() =
    SedJournalstatusType(
        sedId = sedId,
        sedVersjon = sedVersjon,
        sedJournalstatus = status.name.toEnum(),
        endretBruker = endretBruker,
        endretTidspunkt = endretTidspunkt.atOffset(UTC),
        opprettetBruker = opprettetBruker,
        opprettetTidspunkt = opprettetTidspunkt.atOffset(UTC),
    )

fun SedJournalstatusOpenApiEnum?.toStatus(): SedJournalstatus.Status? =
    this?.name?.toEnum<SedJournalstatus.Status>()
