package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.model.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.NavRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.NavRinasaker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject

class RinasakerApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST relaterterinasaker - forespørsel, finn med id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse().httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(201)
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = "rinasakId-1").httpEntity
            )!!
            .navRinasaker
            .single()
        assertThat(navRinasak.rinasakId).isEqualTo("rinasakId-1")
        with (navRinasak.fagsak!!) {
            assertThat(tema).isEqualTo("tema")
            assertThat(system).isEqualTo("system")
            assertThat(nr).isEqualTo("nr")
            assertThat(type).isEqualTo("type")
            assertThat(opprettetBruker).isEqualTo("fagsak-bruker")
        }
    }

    @Test
    fun `POST relaterterinasaker - forespørsel, uten fagsak og sed finn med id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse(fagsak = null).httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(201)
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = "rinasakId-1").httpEntity
            )!!
            .navRinasaker
            .single()
        assertThat(navRinasak.rinasakId).isEqualTo("rinasakId-1")
        assertThat(navRinasak.fagsak).isNull()
    }

    @Test
    fun `POST relaterterinasaker finn - forespørsel, ikke funnet med feil id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse().httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(201)
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = "finnes ikke").httpEntity
            )!!
            .navRinasaker
            .single()
        assertThat(navRinasak.rinasakId).isEqualTo("rinasakId-1")
        assertThat(navRinasak.fagsak!!.id).isEqualTo("fagsak-1")
    }
}
