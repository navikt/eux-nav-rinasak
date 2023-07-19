package no.nav.eux.rinasak.service

import no.nav.eux.rinasak.model.dto.NavRinasakCreateRequest
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
        request.fagsakEntity?.let { fagsakRepository.save(it) }
        request.sedEntities.forEach { sedRepository.save(it) }
    }

    fun findAllNavRinasaker(): List<NavRinasakFinnResponse> {
        val navRinasaker = navRinasakRepository.findAll()
        val fagsaker = fagsakRepository
            .findAllById(navRinasaker.map { it.navRinasakUuid })
            .associateBy { it.navRinasakUuid }
        val seder = sedRepository
            .findByNavRinasakUuidIn(navRinasaker.map { it.navRinasakUuid })
            .groupBy { it.navRinasakUuid }
        println(seder)
        return navRinasaker.map {
            NavRinasakFinnResponse(it, fagsaker[it.navRinasakUuid], seder[it.navRinasakUuid])
        }
    }
}
