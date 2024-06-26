package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.webapp.common.sedJournalstatuserFinnUrl
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusFinnKriterierRinasakIdTestModel
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
                sedId = uuid1,
                sedVersjon = 1,
                sedJournalstatus = "UKJENT"
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
                sedId = uuid1,
                sedVersjon = 1,
                sedJournalstatus = "UKJENT"
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
    fun `PUT sed journalstatuser - forespørsel, finn med rinasakId - 200`() {
        val createResponse = restTemplate.exchange<Void>(
            url = sedJournalstatuserUrl,
            method = HttpMethod.PUT,
            requestEntity = SedJournalstatusPutTestModel(
                rinasakId = 3,
                sedId = uuid1,
                sedVersjon = 0,
                sedJournalstatus = "MELOSYS_JOURNALFOERER"
            )
                .httpEntity
        )
        assertThat(createResponse.statusCode.value()).isEqualTo(200)
        val sedJournalstatus = restTemplate
            .postForObject<SedJournalstatuserTestModel>(
                url = sedJournalstatuserFinnUrl,
                request = SedJournalstatusFinnKriterierRinasakIdTestModel(
                    rinasakId = 3
                )
                    .httpEntity
            )!!
            .sedJournalstatuser
            .single()
        with (sedJournalstatus) {
            assertThat(rinasakId).isEqualTo(3)
            assertThat(sedId).isEqualTo(uuid1)
            assertThat(sedVersjon).isEqualTo(0)
            assertThat(sedJournalstatus.sedJournalstatus).isEqualTo("MELOSYS_JOURNALFOERER")
        }
    }

    @Test
    fun `PUT sed journalstatuser - forespørsel, ikke funnet pga annen status - 200`() {
        val createResponse = restTemplate.exchange<Void>(
            url = sedJournalstatuserUrl,
            method = HttpMethod.PUT,
            requestEntity = SedJournalstatusPutTestModel(
                rinasakId = 1,
                sedId = uuid1,
                sedVersjon = 1,
                sedJournalstatus = "UKJENT"
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
