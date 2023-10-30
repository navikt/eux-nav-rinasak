package no.nav.eux.rinasak.webapp.dataset

import no.nav.eux.rinasak.webapp.model.Dokument
import java.util.*

val dokument1 = Dokument(
    sedId = UUID.fromString("164a85f4-a031-48e3-a349-53f516005b67"),
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000001",
)

val dokument1Oppdatert = Dokument(
    sedId = UUID.fromString("164a85f4-a031-48e3-a349-53f516005b67"),
    sedVersjon = 1,
    sedType = "oppdatert",
    dokumentInfoId = "000000011",
)

val dokument2 = Dokument(
    sedId = UUID.fromString("164a85f4-a031-48e3-a349-53f516005b68"),
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000002",
)

val dokument3 = Dokument(
    sedId = UUID.fromString("164a85f4-a031-48e3-a349-53f516005b69"),
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "000000003",
)

val dokumenterEttElement = listOf(dokument1)
