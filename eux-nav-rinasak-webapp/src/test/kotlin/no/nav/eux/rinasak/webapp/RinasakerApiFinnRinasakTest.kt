package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasak
import org.junit.jupiter.api.Test
import org.springframework.boot.resttestclient.exchange
import org.springframework.boot.resttestclient.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType

class RinasakerApiFinnRinasakTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `GET rinasaker - forespørsel, finn med id - 200`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        val entity = restTemplate.exchange(
            "/api/v1/rinasaker/1",
            GET,
            httpEntity(),
            NavRinasak::class.java
        )
        entity.statusCode.value() shouldBe 200
        val navRinasak = entity.body!!
        navRinasak.rinasakId shouldBe 1
        navRinasak.overstyrtEnhetsnummer shouldBe "1234"
        with(navRinasak.initiellFagsak!!) {
            tema shouldBe "AAA"
            system shouldBe "system"
            nr shouldBe "nr"
            type shouldBe "FAGSAK"
            opprettetBruker shouldBe "ukjent"
            fnr shouldBe "03028700001"
            arkiv shouldBe "PSAK"
        }
        with(navRinasak.dokumenter!!.single()) {
            sedId shouldBe uuid1
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000001"
            sedType shouldBe "type"
        }
    }

    @Test
    fun `GET rinasaker - forespørsel, ikke funnet - 404`() {
        val entity = restTemplate.exchange<Void>(
            url = "/api/v1/rinasaker/2",
            method = GET,
            requestEntity = httpEntity()
        )
        entity.statusCode.value() shouldBe 404
    }

    @Test
    fun `GET rinasaker finn - ikke autentisert - 401`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = restTemplate.exchange<Void>(
            url = "/api/v1/rinasaker/1",
            method = GET,
            requestEntity = HttpEntity<Void>(headers)
        )
        entity.statusCode.value() shouldBe 401
    }
}
