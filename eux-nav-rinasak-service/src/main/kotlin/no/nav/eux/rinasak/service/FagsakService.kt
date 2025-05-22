package no.nav.eux.rinasak.service

import no.nav.eux.rinasak.model.dto.FagsakPatchRequest
import no.nav.eux.rinasak.persistence.FagsakRepository
import no.nav.eux.rinasak.persistence.NavRinasakRepository
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class FagsakService(
    val fagsakRepository: FagsakRepository,
    val navRinasakRepository: NavRinasakRepository,
) {

    fun patch(request: FagsakPatchRequest, rinasakId: Int) {
        val navRinasak = navRinasakRepository.findByRinasakId(rinasakId)
            ?: throw ResponseStatusException(NOT_FOUND, "$rinasakId ikke funnet")
        val fagsak = request.toFagsak(navRinasak.navRinasakUuid)
        fagsakRepository.save(fagsak)
    }
}