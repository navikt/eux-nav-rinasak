package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.common.uuid4
import no.nav.eux.rinasak.webapp.dataset.navRinasakDokumentOpprettelse
import no.nav.eux.rinasak.webapp.model.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.NavRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.NavRinasaker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class RinasakerApiDokumentOpprettelseTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker dokumenter - gyldig forespørsel - 201`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse().httpEntity
        )
        val createResponse = restTemplate.postForEntity<Void>(
            "$navRinasakerUrl/1/dokumenter",
            navRinasakDokumentOpprettelse.httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(201)
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        val dokumentMap = navRinasak.dokumenter!!.associateBy { Pair(it.sedId, it.sedVersjon) }
        with(dokumentMap[Pair(uuid1, 1)]!!) {
            assertThat(sedId).isEqualTo(uuid1)
            assertThat(sedVersjon).isEqualTo(1)
            assertThat(dokumentInfoId).isEqualTo("000000001")
            assertThat(sedType).isEqualTo("type")
        }
        with(dokumentMap[Pair(uuid4, 1)]!!) {
            assertThat(sedId).isEqualTo(uuid4)
            assertThat(sedVersjon).isEqualTo(1)
            assertThat(dokumentInfoId).isEqualTo("000000111")
            assertThat(sedType).isEqualTo("type")
        }
    }

    @Test
    fun `POST rinasaker dokumenter - ikke autentisert - 401`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("{}", headers)
        val createResponse = restTemplate.postForEntity<Void>(
            "$navRinasakerUrl/1/dokumenter",
            entity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(401)
    }

    @Test
    fun `POST rinasaker dokumenter - ugyldig request - 400`() {
        val createResponse = restTemplate.postForEntity<Void>(
            "$navRinasakerUrl/1/dokumenter",
            ".".httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(400)
    }
}
