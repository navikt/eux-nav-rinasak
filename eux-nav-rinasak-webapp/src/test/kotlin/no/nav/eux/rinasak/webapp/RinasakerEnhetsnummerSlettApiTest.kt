package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerEnhetsnummerUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasak
import org.junit.jupiter.api.Test

class RinasakerEnhetsnummerSlettApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `DELETE enhetsnummer - forespørsel, sletting, finn med id - 204`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
        val navRinasak = restTestClient.get().uri("/api/v1/rinasaker/1")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectBody(NavRinasak::class.java)
            .returnResult().responseBody!!
        navRinasak.rinasakId shouldBe 1
        navRinasak.overstyrtEnhetsnummer shouldBe "1234"

        restTestClient.delete().uri(navRinasakerEnhetsnummerUrl, 1)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(204)

        val updatedNavRinasak = restTestClient.get().uri("/api/v1/rinasaker/1")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectBody(NavRinasak::class.java)
            .returnResult().responseBody!!
        updatedNavRinasak.overstyrtEnhetsnummer shouldBe null
    }
}
