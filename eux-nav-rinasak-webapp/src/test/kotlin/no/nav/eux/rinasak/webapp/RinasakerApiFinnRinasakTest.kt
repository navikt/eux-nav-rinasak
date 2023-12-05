package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasak
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType

class RinasakerApiFinnRinasakTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `GET rinasaker - forespørsel, finn med id - 200`() {
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
        assertThat(entity.statusCode.value()).isEqualTo(200)
        val navRinasak = entity.body!!
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
    fun `GET rinasaker - forespørsel, ikke funnet - 404`() {
        val entity = restTemplate.exchange<Void>(
            url = "/api/v1/rinasaker/2",
            method = GET,
            requestEntity = httpEntity()
        )
        assertThat(entity.statusCode.value()).isEqualTo(404)
    }

    @Test
    fun `GET rinasaker finn - ikke autentisert - 401`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = restTemplate.exchange<Void>(
            url = "/api/v1/rinasaker/1",
            method = GET,
            requestEntity = HttpEntity<Void>(headers)
        )
        assertThat(entity.statusCode.value()).isEqualTo(401)
    }
}
