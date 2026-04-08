package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasak
import org.junit.jupiter.api.Test

class RinasakerApiFinnRinasakTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `GET rinasaker - forespørsel, finn med id - 200`() {
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
        with(navRinasak.initiellFagsak!!) {
            tema shouldBe "AAA"
            system shouldBe "system"
            nr shouldBe "nr"
            type shouldBe "FAGSAK"
            opprettetBruker shouldBe "ukjent"
            fnr shouldBe "03028700001"
            arkiv shouldBe "PSAK"
        }
        with(navRinasak.dokumenter!!.single()) {
            sedId shouldBe uuid1
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000001"
            sedType shouldBe "type"
        }
    }

    @Test
    fun `GET rinasaker - forespørsel, ikke funnet - 404`() {
        restTestClient.get().uri("/api/v1/rinasaker/2")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .exchange()
            .expectStatus().isEqualTo(404)
    }

    @Test
    fun `GET rinasaker finn - ikke autentisert - 401`() {
        restTestClient.get().uri("/api/v1/rinasaker/1")
            .exchange()
            .expectStatus().isEqualTo(401)
    }
}
