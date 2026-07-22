package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerNyesteUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel2
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel3
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

class RinasakerApiNyesteTest : AbstractRinasakerApiImplTest() {

    private fun opprett(body: Any) {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(body)
            .exchange()
            .expectStatus().isEqualTo(201)
    }

    private fun hentNyeste(uri: String = navRinasakerNyesteUrl) =
        restTestClient.get().uri(uri)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectBody(NavRinasaker::class.java)
            .returnResult().responseBody!!
            .navRinasaker

    @Test
    fun `GET rinasaker nyeste - sortert nyeste først - 200`() {
        opprett(navRinasakOpprettelse)
        opprett(navRinasakOpprettelseEnkel2)
        opprett(navRinasakOpprettelseEnkel3)
        val navRinasaker = hentNyeste()
        navRinasaker shouldHaveSize 3
        navRinasaker.map { it.rinasakId } shouldContainExactly listOf(3, 2, 1)
        with(navRinasaker.single { it.rinasakId == 1 }.dokumenter!!.single()) {
            sedId shouldBe uuid1
            sedType shouldBe "type"
        }
    }

    @Test
    fun `GET rinasaker nyeste - antall begrenser resultatet - 200`() {
        opprett(navRinasakOpprettelse)
        opprett(navRinasakOpprettelseEnkel2)
        opprett(navRinasakOpprettelseEnkel3)
        val navRinasaker = hentNyeste("$navRinasakerNyesteUrl?antall=2")
        navRinasaker.map { it.rinasakId } shouldContainExactly listOf(3, 2)
    }

    @Test
    fun `GET rinasaker nyeste - tom database - 200`() {
        hentNyeste() shouldHaveSize 0
    }

    @Test
    fun `GET rinasaker nyeste - ikke autentisert - 401`() {
        restTestClient.get().uri(navRinasakerNyesteUrl)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(401)
    }

    @Test
    fun `GET rinasaker nyeste - ugyldig antall - 400`() {
        restTestClient.get().uri("$navRinasakerNyesteUrl?antall=0")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(400)
    }
}
