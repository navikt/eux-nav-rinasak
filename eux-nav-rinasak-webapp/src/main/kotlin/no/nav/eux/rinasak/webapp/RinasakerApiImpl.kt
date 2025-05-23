package no.nav.eux.rinasak.webapp

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import no.nav.eux.logging.mdc
import no.nav.eux.rinasak.openapi.api.RinasakerApi
import no.nav.eux.rinasak.openapi.model.*
import no.nav.eux.rinasak.service.DokumentService
import no.nav.eux.rinasak.service.FagsakService
import no.nav.eux.rinasak.service.NavRinasakService
import no.nav.eux.rinasak.service.TokenContextService
import no.nav.security.token.support.core.api.Protected
import org.springframework.web.bind.annotation.RestController

@RestController
class RinasakerApiImpl(
    val service: NavRinasakService,
    val dokumentService: DokumentService,
    val fagsakService: FagsakService,
    val contextService: TokenContextService,
) : RinasakerApi {

    val log = logger {}

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
        navRinasakCreateType: NavRinasakCreateType
    ) = service
        .mdc(rinasakId = navRinasakCreateType.rinasakId)
        .also { log.info { "oppretter nav rinasak" } }
        .createNavRinasak(navRinasakCreateType.navRinasakCreateRequest)
        .toCreatedEmptyResponseEntity()

    @Protected
    override fun oppdaterNavRinasak(
        navRinasakPatchType: NavRinasakPatchType
    ) = service
        .mdc(rinasakId = navRinasakPatchType.rinasakId)
        .also { log.info { "oppdaterer nav rinasak" } }
        .patchNavRinasak(navRinasakPatchType.navRinasakPatch)
        .toCreatedEmptyResponseEntity()

    @Protected
    override fun navRinasakFinn(
        navRinasakSearchCriteriaType: NavRinasakSearchCriteriaType
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
        .also { log.info { "oppretter nytt dokument i nav rinasak" } }
        .let { dokumentService.createDokument(it) }
        .toCreatedEmptyResponseEntity()

    @Protected
    override fun patchFagsak(
        rinasakId: Int,
        fagsakPatchType: FagsakPatchType
    ) = fagsakService
        .patch(fagsakPatchType.toFagsakPatchType(contextService.navIdent), rinasakId)
        .toCreatedEmptyResponseEntity()

    val NavRinasakCreateType.navRinasakCreateRequest
        get() = toNavRinasakCreateRequest(this, contextService.navIdent)
}
