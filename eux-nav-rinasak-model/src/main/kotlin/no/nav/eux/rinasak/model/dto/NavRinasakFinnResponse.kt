package no.nav.eux.rinasak.model.dto

import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.model.entity.InitiellFagsak
import no.nav.eux.rinasak.model.entity.NavRinasak

data class NavRinasakFinnResponse(
    val navRinasak: NavRinasak,
    val initiellFagsak: InitiellFagsak?,
    val dokumenter: List<Dokument>?,
)
