package no.nav.eux.rinasak.webapp

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerEnhetsnummerUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasak
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.client.expectBody

class RinasakerApiEnhetsnummerSlettTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `DELETE enhetsnummer - sletter overstyrt enhetsnummer - 204`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
        val navRinasak = restTestClient.get().uri("$navRinasakerUrl/1")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectBody<NavRinasak>()
            .returnResult().responseBody!!
        navRinasak.rinasakId shouldBe 1
        navRinasak.overstyrtEnhetsnummer shouldBe "1234"

        restTestClient.delete().uri(navRinasakerEnhetsnummerUrl, 1)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(204)

        val updatedNavRinasak = restTestClient.get().uri("$navRinasakerUrl/1")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectBody<NavRinasak>()
            .returnResult().responseBody!!
        updatedNavRinasak.overstyrtEnhetsnummer.shouldBeNull()
    }

    @Test
    fun `DELETE enhetsnummer - rinasak finnes ikke - 404`() {
        restTestClient.delete().uri(navRinasakerEnhetsnummerUrl, 999)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(404)
    }

    @Test
    fun `DELETE enhetsnummer - ikke autentisert - 401`() {
        restTestClient.delete().uri(navRinasakerEnhetsnummerUrl, 1)
            .exchange()
            .expectStatus().isEqualTo(401)
    }
}
