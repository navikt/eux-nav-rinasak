package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.common.uuid3
import no.nav.eux.rinasak.webapp.dataset.oppdatering.navRinasakOppdatering
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import no.nav.eux.rinasak.webapp.model.oppdatering.NavRinasakOppdatering
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpMethod

class RinasakerOppdateringApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `PATCH rinasaker - forespørsel, oppdatering, finn med id - 200`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        val updateResponse = restTemplate.exchange<Void>(
            url = navRinasakerUrl,
            method = HttpMethod.PATCH,
            requestEntity = navRinasakOppdatering
                .httpEntity
        )
        assertThat(updateResponse.statusCode.value()).isEqualTo(201)
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        assertThat(navRinasak.rinasakId).isEqualTo(1)
        assertThat(navRinasak.overstyrtEnhetsnummer).isEqualTo("5678")
        with(navRinasak.initiellFagsak!!) {
            assertThat(tema).isEqualTo("BBB")
            assertThat(system).isEqualTo("oppdatertSystem")
            assertThat(nr).isEqualTo("oppdatertNr")
            assertThat(type).isEqualTo("endret")
            assertThat(opprettetBruker).isEqualTo("ukjent")
            assertThat(fnr).isEqualTo("03028700001")
        }
        val dokumentMap = navRinasak.dokumenter!!.associateBy { Pair(it.sedId, it.sedVersjon) }
        with(dokumentMap[Pair(uuid1, 1)]!!) {
            assertThat(sedId).isEqualTo(uuid1)
            assertThat(sedVersjon).isEqualTo(1)
            assertThat(dokumentInfoId).isEqualTo("000000011")
            assertThat(sedType).isEqualTo("oppdatert")
        }
        with(dokumentMap[Pair(uuid3, 1)]!!) {
            assertThat(sedId).isEqualTo(uuid3)
            assertThat(sedVersjon).isEqualTo(1)
            assertThat(dokumentInfoId).isEqualTo("000000003")
            assertThat(sedType).isEqualTo("type")
        }
    }

    @Test
    fun `PATCH rinasaker - forespørsel, oppdatering kun enhetsnummer, finn med id - 200`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        val updateResponse = restTemplate.exchange<Void>(
            url = navRinasakerUrl,
            method = HttpMethod.PATCH,
            requestEntity = NavRinasakOppdatering(
                rinasakId = 1,
                overstyrtEnhetsnummer = "5678",
                initiellFagsak = null,
                dokumenter = null
            )
                .httpEntity
        )
        assertThat(updateResponse.statusCode.value()).isEqualTo(201)
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        assertThat(navRinasak.rinasakId).isEqualTo(1)
        assertThat(navRinasak.overstyrtEnhetsnummer).isEqualTo("5678")
        assertThat(navRinasak.initiellFagsak).isNotNull
        assertThat(navRinasak.dokumenter!!).hasSize(1)
    }
}
