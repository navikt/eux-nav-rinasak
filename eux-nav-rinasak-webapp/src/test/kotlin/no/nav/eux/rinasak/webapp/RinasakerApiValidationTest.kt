package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.advice.MethodArgumentNotValidExceptionAdvice
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.dataset.navRinasakOpprettelseInvalid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity

class RinasakerApiValidationTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - forespørsel, invalid fnr - 400`() {
        val createResponse = restTemplate
            .postForEntity<MethodArgumentNotValidExceptionAdvice.ApiError>(
                navRinasakerUrl,
                navRinasakOpprettelseInvalid.httpEntity
            )
        assertThat(createResponse.statusCode.value()).isEqualTo(400)
        with(createResponse.body!!.errors[0]) {
            assertThat(rejectedValue).isEqualTo("invalid")
            assertThat(defaultMessage).isEqualTo("""must match "^\d{11}$"""")
        }
        with(createResponse.body!!.errors[1]) {
            assertThat(rejectedValue).isEqualTo("invalid")
            assertThat(defaultMessage).isEqualTo("size must be between 11 and 11")
        }
    }
}
