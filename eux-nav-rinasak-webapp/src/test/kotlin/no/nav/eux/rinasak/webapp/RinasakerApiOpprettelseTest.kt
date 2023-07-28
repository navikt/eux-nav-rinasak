package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.model.NavRinasakOpprettelse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class RinasakerApiOpprettelseTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - gyldig forespørsel - 201`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse().httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(201)
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
        assertThat(createResponse.statusCode.value()).isEqualTo(401)
    }

    @Test
    fun `POST rinasaker - ugyldig request - 400`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            ".".httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(400)
    }
}
