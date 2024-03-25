package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.common.toEnum
import no.nav.eux.rinasak.model.entity.SedJournalstatus
import no.nav.eux.rinasak.openapi.model.SedJournalstatusSearchResponseType
import no.nav.eux.rinasak.openapi.model.SedJournalstatusType
import java.time.ZoneOffset.UTC

fun SedJournalstatus?.toSedJournalstatusSearchResponseType() =
    SedJournalstatusSearchResponseType(
        sedJournalstatuser = toSedJournalstatuser()
    )

fun SedJournalstatus?.toSedJournalstatuser() =
    if (this == null)
        emptyList()
    else
        listOf(
            SedJournalstatusType(
                sedId = sedId,
                sedVersjon = sedVersjon,
                sedJournalstatus = status.name.toEnum(),
                endretBruker = endretBruker,
                endretTidspunkt = endretTidspunkt.atOffset(UTC),
                opprettetBruker = opprettetBruker,
                opprettetTidspunkt = opprettetTidspunkt.atOffset(UTC),
            )
        )
