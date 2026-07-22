package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel2
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel3
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

class RinasakerApiFinnTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker finn - rinasak finnes ikke - 200`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(201)
        val navRinasaker = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 0))
            .exchange()
            .expectBody(NavRinasaker::class.java)
            .returnResult().responseBody!!
            .navRinasaker
        navRinasaker.shouldBeEmpty()
    }

    @Test
    fun `POST rinasaker finn - finner kun rinasak med angitt id - 200`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelseEnkel1)
            .exchange()
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelseEnkel2)
            .exchange()
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelseEnkel3)
            .exchange()
        val navRinasak = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 2))
            .exchange()
            .expectBody(NavRinasaker::class.java)
            .returnResult().responseBody!!
            .navRinasaker
            .single()
        navRinasak.rinasakId shouldBe 2
        navRinasak.initiellFagsak.shouldBeNull()
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
