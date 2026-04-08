package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserFinnUrl
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusFinnKriterierRinasakIdTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusFinnKriterierTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusPutTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatuserTestModel
import org.junit.jupiter.api.Test

class SedJournalstatusApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `PUT sed journalstatuser - forespørsel, finn med id - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = uuid1,
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
                    sedId = uuid1,
                    sedVersjon = 1
                )
            )
            .exchange()
            .expectBody(SedJournalstatuserTestModel::class.java)
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.rinasakId shouldBe 1
        sedJournalstatus.sedId shouldBe uuid1
        sedJournalstatus.sedVersjon shouldBe 1
        sedJournalstatus.sedJournalstatus shouldBe "UKJENT"
    }

    @Test
    fun `PUT sed journalstatuser - forespørsel, finn med status - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = uuid1,
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
            .expectBody(SedJournalstatuserTestModel::class.java)
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.sedId shouldBe uuid1
        sedJournalstatus.sedVersjon shouldBe 1
        sedJournalstatus.sedJournalstatus shouldBe "UKJENT"
    }


    @Test
    fun `PUT sed journalstatuser - forespørsel, finn med rinasakId - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 3,
                    sedId = uuid1,
                    sedVersjon = 0,
                    sedJournalstatus = "MELOSYS_JOURNALFOERER"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(200)
        val sedJournalstatus = restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusFinnKriterierRinasakIdTestModel(
                    rinasakId = 3
                )
            )
            .exchange()
            .expectBody(SedJournalstatuserTestModel::class.java)
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        with(sedJournalstatus) {
            rinasakId shouldBe 3
            sedId shouldBe uuid1
            sedVersjon shouldBe 0
            sedJournalstatus.sedJournalstatus shouldBe "MELOSYS_JOURNALFOERER"
        }
    }

    @Test
    fun `PUT sed journalstatuser - forespørsel, ikke funnet pga annen status - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = uuid1,
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
            .expectBody(SedJournalstatuserTestModel::class.java)
            .returnResult().responseBody!!
            .sedJournalstatuser
        sedJournalstatus.shouldBeEmpty()
    }

    @Test
    fun `POST sed journalstatuser finn - forespørsel, finn uten argumenter - 400`() {
        restTestClient.post().uri(sedJournalstatuserFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(SedJournalstatusFinnKriterierTestModel())
            .exchange()
            .expectStatus().isEqualTo(400)
    }

    @Test
    fun `PUT sed journalstatuser med feilmelding - forespørsel, finn med id - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = uuid1,
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
                    sedId = uuid1,
                    sedVersjon = 1
                )
            )
            .exchange()
            .expectBody(SedJournalstatuserTestModel::class.java)
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.rinasakId shouldBe 1
        sedJournalstatus.sedId shouldBe uuid1
        sedJournalstatus.sedVersjon shouldBe 1
        sedJournalstatus.sedJournalstatus shouldBe "FEILET_FERDIGSTILL"
        sedJournalstatus.feilmelding shouldBe "Ferdigstilling feilet: 500 Internal Server Error"
    }

    @Test
    fun `PUT sed journalstatuser uten feilmelding - feilmelding er null - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = uuid1,
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
                    sedId = uuid1,
                    sedVersjon = 1
                )
            )
            .exchange()
            .expectBody(SedJournalstatuserTestModel::class.java)
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.feilmelding shouldBe null
    }

    @Test
    fun `PUT sed journalstatuser - oppdater eksisterende med feilmelding - 200`() {
        restTestClient.put().uri(sedJournalstatuserUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                SedJournalstatusPutTestModel(
                    rinasakId = 1,
                    sedId = uuid1,
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
                    sedId = uuid1,
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
                    sedId = uuid1,
                    sedVersjon = 1
                )
            )
            .exchange()
            .expectBody(SedJournalstatuserTestModel::class.java)
            .returnResult().responseBody!!
            .sedJournalstatuser
            .single()
        sedJournalstatus.sedJournalstatus shouldBe "FEILET_FERDIGSTILL"
        sedJournalstatus.feilmelding shouldBe "Ferdigstilling feilet: 500 Internal Server Error"
    }
}
