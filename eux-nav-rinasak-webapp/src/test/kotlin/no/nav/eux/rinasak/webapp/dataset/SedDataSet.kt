package no.nav.eux.rinasak.webapp.dataset

import no.nav.eux.rinasak.webapp.model.Dokument
import java.util.*

val dokument = Dokument(
    sedId = UUID.fromString("164a85f4-a031-48e3-a349-53f516005b67"),
    sedVersjon = 1,
    sedType = "type",
    dokumentInfoId = "123456789",
)

val dokumenterEttElement = listOf(dokument)
