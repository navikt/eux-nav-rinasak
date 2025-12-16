package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import org.junit.jupiter.api.Test
import org.springframework.boot.resttestclient.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType

class RinasakerApiOpprettelseTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - gyldig forespørsel - 201`() {
            val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        createResponse.statusCode shouldBe CREATED
    }

    @Test
    fun `POST rinasaker - finnes alt, conflict - 409`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        createResponse.statusCode.value() shouldBe 409
    }

    @Test
    fun `POST rinasaker - ikke autentisert - 401`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("{}", headers)
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            entity
        )
        createResponse.statusCode.value() shouldBe 401
    }

    @Test
    fun `POST rinasaker - ugyldig request - 400`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            ".".httpEntity
        )
        createResponse.statusCode.value() shouldBe 400
    }
}
