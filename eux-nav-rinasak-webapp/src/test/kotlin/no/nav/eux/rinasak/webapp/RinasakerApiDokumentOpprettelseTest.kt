package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.common.uuid4
import no.nav.eux.rinasak.webapp.dataset.oppdatering.navRinasakDokumentOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import org.junit.jupiter.api.Test
import org.springframework.boot.resttestclient.postForEntity
import org.springframework.boot.resttestclient.postForObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class RinasakerApiDokumentOpprettelseTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker dokumenter - gyldig forespørsel - 201`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        val createResponse = restTemplate.postForEntity<Void>(
            "$navRinasakerUrl/1/dokumenter",
            navRinasakDokumentOpprettelse.httpEntity
        )
        createResponse.statusCode.value() shouldBe 201
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        val dokumentMap = navRinasak.dokumenter!!.associateBy { Pair(it.sedId, it.sedVersjon) }
        with(dokumentMap[Pair(uuid1, 1)]!!) {
            sedId shouldBe uuid1
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000001"
            sedType shouldBe "type"
        }
        with(dokumentMap[Pair(uuid4, 1)]!!) {
            sedId shouldBe uuid4
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000111"
            sedType shouldBe "type"
        }
    }

    @Test
    fun `POST rinasaker dokumenter - ikke autentisert - 401`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("{}", headers)
        val createResponse = restTemplate.postForEntity<Void>(
            "$navRinasakerUrl/1/dokumenter",
            entity
        )
        createResponse.statusCode.value() shouldBe 401
    }

    @Test
    fun `POST rinasaker dokumenter - ugyldig request - 400`() {
        val createResponse = restTemplate.postForEntity<Void>(
            "$navRinasakerUrl/1/dokumenter",
            ".".httpEntity
        )
        createResponse.statusCode.value() shouldBe 400
    }
}
