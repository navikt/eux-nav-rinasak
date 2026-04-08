package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

class RinasakerApiFinnTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker finn - finn uten kriterier - 200`() {
        restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier())
            .exchange()
            .expectStatus().isEqualTo(200)
    }

    @Test
    fun `POST rinasaker finn - ikke autentisert - 401`() {
        restTestClient.post().uri(navRinasakerFinnUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{}")
            .exchange()
            .expectStatus().isEqualTo(401)
    }

    @Test
    fun `POST rinasaker finn - ugyldig request - 400`() {
        restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .contentType(MediaType.APPLICATION_JSON)
            .body(".")
            .exchange()
            .expectStatus().isEqualTo(400)
    }
}
