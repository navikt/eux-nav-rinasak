package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.openapi.api.RinasakerApi
import no.nav.eux.rinasak.openapi.model.NavRinasakCreateType
import no.nav.eux.rinasak.openapi.model.NavRinasakSearchCriteriaType
import no.nav.eux.rinasak.service.NavRinasakService
import no.nav.security.token.support.core.api.Protected
import org.springframework.web.bind.annotation.RestController

@RestController
class RinasakerApiImpl(
    val service: NavRinasakService
) : RinasakerApi {

    @Protected
    override fun opprettNavRinasak(
        navRinasakCreateType: NavRinasakCreateType,
        userId: String?
    ) = service
        .createNavRinasak(navRinasakCreateType.navRinasakCreateRequest)
        .toCreatedEmptyResponseEntity()

    @Protected
    override fun navRinasakFinn(
        navRinasakSearchCriteriaType: NavRinasakSearchCriteriaType,
        userId: String?
    ) = service
        .findAllNavRinasaker()
        .toNavRinasakSearchResponseType()
        .also { println(it) }
        .toOkResponseEntity()
}
