package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
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
        response.statusCode.value() shouldBe 200
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
        createResponse.statusCode.value() shouldBe 401
    }

    @Test
    fun `POST rinasaker finn - ugyldig request - 400`() {
        val finnResponse = restTemplate.postForEntity<Void>(
            navRinasakerFinnUrl,
            ".".httpEntity
        )
        finnResponse.statusCode.value() shouldBe 400
    }
}
