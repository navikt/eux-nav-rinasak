package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.model.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.NavRinasakOppdatering
import no.nav.eux.rinasak.webapp.model.NavRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.NavRinasaker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpMethod
import java.util.*

class RinasakerOppdateringApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - forespørsel, oppdatering, finn med id - 200`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse().httpEntity
        )
        val createResponse = restTemplate.exchange<Void>(
            url = navRinasakerUrl,
            method = HttpMethod.PATCH,
            requestEntity = NavRinasakOppdatering()
                .copy(
                    overstyrtEnhetsnummer = "5678"
                )
                .httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(201)
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        assertThat(navRinasak.rinasakId).isEqualTo(1)
        assertThat(navRinasak.overstyrtEnhetsnummer).isEqualTo("5678")
        with(navRinasak.initiellFagsak!!) {
            assertThat(tema).isEqualTo("AAA")
            assertThat(system).isEqualTo("system")
            assertThat(nr).isEqualTo("nr")
            assertThat(type).isEqualTo("type")
            assertThat(opprettetBruker).isEqualTo("fagsak-bruker")
            assertThat(fnr).isEqualTo("03028700000")
        }
        with(navRinasak.dokumenter!!.single()) {
            assertThat(sedId).isEqualTo(UUID.fromString("164a85f4-a031-48e3-a349-53f516005b67"))
            assertThat(sedVersjon).isEqualTo(1)
            assertThat(dokumentInfoId).isEqualTo("123456789")
            assertThat(sedType).isEqualTo("type")
        }
    }
}
