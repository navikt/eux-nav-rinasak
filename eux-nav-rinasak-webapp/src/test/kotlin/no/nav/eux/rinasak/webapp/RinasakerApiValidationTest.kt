package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.advice.MethodArgumentNotValidExceptionAdvice
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseInvalid
import org.junit.jupiter.api.Test

class RinasakerApiValidationTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - forespørsel, invalid fnr - 400`() {
        val responseBody = restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelseInvalid)
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody(MethodArgumentNotValidExceptionAdvice.ApiError::class.java)
            .returnResult().responseBody!!
        with(responseBody.errors[0]) {
            rejectedValue shouldBe "invalid"
            defaultMessage shouldBe """must match "^\d{11}$""""
        }
        with(responseBody.errors[1]) {
            rejectedValue shouldBe "invalid"
            defaultMessage shouldBe "size must be between 11 and 11"
        }
    }
}
