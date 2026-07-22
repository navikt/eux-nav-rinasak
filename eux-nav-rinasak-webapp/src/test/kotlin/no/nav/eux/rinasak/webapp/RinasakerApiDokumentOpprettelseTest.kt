package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.*
import no.nav.eux.rinasak.webapp.dataset.opprettelse.nyttDokumentOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.nyttDokumentSedId
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.client.expectBody

class RinasakerApiDokumentOpprettelseTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker dokumenter - oppretter dokument - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
        restTestClient.post().uri("$navRinasakerUrl/1/dokumenter")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(nyttDokumentOpprettelse)
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
        val dokumentMap = navRinasak.dokumenter!!.associateBy { it.sedId to it.sedVersjon }
        with(dokumentMap[forventetSedId to 1]!!) {
            sedId shouldBe forventetSedId
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000001"
            sedType shouldBe "type"
        }
        with(dokumentMap[nyttDokumentSedId to 1]!!) {
            sedId shouldBe nyttDokumentSedId
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000111"
            sedType shouldBe "type"
        }
    }

    @Test
    fun `POST rinasaker dokumenter - samme sedId med ny versjon - 201`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(201)
        restTestClient.post().uri("$navRinasakerUrl/1/dokumenter")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(
                nyttDokumentOpprettelse.copy(
                    sedId = forventetSedId,
                    sedVersjon = 2,
                    dokumentInfoId = "000000112"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(201)
        val dokumenter = restTestClient.post().uri(navRinasakerFinnUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(NavRinasakFinnKriterier(rinasakId = 1))
            .exchange()
            .expectBody<NavRinasaker>()
            .returnResult().responseBody!!
            .navRinasaker
            .single()
            .dokumenter!!
        dokumenter
            .map { it.sedId to it.sedVersjon }
            .shouldContainExactlyInAnyOrder(listOf(forventetSedId to 1, forventetSedId to 2))
    }

    @Test
    fun `POST rinasaker dokumenter - dokument finnes allerede - 409`() {
        restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelse)
            .exchange()
        restTestClient.post().uri("$navRinasakerUrl/1/dokumenter")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(nyttDokumentOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(201)
        restTestClient.post().uri("$navRinasakerUrl/1/dokumenter")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(nyttDokumentOpprettelse)
            .exchange()
            .expectStatus().isEqualTo(409)
    }

    @Test
    fun `POST rinasaker dokumenter - ikke autentisert - 401`() {
        restTestClient.post().uri("$navRinasakerUrl/1/dokumenter")
            .contentType(MediaType.APPLICATION_JSON)
            .body("{}")
            .exchange()
            .expectStatus().isEqualTo(401)
    }

    @Test
    fun `POST rinasaker dokumenter - ugyldig request - 400`() {
        restTestClient.post().uri("$navRinasakerUrl/1/dokumenter")
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .contentType(MediaType.APPLICATION_JSON)
            .body(".")
            .exchange()
            .expectStatus().isEqualTo(400)
    }
}
