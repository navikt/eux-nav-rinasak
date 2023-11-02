package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.dto.DokumentCreateRequest
import no.nav.eux.rinasak.openapi.model.DokumentCreateType
import java.util.UUID.randomUUID

fun toDokumentCreateRequest(rinasakId: Int, dokument: DokumentCreateType) =
    DokumentCreateRequest(
        rinasakId = rinasakId,
        sedId = dokument.sedId,
        sedVersjon = dokument.sedVersjon,
        sedType = dokument.sedType,
        dokumentInfoId = dokument.dokumentInfoId,
        dokumentUuid = randomUUID(),
    )
