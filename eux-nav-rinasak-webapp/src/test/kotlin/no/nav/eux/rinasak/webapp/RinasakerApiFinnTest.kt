package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.openapi.model.NavRinasakSearchResponseType
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterierFagsakId
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterierFagsakIdOgRinasakId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.POST
import org.springframework.http.MediaType


class RinasakerApiFinnTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker finn - finn uten kriterier - 200`() {
        val response = restTemplate.postForEntity<Void>(
            navRinasakerFinnUrl,
            NavRinasakFinnKriterier().httpEntity
        )
        assertThat(response.statusCode.value()).isEqualTo(200)
    }

    @Test
    fun `POST rinasaker finn - ikke autentisert - 401`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>("{}", headers)
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerFinnUrl,
            entity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(401)
    }

    @Test
    fun `POST rinasaker finn - ugyldig request - 400`() {
        val finnResponse = restTemplate.postForEntity<Void>(
            navRinasakerFinnUrl,
            ".".httpEntity
        )
        assertThat(finnResponse.statusCode.value()).isEqualTo(400)
    }

    @Test
    fun `POST rinasaker finn - finn med fagsakId - 200`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )

        val entity = restTemplate.exchange(
            navRinasakerFinnUrl,
            POST,
            NavRinasakFinnKriterierFagsakId().httpEntity,
            NavRinasakSearchResponseType::class.java
        )

        assertThat(entity.statusCode.value()).isEqualTo(200)
        val navRinasakSearchResponseType = entity.body!!
        val navRinasakTypeList = navRinasakSearchResponseType.navRinasaker
        assertThat(navRinasakTypeList!!.size).isEqualTo(1)
        val navRinasakType = navRinasakTypeList.get(0)
        assertThat(navRinasakType.rinasakId).isEqualTo(1)
        assertThat(navRinasakType.overstyrtEnhetsnummer).isEqualTo("1234")
        with(navRinasakType.initiellFagsak!!) {
            assertThat(id).isEqualTo("fagsak-1")
            assertThat(nr).isEqualTo("nr")
            assertThat(fnr).isEqualTo("03028700001")
        }
        with(navRinasakType.dokumenter!!.single()) {
            assertThat(sedId).isEqualTo(uuid1)
            assertThat(sedVersjon).isEqualTo(1)
            assertThat(dokumentInfoId).isEqualTo("000000001")
            assertThat(sedType).isEqualTo("type")
        }
    }

    @Test
    fun `POST rinasaker finn - finn med fagsakId og rinasakId - 200`() {
        restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )

        val entity = restTemplate.exchange(
            navRinasakerFinnUrl,
            POST,
            NavRinasakFinnKriterierFagsakIdOgRinasakId().httpEntity,
            NavRinasakSearchResponseType::class.java
        )

        assertThat(entity.statusCode.value()).isEqualTo(200)
        val navRinasakSearchResponseType = entity.body!!
        val navRinasakTypeList = navRinasakSearchResponseType.navRinasaker
        assertThat(navRinasakTypeList!!.size).isEqualTo(1)
        val navRinasakType = navRinasakTypeList.get(0)
        assertThat(navRinasakType.rinasakId).isEqualTo(1)
        assertThat(navRinasakType.overstyrtEnhetsnummer).isEqualTo("1234")
        with(navRinasakType.initiellFagsak!!) {
            assertThat(id).isEqualTo("fagsak-1")
            assertThat(nr).isEqualTo("nr")
            assertThat(fnr).isEqualTo("03028700001")
        }
        with(navRinasakType.dokumenter!!.single()) {
            assertThat(sedId).isEqualTo(uuid1)
            assertThat(sedVersjon).isEqualTo(1)
            assertThat(dokumentInfoId).isEqualTo("000000001")
            assertThat(sedType).isEqualTo("type")
        }
    }
}
