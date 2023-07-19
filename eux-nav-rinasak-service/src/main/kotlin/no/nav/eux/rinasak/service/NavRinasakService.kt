package no.nav.eux.rinasak.service

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.persistence.FagsakRepository
import no.nav.eux.rinasak.persistence.NavRinasakRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NavRinasakService(
    val navRinasakRepository: NavRinasakRepository,
    val fagsakRepository: FagsakRepository
) {

    @Transactional
    fun createNavRinasak(request: NavRinasakCreateRequest) {
        navRinasakRepository.save(request.navRinasakEntity)
        request.fagsakEntity?.let { fagsakRepository.save(it) }
    }

    fun findAllNavRinasaker(): List<NavRinasakFinnResponse> {
        val navRinasaker = navRinasakRepository.findAll()
        val fagsaker = fagsakRepository
            .findAllById(navRinasaker.map { it.navRinasakUuid })
            .associateBy { it.navRinasakUuid }
        return navRinasaker.map { NavRinasakFinnResponse(it, fagsaker[it.navRinasakUuid]) }
    }
}
