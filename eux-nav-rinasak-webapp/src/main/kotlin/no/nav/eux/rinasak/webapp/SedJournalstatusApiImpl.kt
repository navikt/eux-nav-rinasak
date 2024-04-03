package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.model.common.toEnum
import no.nav.eux.rinasak.openapi.api.SedApi
import no.nav.eux.rinasak.openapi.model.SedJournalstatusPutType
import no.nav.eux.rinasak.openapi.model.SedJournalstatusSearchCriteriaType
import no.nav.eux.rinasak.service.SedJournalstatusService
import no.nav.eux.rinasak.service.mdc
import no.nav.security.token.support.core.api.Protected
import org.springframework.web.bind.annotation.RestController

@RestController
class SedJournalstatusApiImpl(
    val service: SedJournalstatusService,
) : SedApi {

    @Protected
    override fun settSedJournalstatus(
        sedJournalstatusPutType: SedJournalstatusPutType
    ) =
        service
            .mdc(
                sedId = sedJournalstatusPutType.sedId,
                sedVersjon = sedJournalstatusPutType.sedVersjon,
            )
            .save(
                rinasakId = sedJournalstatusPutType.rinasakId,
                sedId = sedJournalstatusPutType.sedId,
                sedVersjon = sedJournalstatusPutType.sedVersjon,
                status = sedJournalstatusPutType.sedJournalstatus.name.toEnum(),
            )
            .toOkResponseEntity()

    @Protected
    override fun sedJournalstatusFinn(
        sedJournalstatusSearchCriteriaType: SedJournalstatusSearchCriteriaType
    ) =
        service
            .mdc(
                sedId = sedJournalstatusSearchCriteriaType.sedId,
                sedVersjon = sedJournalstatusSearchCriteriaType.sedVersjon,
            )
            .finn(
                sedId = sedJournalstatusSearchCriteriaType.sedId,
                sedVersjon = sedJournalstatusSearchCriteriaType.sedVersjon,
                status = sedJournalstatusSearchCriteriaType.sedJournalstatus.toStatus()
            )
            .toSedJournalstatusSearchResponseType()
            .toOkResponseEntity()
}
