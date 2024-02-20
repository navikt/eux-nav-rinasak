package no.nav.eux.rinasak.webapp

import no.nav.eux.rinasak.openapi.api.RinasakerApi
import no.nav.eux.rinasak.openapi.model.DokumentCreateType
import no.nav.eux.rinasak.openapi.model.NavRinasakCreateType
import no.nav.eux.rinasak.openapi.model.NavRinasakPatchType
import no.nav.eux.rinasak.openapi.model.NavRinasakSearchCriteriaType
import no.nav.eux.rinasak.service.DokumentService
import no.nav.eux.rinasak.service.NavRinasakService
import no.nav.eux.rinasak.service.TokenContextService
import no.nav.eux.rinasak.service.mdc
import no.nav.security.token.support.core.api.Protected
import org.springframework.web.bind.annotation.RestController

@RestController
class RinasakerApiImpl(
    val service: NavRinasakService,
    val dokumentService: DokumentService,
    val contextService: TokenContextService,
) : RinasakerApi {

    @Protected
    override fun hentNavRinasak(
        rinasakId: Int
    ) = service
        .mdc(rinasakId = rinasakId)
        .findNavRinasakId(rinasakId)
        .toNavRinasakType()
        .toOkResponseEntity()

    @Protected
    override fun opprettNavRinasak(
        navRinasakCreateType: NavRinasakCreateType,
        userId: String?
    ) = service
        .mdc(rinasakId = navRinasakCreateType.rinasakId)
        .createNavRinasak(navRinasakCreateType.navRinasakCreateRequest)
        .toCreatedEmptyResponseEntity()

    @Protected
    override fun oppdaterNavRinasak(
        navRinasakPatchType: NavRinasakPatchType,
        userId: String?
    ) = service
        .mdc(rinasakId = navRinasakPatchType.rinasakId)
        .patchNavRinasak(navRinasakPatchType.navRinasakPatch)
        .toCreatedEmptyResponseEntity()

    @Protected
    override fun navRinasakFinn(
        navRinasakSearchCriteriaType: NavRinasakSearchCriteriaType,
        userId: String?
    ) = service
        .mdc(rinasakId = navRinasakSearchCriteriaType.rinasakId)
        .findAllNavRinasaker(navRinasakSearchCriteriaType.navRinasakFinnRequest)
        .toNavRinasakSearchResponseType()
        .toOkResponseEntity()

    @Protected
    override fun opprettNyttDokument(
        rinasakId: Int,
        dokumentCreateType: DokumentCreateType
    ) = toDokumentCreateRequest(rinasakId, contextService.navIdent, dokumentCreateType)
        .mdc(rinasakId = rinasakId, dokumentInfoId = dokumentCreateType.dokumentInfoId)
        .let { dokumentService.createDokument(it) }
        .toCreatedEmptyResponseEntity()

    val NavRinasakCreateType.navRinasakCreateRequest
        get() = toNavRinasakCreateRequest(this, contextService.navIdent)
}
