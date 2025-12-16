package no.nav.eux.rinasak.webapp

import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerEnhetsnummerUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasak
import org.junit.jupiter.api.Test
import org.springframework.boot.resttestclient.postForEntity
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET

class RinasakerEnhetsnummerSlettApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `DELETE enhetsnummer - forespørsel, sletting, finn med id - 204`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        val entity = restTemplate.exchange(
            "/api/v1/rinasaker/1",
            GET,
            httpEntity(),
            NavRinasak::class.java
        )
        entity.statusCode.value() shouldBe 200
        val navRinasak = entity.body!!
        navRinasak.rinasakId shouldBe 1
        navRinasak.overstyrtEnhetsnummer shouldBe "1234"

        val responseEntity = restTemplate.exchange(
            navRinasakerEnhetsnummerUrl,
            DELETE,
            httpEntity(),
            Void::class.java,
            1
        )
        responseEntity.statusCode.value() shouldBe 204

        val updatedEntity = restTemplate.exchange(
            "/api/v1/rinasaker/1",
            GET,
            httpEntity(),
            NavRinasak::class.java
        )
        val updatedNavRinasak = updatedEntity.body!!
        updatedNavRinasak.overstyrtEnhetsnummer shouldBe null
    }
}
