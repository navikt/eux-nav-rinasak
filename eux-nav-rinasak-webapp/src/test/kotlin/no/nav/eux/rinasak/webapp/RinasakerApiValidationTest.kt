package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.advice.MethodArgumentNotValidExceptionAdvice
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseInvalid
import org.junit.jupiter.api.Test
import org.springframework.boot.resttestclient.postForEntity

class RinasakerApiValidationTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - forespørsel, invalid fnr - 400`() {
        val createResponse = restTemplate
            .postForEntity<MethodArgumentNotValidExceptionAdvice.ApiError>(
                navRinasakerUrl,
                navRinasakOpprettelseInvalid.httpEntity
            )
        createResponse.statusCode.value() shouldBe 400
        with(createResponse.body!!.errors[0]) {
            rejectedValue shouldBe "invalid"
            defaultMessage shouldBe """must match "^\d{11}$""""
        }
        with(createResponse.body!!.errors[1]) {
            rejectedValue shouldBe "invalid"
            defaultMessage shouldBe "size must be between 11 and 11"
        }
    }
}
