package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.dataset.navRinasakOpprettelseEnkel1
import no.nav.eux.rinasak.webapp.dataset.navRinasakOpprettelseEnkel2
import no.nav.eux.rinasak.webapp.dataset.navRinasakOpprettelseEnkel3
import no.nav.eux.rinasak.webapp.model.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.NavRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.NavRinasaker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject
import java.util.*

class RinasakerApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - forespørsel, finn med id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse().httpEntity
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
        assertThat(navRinasak.overstyrtEnhetsnummer).isEqualTo("1234")
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
            assertThat(dokumentInfoId).isEqualTo("000000001")
            assertThat(sedType).isEqualTo("type")
        }
    }

    @Test
    fun `POST rinasaker - forespørsel, uten fagsak og sed finn med id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse(initiellFagsak = null).httpEntity
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
        assertThat(navRinasak.initiellFagsak).isNull()
    }

    @Test
    fun `POST rinasaker finn - forespørsel, ikke funnet med feil id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse().httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(201)
        val navRinasaker = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 0).httpEntity
            )!!
            .navRinasaker
        assertThat(navRinasaker).isEmpty()
    }

    @Test
    fun `POST rinasaker finn - forespørsel, 3 enkle, hent ved rinasakId - 200`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelseEnkel1.httpEntity
        )
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelseEnkel2.httpEntity
        )
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelseEnkel3.httpEntity
        )
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 2).httpEntity
            )!!
            .navRinasaker
            .single()
        assertThat(navRinasak.rinasakId).isEqualTo(2)
        assertThat(navRinasak.initiellFagsak).isNull()
    }
}
