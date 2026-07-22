package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel1
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import no.nav.eux.rinasak.webapp.model.opprettelse.FagsakOpprettelse
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

class RinasakerApiFagsakOppdateringTest : AbstractRinasakerApiImplTest() {

    private val fagsakOpprettelse = FagsakOpprettelse(
        tema = "AAA",
        type = "FAGSAK",
        system = "system",
        nr = "nr",
        fnr = "03028700001"
    )

    @Test
    fun `PATCH rinasaker fagsak - oppretter og oppdaterer fagsak - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelseEnkel1)
            .exchange()
            .expectStatus().isEqualTo(201)
        restTestClient.patch().uri("$navRinasakerUrl/1/fagsak")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(fagsakOpprettelse)
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
        val opprinneligOpprettetTidspunkt = initialNavRinasak.fagsak!!.opprettetTidspunkt
        with(initialNavRinasak.fagsak!!) {
            tema shouldBe "AAA"
            system shouldBe "system"
            nr shouldBe "nr"
            type shouldBe "FAGSAK"
            fnr shouldBe "03028700001"
            opprettetBruker shouldBe "ukjent"
        }
        val oppdatertFagsak = fagsakOpprettelse.copy(
            tema = "BBB",
            type = "KLAGE",
            system = "updated-system",
            nr = "updated-nr",
            fnr = "12345678901"
        )
        restTestClient.patch().uri("$navRinasakerUrl/1/fagsak")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(oppdatertFagsak)
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
            opprettetBruker shouldBe "ukjent"
            opprettetTidspunkt shouldBe opprinneligOpprettetTidspunkt
        }
    }

    @Test
    fun `PATCH rinasaker fagsak - rinasak finnes ikke - 404`() {
        restTestClient.patch().uri("$navRinasakerUrl/999/fagsak")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(fagsakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(404)
    }

    @Test
    fun `PATCH rinasaker fagsak - ikke autentisert - 401`() {
        restTestClient.patch().uri("$navRinasakerUrl/1/fagsak")
            .contentType(MediaType.APPLICATION_JSON)
            .body("{}")
            .exchange()
            .expectStatus().isEqualTo(401)
    }

    @Test
    fun `PATCH rinasaker fagsak - ugyldig request - 400`() {
        restTestClient.patch().uri("$navRinasakerUrl/1/fagsak")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .contentType(MediaType.APPLICATION_JSON)
            .body(".")
            .exchange()
            .expectStatus().isEqualTo(400)
    }
}
