package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.*
import no.nav.eux.rinasak.webapp.dataset.oppdatering.initiellFagsakOppdatering
import no.nav.eux.rinasak.webapp.dataset.oppdatering.navRinasakOppdatering
import no.nav.eux.rinasak.webapp.dataset.oppdatering.tilagtDokumentSedId
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseUtenRelasjoner
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import no.nav.eux.rinasak.webapp.model.oppdatering.NavRinasakOppdatering
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.client.expectBody

class RinasakerApiOppdateringTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `PATCH rinasaker - oppdaterer alle felt - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
        restTestClient.patch().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOppdatering)
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
        navRinasak.overstyrtEnhetsnummer shouldBe "5678"
        with(navRinasak.initiellFagsak!!) {
            tema shouldBe "BBB"
            system shouldBe "oppdatertSystem"
            nr shouldBe "oppdatertNr"
            type shouldBe "endret"
            opprettetBruker shouldBe "ukjent"
            fnr shouldBe "03028700001"
        }
        val dokumentMap = navRinasak.dokumenter!!.associateBy { it.sedId to it.sedVersjon }
        with(dokumentMap[forventetSedId to 1]!!) {
            sedId shouldBe forventetSedId
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000011"
            sedType shouldBe "oppdatert"
        }
        with(dokumentMap[tilagtDokumentSedId to 1]!!) {
            sedId shouldBe tilagtDokumentSedId
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000003"
            sedType shouldBe "type"
        }
    }

    @Test
    fun `PATCH rinasaker - oppdaterer kun overstyrt enhetsnummer - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
        restTestClient.patch().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                NavRinasakOppdatering(
                    rinasakId = 1,
                    overstyrtEnhetsnummer = "5678",
                    initiellFagsak = null,
                    dokumenter = null
                )
            )
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
        navRinasak.overstyrtEnhetsnummer shouldBe "5678"
        navRinasak.initiellFagsak.shouldNotBeNull()
        navRinasak.dokumenter!!.shouldHaveSize(1)
    }

    @Test
    fun `PATCH rinasaker - legger til initiell fagsak - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelseUtenRelasjoner)
            .exchange()
            .expectStatus().isEqualTo(201)
        restTestClient.patch().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                NavRinasakOppdatering(
                    rinasakId = 1,
                    overstyrtEnhetsnummer = null,
                    initiellFagsak = initiellFagsakOppdatering,
                    dokumenter = null
                )
            )
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
        with(navRinasak.initiellFagsak!!) {
            id shouldBe "fagsak-1"
            tema shouldBe "BBB"
            system shouldBe "oppdatertSystem"
            nr shouldBe "oppdatertNr"
            type shouldBe "endret"
            fnr shouldBe "03028700001"
            arkiv shouldBe "PSAK"
        }
    }

    @Test
    fun `PATCH rinasaker - rinasak finnes ikke - 404`() {
        restTestClient.patch().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOppdatering)
            .exchange()
            .expectStatus().isEqualTo(404)
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
