package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.openapi.model.SedJournalstatus
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserFinnUrl
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusFinnKriterierTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusPutTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatuserTestModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.POST

class SedJournalstatusApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `PUT sed journalstatuser - forespørsel, finn med id - 200`() {
        val createResponse = restTemplate.exchange<Void>(
            url = sedJournalstatuserUrl,
            method = HttpMethod.PUT,
            requestEntity = SedJournalstatusPutTestModel(
                rinasakId = 1,
                dokumentInfoId = "000000111",
                sedId = uuid1,
                sedVersjon = 1,
                sedJournalstatus = SedJournalstatus.uKJENT
            )
                .httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(200)
        val sedJournalstatus = restTemplate
            .postForObject<SedJournalstatuserTestModel>(
                url = sedJournalstatuserFinnUrl,
                request = SedJournalstatusFinnKriterierTestModel(
                    sedId = uuid1,
                    sedVersjon = 1
                )
                    .httpEntity
            )!!
            .sedJournalstatuser
            .single()
        assertThat(sedJournalstatus.rinasakId).isEqualTo(1)
        assertThat(sedJournalstatus.dokumentInfoId).isEqualTo("000000111")
        assertThat(sedJournalstatus.sedId).isEqualTo(uuid1)
        assertThat(sedJournalstatus.sedVersjon).isEqualTo(1)
        assertThat(sedJournalstatus.sedJournalstatus).isEqualTo("UKJENT")
    }

    @Test
    fun `PUT sed journalstatuser - forespørsel, finn med status - 200`() {
        val createResponse = restTemplate.exchange<Void>(
            url = sedJournalstatuserUrl,
            method = HttpMethod.PUT,
            requestEntity = SedJournalstatusPutTestModel(
                rinasakId = 1,
                dokumentInfoId = "000000111",
                sedId = uuid1,
                sedVersjon = 1,
                sedJournalstatus = SedJournalstatus.uKJENT
            )
                .httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(200)
        val sedJournalstatus = restTemplate
            .postForObject<SedJournalstatuserTestModel>(
                url = sedJournalstatuserFinnUrl,
                request = SedJournalstatusFinnKriterierTestModel(
                    sedJournalstatus = "UKJENT"
                )
                    .httpEntity
            )!!
            .sedJournalstatuser
            .single()
        assertThat(sedJournalstatus.sedId).isEqualTo(uuid1)
        assertThat(sedJournalstatus.sedVersjon).isEqualTo(1)
        assertThat(sedJournalstatus.sedJournalstatus).isEqualTo("UKJENT")
    }

    @Test
    fun `PUT sed journalstatuser - forespørsel, ikke funnet pga annen status - 200`() {
        val createResponse = restTemplate.exchange<Void>(
            url = sedJournalstatuserUrl,
            method = HttpMethod.PUT,
            requestEntity = SedJournalstatusPutTestModel(
                rinasakId = 1,
                dokumentInfoId = "000000111",
                sedId = uuid1,
                sedVersjon = 1,
                sedJournalstatus = SedJournalstatus.uKJENT
            )
                .httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(200)
        val sedJournalstatus = restTemplate
            .postForObject<SedJournalstatuserTestModel>(
                url = sedJournalstatuserFinnUrl,
                request = SedJournalstatusFinnKriterierTestModel(
                    sedJournalstatus = "JOURNALFOERT"
                )
                    .httpEntity
            )!!
            .sedJournalstatuser
        assertThat(sedJournalstatus).isEmpty()
    }

    @Test
    fun `POST sed journalstatuser finn - forespørsel, finn uten argumenter - 400`() {
        val response = restTemplate.exchange<Void>(
            url = sedJournalstatuserFinnUrl,
            method = POST,
            requestEntity = SedJournalstatusFinnKriterierTestModel()
                .httpEntity
        )
        assertThat(response.statusCode.value()).isEqualTo(400)
    }
}
