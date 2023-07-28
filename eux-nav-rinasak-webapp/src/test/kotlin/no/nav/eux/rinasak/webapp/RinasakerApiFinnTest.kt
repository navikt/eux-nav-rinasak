package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.model.NavRinasakFinnKriterier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class RinasakerApiFinnTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker finn - finn uten kriterier - 200`() {
        val response = restTemplate.postForEntity<Void>(
            navRinasakerFinnUrl,
            NavRinasakFinnKriterier().httpEntity
        )
        assertThat(response.statusCode.value()).isEqualTo(200)
    }

    @Test
    fun `POST rinasaker finn - ikke autentisert - 401`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("{}", headers)
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerFinnUrl,
            entity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(401)
    }

    @Test
    fun `POST rinasaker finn - ugyldig request - 400`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerFinnUrl,
            ".".httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(400)
    }
}
