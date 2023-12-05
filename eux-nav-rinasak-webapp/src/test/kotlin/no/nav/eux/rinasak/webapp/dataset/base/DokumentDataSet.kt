package no.nav.eux.rinasak.webapp.dataset.base

import no.nav.eux.rinasak.webapp.common.offsetDateTime1
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.common.uuid2
import no.nav.eux.rinasak.webapp.common.uuid3
import no.nav.eux.rinasak.webapp.model.base.Dokument

val dokument1 = Dokument(
    sedId = uuid1,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000001",
    opprettetBruker = "ukjent",
    opprettetTidspunkt = offsetDateTime1.toLocalDateTime(),
)

val dokument1Oppdatert = Dokument(
    sedId = uuid1,
    sedVersjon = 1,
    sedType = "oppdatert",
    dokumentInfoId = "000000011",
    opprettetBruker = "ukjent",
    opprettetTidspunkt = offsetDateTime1.toLocalDateTime(),
)

val dokument2 = Dokument(
    sedId = uuid2,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000002",
    opprettetBruker = "ukjent",
    opprettetTidspunkt = offsetDateTime1.toLocalDateTime(),
)

val dokument3 = Dokument(
    sedId = uuid3,
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000003",
    opprettetBruker = "ukjent",
    opprettetTidspunkt = offsetDateTime1.toLocalDateTime(),
)

val dokumenterEttElement = listOf(dokument1)
