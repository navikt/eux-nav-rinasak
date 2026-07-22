package no.nav.eux.rinasak.webapp

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.common.forventetSedId
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseUtenRelasjoner
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.client.expectBody

class RinasakerApiOpprettelseTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - oppretter rinasak med fagsak og dokument - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(201)
        val navRinasak = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 1))
            .exchange()
            .expectBody<NavRinasaker>()
            .returnResult().responseBody!!
            .navRinasaker
            .single()
        navRinasak.rinasakId shouldBe 1
        navRinasak.overstyrtEnhetsnummer shouldBe "1234"
        navRinasak.opprettetBruker shouldBe "ukjent"
        with(navRinasak.initiellFagsak!!) {
            id.shouldBeNull()
            tema shouldBe "AAA"
            system shouldBe "system"
            nr shouldBe "nr"
            type shouldBe "FAGSAK"
            opprettetBruker shouldBe "ukjent"
            fnr shouldBe "03028700001"
            arkiv shouldBe "PSAK"
        }
        with(navRinasak.dokumenter!!.single()) {
            sedId shouldBe forventetSedId
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000001"
            sedType shouldBe "type"
            opprettetBruker shouldBe "ukjent"
        }
    }

    @Test
    fun `POST rinasaker - oppretter rinasak med dokument uten fagsak - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse.copy(initiellFagsak = null))
            .exchange()
            .expectStatus().isEqualTo(201)
        val navRinasak = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 1))
            .exchange()
            .expectBody<NavRinasaker>()
            .returnResult().responseBody!!
            .navRinasaker
            .single()
        navRinasak.initiellFagsak.shouldBeNull()
        with(navRinasak.dokumenter!!.single()) {
            sedId shouldBe forventetSedId
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000001"
            sedType shouldBe "type"
        }
    }

    @Test
    fun `POST rinasaker - oppretter rinasak uten fagsak og dokumenter - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelseUtenRelasjoner)
            .exchange()
            .expectStatus().isEqualTo(201)
        val navRinasak = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 1))
            .exchange()
            .expectBody<NavRinasaker>()
            .returnResult().responseBody!!
            .navRinasaker
            .single()
        navRinasak.rinasakId shouldBe 1
        navRinasak.initiellFagsak.shouldBeNull()
        navRinasak.dokumenter.shouldBeNull()
    }

    @Test
    fun `POST rinasaker - rinasak finnes allerede - 409`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(409)
    }

    @Test
    fun `POST rinasaker - ikke autentisert - 401`() {
        restTestClient.post().uri(navRinasakerUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{}")
            .exchange()
            .expectStatus().isEqualTo(401)
    }

    @Test
    fun `POST rinasaker - ugyldig request - 400`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .contentType(MediaType.APPLICATION_JSON)
            .body(".")
            .exchange()
            .expectStatus().isEqualTo(400)
    }
}
