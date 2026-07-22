package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserFinnUrl
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.common.forventetSedId
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusFinnKriterierTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusPutTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatuserTestModel
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.client.expectBody

class SedJournalstatusApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `PUT sed journalstatuser - oppretter og finner med sedId og versjon - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 1,
                    sedJournalstatus = "UKJENT"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        val sedJournalstatus = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusFinnKriterierTestModel(
                    sedId = forventetSedId,
                    sedVersjon = 1
                )
            )
            .exchange()
            .expectBody<SedJournalstatuserTestModel>()
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.rinasakId shouldBe 1
        sedJournalstatus.sedId shouldBe forventetSedId
        sedJournalstatus.sedVersjon shouldBe 1
        sedJournalstatus.sedJournalstatus shouldBe "UKJENT"
        sedJournalstatus.opprettetBruker shouldBe "ukjent"
        sedJournalstatus.endretBruker shouldBe "ukjent"
    }

    @Test
    fun `PUT sed journalstatuser - finner med status - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 1,
                    sedJournalstatus = "UKJENT"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        val sedJournalstatus = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusFinnKriterierTestModel(
                    sedJournalstatus = "UKJENT"
                )
            )
            .exchange()
            .expectBody<SedJournalstatuserTestModel>()
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.sedId shouldBe forventetSedId
        sedJournalstatus.sedVersjon shouldBe 1
        sedJournalstatus.sedJournalstatus shouldBe "UKJENT"
    }

    @Test
    fun `PUT sed journalstatuser - finner med rinasakId - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 3,
                    sedId = forventetSedId,
                    sedVersjon = 0,
                    sedJournalstatus = "MELOSYS_JOURNALFOERER"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        val sedJournalstatus = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusFinnKriterierTestModel(
                    rinasakId = 3
                )
            )
            .exchange()
            .expectBody<SedJournalstatuserTestModel>()
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        with(sedJournalstatus) {
            rinasakId shouldBe 3
            sedId shouldBe forventetSedId
            sedVersjon shouldBe 0
            sedJournalstatus.sedJournalstatus shouldBe "MELOSYS_JOURNALFOERER"
        }
    }

    @Test
    fun `PUT sed journalstatuser - annen status gir tomt resultat - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 1,
                    sedJournalstatus = "UKJENT"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        val sedJournalstatus = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusFinnKriterierTestModel(
                    sedJournalstatus = "JOURNALFOERT"
                )
            )
            .exchange()
            .expectBody<SedJournalstatuserTestModel>()
            .returnResult().responseBody!!
            .sedJournalstatuser
        sedJournalstatus.shouldBeEmpty()
    }

    @Test
    fun `POST sed journalstatuser finn - mangler søkekriterier - 400`() {
        restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(SedJournalstatusFinnKriterierTestModel())
            .exchange()
            .expectStatus().isEqualTo(400)
    }

    @Test
    fun `PUT sed journalstatuser - lagrer feilmelding - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 1,
                    sedJournalstatus = "FEILET_FERDIGSTILL",
                    feilmelding = "Ferdigstilling feilet: 500 Internal Server Error"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        val sedJournalstatus = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusFinnKriterierTestModel(
                    sedId = forventetSedId,
                    sedVersjon = 1
                )
            )
            .exchange()
            .expectBody<SedJournalstatuserTestModel>()
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.rinasakId shouldBe 1
        sedJournalstatus.sedId shouldBe forventetSedId
        sedJournalstatus.sedVersjon shouldBe 1
        sedJournalstatus.sedJournalstatus shouldBe "FEILET_FERDIGSTILL"
        sedJournalstatus.feilmelding shouldBe "Ferdigstilling feilet: 500 Internal Server Error"
    }

    @Test
    fun `PUT sed journalstatuser - uten feilmelding - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 1,
                    sedJournalstatus = "UKJENT"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        val sedJournalstatus = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusFinnKriterierTestModel(
                    sedId = forventetSedId,
                    sedVersjon = 1
                )
            )
            .exchange()
            .expectBody<SedJournalstatuserTestModel>()
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.feilmelding shouldBe null
    }

    @Test
    fun `PUT sed journalstatuser - oppdaterer eksisterende status og feilmelding - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 1,
                    sedJournalstatus = "UKJENT"
                )
            )
            .exchange()
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 1,
                    sedJournalstatus = "FEILET_FERDIGSTILL",
                    feilmelding = "Ferdigstilling feilet: 500 Internal Server Error"
                )
            )
            .exchange()
        val sedJournalstatus = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusFinnKriterierTestModel(
                    sedId = forventetSedId,
                    sedVersjon = 1
                )
            )
            .exchange()
            .expectBody<SedJournalstatuserTestModel>()
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.sedJournalstatus shouldBe "FEILET_FERDIGSTILL"
        sedJournalstatus.feilmelding shouldBe "Ferdigstilling feilet: 500 Internal Server Error"
    }

    @Test
    fun `PUT sed journalstatuser - samme sedId med ulike versjoner - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 1,
                    sedJournalstatus = "UKJENT"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = forventetSedId,
                    sedVersjon = 2,
                    sedJournalstatus = "JOURNALFOERT"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        val sedJournalstatuser = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(SedJournalstatusFinnKriterierTestModel(rinasakId = 1))
            .exchange()
            .expectBody<SedJournalstatuserTestModel>()
            .returnResult().responseBody!!
            .sedJournalstatuser
        sedJournalstatuser
            .map { it.sedVersjon to it.sedJournalstatus }
            .shouldContainExactlyInAnyOrder(listOf(1 to "UKJENT", 2 to "JOURNALFOERT"))
    }

    @Test
    fun `PUT sed journalstatuser - ikke autentisert - 401`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{}")
            .exchange()
            .expectStatus().isEqualTo(401)
    }

    @Test
    fun `PUT sed journalstatuser - ugyldig request - 400`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .contentType(MediaType.APPLICATION_JSON)
            .body(".")
            .exchange()
            .expectStatus().isEqualTo(400)
    }

    @Test
    fun `POST sed journalstatuser finn - ikke autentisert - 401`() {
        restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .body("{}")
            .exchange()
            .expectStatus().isEqualTo(401)
    }
}
