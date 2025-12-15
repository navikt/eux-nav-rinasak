package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserFinnUrl
import no.nav.eux.rinasak.webapp.common.sedJournalstatuserUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusFinnKriterierRinasakIdTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusFinnKriterierTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatusPutTestModel
import no.nav.eux.rinasak.webapp.model.base.SedJournalstatuserTestModel
import org.junit.jupiter.api.Test
import org.springframework.boot.resttestclient.exchange
import org.springframework.boot.resttestclient.postForObject
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
        createResponse.statusCode.value() shouldBe 200
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
        sedJournalstatus.rinasakId shouldBe 1
        sedJournalstatus.sedId shouldBe uuid1
        sedJournalstatus.sedVersjon shouldBe 1
        sedJournalstatus.sedJournalstatus shouldBe "UKJENT"
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
        createResponse.statusCode.value() shouldBe 200
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
        sedJournalstatus.sedId shouldBe uuid1
        sedJournalstatus.sedVersjon shouldBe 1
        sedJournalstatus.sedJournalstatus shouldBe "UKJENT"
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
        createResponse.statusCode.value() shouldBe 200
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
        with(sedJournalstatus) {
            rinasakId shouldBe 3
            sedId shouldBe uuid1
            sedVersjon shouldBe 0
            sedJournalstatus.sedJournalstatus shouldBe "MELOSYS_JOURNALFOERER"
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
        createResponse.statusCode.value() shouldBe 200
        val sedJournalstatus = restTemplate
            .postForObject<SedJournalstatuserTestModel>(
                url = sedJournalstatuserFinnUrl,
                request = SedJournalstatusFinnKriterierTestModel(
                    sedJournalstatus = "JOURNALFOERT"
                )
                    .httpEntity
            )!!
            .sedJournalstatuser
        sedJournalstatus.shouldBeEmpty()
    }

    @Test
    fun `POST sed journalstatuser finn - forespørsel, finn uten argumenter - 400`() {
        val response = restTemplate.exchange<Void>(
            url = sedJournalstatuserFinnUrl,
            method = POST,
            requestEntity = SedJournalstatusFinnKriterierTestModel()
                .httpEntity
        )
        response.statusCode.value() shouldBe 400
    }
}
