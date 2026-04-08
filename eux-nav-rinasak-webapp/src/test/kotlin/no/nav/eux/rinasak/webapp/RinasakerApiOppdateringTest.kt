package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.dataset.oppdatering.navRinasakOppdatering
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

class RinasakerApiOppdateringTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `PATCH rinasaker - gyldig forespørsel - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
        restTestClient.patch().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOppdatering)
            .exchange()
            .expectStatus().isEqualTo(201)
    }

    @Test
    fun `PATCH rinasaker - ikke autentisert - 401`() {
        restTestClient.patch().uri(navRinasakerUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{}")
            .exchange()
            .expectStatus().isEqualTo(401)
    }

    @Test
    fun `PATCH rinasaker - ugyldig request - 400`() {
        restTestClient.patch().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .contentType(MediaType.APPLICATION_JSON)
            .body(".")
            .exchange()
            .expectStatus().isEqualTo(400)
    }
}
