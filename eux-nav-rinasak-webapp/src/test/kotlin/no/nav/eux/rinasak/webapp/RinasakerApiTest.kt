package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel2
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel3
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import no.nav.eux.rinasak.webapp.model.opprettelse.NavRinasakOpprettelse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject

class RinasakerApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - forespørsel, finn med id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
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
            assertThat(type).isEqualTo("FAGSAK")
            assertThat(opprettetBruker).isEqualTo("ukjent")
            assertThat(fnr).isEqualTo("03028700001")
            assertThat(arkiv).isEqualTo("PSAK")
        }
        with(navRinasak.dokumenter!!.single()) {
            assertThat(sedId).isEqualTo(uuid1)
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
            navRinasakOpprettelse.httpEntity
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
