package no.nav.eux.rinasak.webapp

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import no.nav.eux.rinasak.webapp.common.navRinasakerFinnUrl
import no.nav.eux.rinasak.webapp.common.navRinasakerUrl
import no.nav.eux.rinasak.webapp.common.uuid1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelse
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel1
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel2
import no.nav.eux.rinasak.webapp.dataset.opprettelse.navRinasakOpprettelseEnkel3
import no.nav.eux.rinasak.webapp.model.base.NavRinasakFinnKriterier
import no.nav.eux.rinasak.webapp.model.base.NavRinasaker
import no.nav.eux.rinasak.webapp.model.opprettelse.FagsakOpprettelse
import no.nav.eux.rinasak.webapp.model.opprettelse.NavRinasakOpprettelse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpMethod

class RinasakerApiTest : AbstractRinasakerApiImplTest() {

    @Test
    fun `POST rinasaker - forespørsel, finn med id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        createResponse.statusCode.value() shouldBe 201
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        navRinasak.rinasakId shouldBe 1
        navRinasak.overstyrtEnhetsnummer shouldBe "1234"
        with(navRinasak.initiellFagsak!!) {
            tema shouldBe "AAA"
            system shouldBe "system"
            nr shouldBe "nr"
            type shouldBe "FAGSAK"
            opprettetBruker shouldBe "ukjent"
            fnr shouldBe "03028700001"
            arkiv shouldBe "PSAK"
        }
        with(navRinasak.dokumenter!!.single()) {
            sedId shouldBe uuid1
            sedVersjon shouldBe 1
            dokumentInfoId shouldBe "000000001"
            sedType shouldBe "type"
        }
    }

    @Test
    fun `POST rinasaker - forespørsel, uten fagsak og sed finn med id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse(initiellFagsak = null).httpEntity
        )
        createResponse.statusCode.value() shouldBe 201
        val navRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        navRinasak.rinasakId shouldBe 1
        navRinasak.initiellFagsak.shouldBeNull()
    }

    @Test
    fun `POST rinasaker finn - forespørsel, ikke funnet med feil id - 200`() {
        val createResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            navRinasakOpprettelse.httpEntity
        )
        createResponse.statusCode.value() shouldBe 201
        val navRinasaker = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 0).httpEntity
            )!!
            .navRinasaker
        navRinasaker.shouldBeEmpty()
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
        navRinasak.rinasakId shouldBe 2
        navRinasak.initiellFagsak.shouldBeNull()
    }

    @Test
    fun `PATCH rinasaker fagsak - update existing fagsak - 201`() {
        val createRinasakResponse = restTemplate.postForEntity<Void>(
            navRinasakerUrl,
            NavRinasakOpprettelse(initiellFagsak = null).httpEntity
        )
        createRinasakResponse.statusCode.value() shouldBe 201
        val initialFagsakOpprettelse = FagsakOpprettelse(
            tema = "AAA",
            type = "FAGSAK",
            system = "system",
            nr = "nr",
            fnr = "03028700001"
        )
        val initialResponse = restTemplate.exchange(
            "$navRinasakerUrl/1/fagsak",
            HttpMethod.PATCH,
            initialFagsakOpprettelse.httpEntity,
            Void::class.java
        )
        initialResponse.statusCode.value() shouldBe 201
        val initialNavRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        with(initialNavRinasak.fagsak!!) {
            tema shouldBe "AAA"
            system shouldBe "system"
            nr shouldBe "nr"
            type shouldBe "FAGSAK"
            fnr shouldBe "03028700001"
        }
        val updatedFagsakOpprettelse = FagsakOpprettelse(
            tema = "BBB",
            type = "KLAGE",
            system = "updated-system",
            nr = "updated-nr",
            fnr = "12345678901"
        )
        val updateResponse = restTemplate.exchange(
            "$navRinasakerUrl/1/fagsak",
            HttpMethod.PATCH,
            updatedFagsakOpprettelse.httpEntity,
            Void::class.java
        )
        updateResponse.statusCode.value() shouldBe 201
        val updatedNavRinasak = restTemplate
            .postForObject<NavRinasaker>(
                url = navRinasakerFinnUrl,
                request = NavRinasakFinnKriterier(rinasakId = 1).httpEntity
            )!!
            .navRinasaker
            .single()
        with(updatedNavRinasak.fagsak!!) {
            tema shouldBe "BBB"
            type shouldBe "KLAGE"
            system shouldBe "updated-system"
            nr shouldBe "updated-nr"
            fnr shouldBe "12345678901"
        }
    }

    @Test
    fun `POST rinasaker fagsak - non-existent rinasak - 404`() {
        val nonExistentRinasakId = 999
        val fagsakOpprettelse = FagsakOpprettelse(
            tema = "AAA",
            type = "FAGSAK",
            system = "system",
            nr = "nr",
            fnr = "03028700001"
        )
        val response = restTemplate.exchange(
            "$navRinasakerUrl/$nonExistentRinasakId/fagsak",
            HttpMethod.PATCH,
            fagsakOpprettelse.httpEntity,
            Void::class.java
        )
        response.statusCode.value() shouldBe 404
    }
}
