package no.nav.eux.rinasak.model.dto

import java.util.*

data class DokumentCreateRequest(
    val rinasakId: Int,
    val sedId: UUID,
    val sedVersjon: Int,
    val sedType: String,
    val dokumentUuid: UUID,
    val dokumentInfoId: String,
    val opprettetBruker: String,
)
