package no.nav.eux.rinasak.service

import org.slf4j.MDC
import java.util.*

fun <T> T.mdc(
    rinasakId: Int? = null,
    sedId: UUID? = null,
    sedVersjon: Int? = null,
    dokumentInfoId: String? = null,
    journalpostId: String? = null,
): T {
    "rinasakId" leggTil rinasakId
    "sedId" leggTil sedId
    "sedVersjon" leggTil sedVersjon
    "dokumentInfoId" leggTil dokumentInfoId
    "journalpostId" leggTil journalpostId
    return this
}

private infix fun String.leggTil(value: Any?) {
    if (value != null) MDC.put(this, "$value")
}
