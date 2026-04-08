package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel2
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel3
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import no.nav.eux.rinasak.webapp.model.opprettelse.FagsakOpprettelse
import no.nav.eux.rinasak.webapp.model.opprettelse.NavRinasakOpprettelse
import org.junit.jupiter.api.Test

class RinasakerApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - forespørsel, finn med id - 200`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(201)
        val navRinasak = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 1))
            .exchange()
            .expectBody(NavRinasaker::class.java)
            .returnResult().responseBody!!
            .navRinasaker
            .single()
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
    fun `POST rinasaker - forespørsel, uten fagsak og sed finn med id - 200`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakOpprettelse(initiellFagsak = null))
            .exchange()
            .expectStatus().isEqualTo(201)
        val navRinasak = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 1))
            .exchange()
            .expectBody(NavRinasaker::class.java)
            .returnResult().responseBody!!
            .navRinasaker
            .single()
        navRinasak.rinasakId shouldBe 1
        navRinasak.initiellFagsak.shouldBeNull()
    }

    @Test
    fun `POST rinasaker finn - forespørsel, ikke funnet med feil id - 200`() {
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
    fun `POST rinasaker finn - forespørsel, 3 enkle, hent ved rinasakId - 200`() {
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
    fun `PATCH rinasaker fagsak - update existing fagsak - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakOpprettelse(initiellFagsak = null))
            .exchange()
            .expectStatus().isEqualTo(201)
        val initialFagsakOpprettelse = FagsakOpprettelse(
            tema = "AAA",
            type = "FAGSAK",
            system = "system",
            nr = "nr",
            fnr = "03028700001"
        )
        restTestClient.patch().uri("$navRinasakerUrl/1/fagsak")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(initialFagsakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(201)
        val initialNavRinasak = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 1))
            .exchange()
            .expectBody(NavRinasaker::class.java)
            .returnResult().responseBody!!
            .navRinasaker
            .single()
        with(initialNavRinasak.fagsak!!) {
            tema shouldBe "AAA"
            system shouldBe "system"
            nr shouldBe "nr"
            type shouldBe "FAGSAK"
            fnr shouldBe "03028700001"
        }
        val updatedFagsakOpprettelse = FagsakOpprettelse(
            tema = "BBB",
            type = "KLAGE",
            system = "updated-system",
            nr = "updated-nr",
            fnr = "12345678901"
        )
        restTestClient.patch().uri("$navRinasakerUrl/1/fagsak")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(updatedFagsakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(201)
        val updatedNavRinasak = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 1))
            .exchange()
            .expectBody(NavRinasaker::class.java)
            .returnResult().responseBody!!
            .navRinasaker
            .single()
        with(updatedNavRinasak.fagsak!!) {
            tema shouldBe "BBB"
            type shouldBe "KLAGE"
            system shouldBe "updated-system"
            nr shouldBe "updated-nr"
            fnr shouldBe "12345678901"
        }
    }

    @Test
    fun `PATCH rinasaker fagsak - non-existent rinasak - 404`() {
        val nonExistentRinasakId = 999
        val fagsakOpprettelse = FagsakOpprettelse(
            tema = "AAA",
            type = "FAGSAK",
            system = "system",
            nr = "nr",
            fnr = "03028700001"
        )
        restTestClient.patch().uri("$navRinasakerUrl/$nonExistentRinasakId/fagsak")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(fagsakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(404)
    }
}
