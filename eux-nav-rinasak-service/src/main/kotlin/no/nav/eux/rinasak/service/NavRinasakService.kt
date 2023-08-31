package no.nav.eux.rinasak.service

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnRequest
import no.nav.eux.rinasak.model.dto.NavRinasakFinnResponse
import no.nav.eux.rinasak.persistence.FagsakRepository
import no.nav.eux.rinasak.persistence.NavRinasakRepository
import no.nav.eux.rinasak.persistence.SedRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NavRinasakService(
    val navRinasakRepository: NavRinasakRepository,
    val fagsakRepository: FagsakRepository,
    val sedRepository: SedRepository,
) {

    @Transactional
    fun createNavRinasak(request: NavRinasakCreateRequest) {
        navRinasakRepository.save(request.navRinasakEntity)
        request.initiellFagsakEntity?.let { fagsakRepository.save(it) }
        request.dokumentEntities.forEach { sedRepository.save(it) }
    }

    fun findAllNavRinasaker(request: NavRinasakFinnRequest): List<NavRinasakFinnResponse> {
        val navRinasakList = when {
            request.rinasakId != null -> navRinasakRepository.findAllByRinasakId(request.rinasakId!!)
            else -> navRinasakRepository.findAll()
        }
        val fagsakMap = fagsakRepository
            .findAllById(navRinasakList.map { it.navRinasakUuid })
            .associateBy { it.navRinasakUuid }
        val sedMap = sedRepository
            .findByNavRinasakUuidIn(navRinasakList.map { it.navRinasakUuid })
            .groupBy { it.navRinasakUuid }
        return navRinasakList.map {
            NavRinasakFinnResponse(it, fagsakMap[it.navRinasakUuid], sedMap[it.navRinasakUuid])
        }
    }
}
