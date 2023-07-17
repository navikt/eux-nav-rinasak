package no.nav.eux.rinasak.service

import no.nav.eux.rinasak.model.NavRinasak
import no.nav.eux.rinasak.model.NavRinasakCreateRequest
import no.nav.eux.rinasak.persistence.NavRinasakRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NavRinasakService(
    val repository: NavRinasakRepository
) {

    @Transactional
    fun createNavRinasak(navRinasakCreateRequest: NavRinasakCreateRequest) {
        repository.save(navRinasakCreateRequest.navRinasak)
    }

    fun findAllNavRinasaker(): List<NavRinasak> =
        repository.findAll()

}
