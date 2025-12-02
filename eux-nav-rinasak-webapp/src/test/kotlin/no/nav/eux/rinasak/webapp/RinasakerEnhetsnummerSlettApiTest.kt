package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerEnhetsnummerUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.common.uuid3
import no.nav.eux.rinasak.webapp.dataset.oppdatering.navRinasakOppdatering
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasak
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import no.nav.eux.rinasak.webapp.model.oppdatering.NavRinasakOppdatering
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.DELETE

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
        val updatedNavRinasak = entity.body!!
        updatedNavRinasak.overstyrtEnhetsnummer shouldBe null
    }
}
