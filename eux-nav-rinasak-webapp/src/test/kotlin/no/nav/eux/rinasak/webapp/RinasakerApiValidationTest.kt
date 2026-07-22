package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import no.nav.eux.rinasak.advice.MethodArgumentNotValidExceptionAdvice
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.token
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseInvalid
import org.junit.jupiter.api.Test

class RinasakerApiValidationTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - ugyldig fødselsnummer - 400`() {
        val responseBody = restTestClient.post().uri(navRinasakerUrl)
            .header("Authorization", "Bearer ${mockOAuth2Server.token}")
            .body(navRinasakOpprettelseInvalid)
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody(MethodArgumentNotValidExceptionAdvice.ApiError::class.java)
            .returnResult().responseBody!!
        responseBody.errors
            .map { it.rejectedValue to it.defaultMessage }
            .shouldContainExactlyInAnyOrder(
                "invalid" to """must match "^\d{11}$"""",
                "invalid" to "size must be between 11 and 11",
            )
    }
}
