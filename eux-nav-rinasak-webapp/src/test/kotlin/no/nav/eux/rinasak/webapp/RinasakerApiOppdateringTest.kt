package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.dataset.oppdatering.navRinasakOppdatering
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType

class RinasakerApiOppdateringTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `PATCH rinasaker - gyldig forespørsel - 201`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        val createResponse = restTemplate.exchange<Void>(
            url = navRinasakerUrl,
            method = HttpMethod.PATCH,
            requestEntity = navRinasakOppdatering.httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(201)
    }

    @Test
    fun `PATCH rinasaker - ikke autentisert - 401`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("{}", headers)
        val createResponse = restTemplate.exchange<Void>(
            url = navRinasakerUrl,
            method = HttpMethod.PATCH,
            requestEntity = entity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(401)
    }

    @Test
    fun `PATCH rinasaker - ugyldig request - 400`() {
        val createResponse = restTemplate.exchange<Void>(
            url = navRinasakerUrl,
            method = HttpMethod.PATCH,
            requestEntity =  ".".httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(400)
    }
}
