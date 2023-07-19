package no.nav.eux.rinasak.model.dto

import no.nav.eux.rinasak.model.entity.Fagsak
import no.nav.eux.rinasak.model.entity.NavRinasak

data class NavRinasakFinnResponse(
    val navRinasak: NavRinasak,
    val fagsak: Fagsak?
)
