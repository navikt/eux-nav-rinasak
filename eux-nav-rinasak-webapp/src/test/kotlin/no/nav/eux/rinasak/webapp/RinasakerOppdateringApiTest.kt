package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.common.uuid3
import no.nav.eux.rinasak.webapp.dataset.oppdatering.navRinasakOppdatering
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import no.nav.eux.rinasak.webapp.model.oppdatering.NavRinasakOppdatering
import org.junit.jupiter.api.Test
import org.springframework.boot.resttestclient.exchange
import org.springframework.boot.resttestclient.postForEntity
import org.springframework.boot.resttestclient.postForObject
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
        updateResponse.statusCode.value() shouldBe 201
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        navRinasak.rinasakId shouldBe 1
        navRinasak.overstyrtEnhetsnummer shouldBe "5678"
        with(navRinasak.initiellFagsak!!) {
            tema shouldBe "BBB"
            system shouldBe "oppdatertSystem"
            nr shouldBe "oppdatertNr"
            type shouldBe "endret"
            opprettetBruker shouldBe "ukjent"
            fnr shouldBe "03028700001"
        }
        val dokumentMap = navRinasak.dokumenter!!.associateBy { Pair(it.sedId, it.sedVersjon) }
        with(dokumentMap[Pair(uuid1, 1)]!!) {
            sedId shouldBe uuid1
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000011"
            sedType shouldBe "oppdatert"
        }
        with(dokumentMap[Pair(uuid3, 1)]!!) {
            sedId shouldBe uuid3
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000003"
            sedType shouldBe "type"
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
        updateResponse.statusCode.value() shouldBe 201
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        navRinasak.rinasakId shouldBe 1
        navRinasak.overstyrtEnhetsnummer shouldBe "5678"
        navRinasak.initiellFagsak.shouldNotBeNull()
        navRinasak.dokumenter!!.shouldHaveSize(1)
    }
}
