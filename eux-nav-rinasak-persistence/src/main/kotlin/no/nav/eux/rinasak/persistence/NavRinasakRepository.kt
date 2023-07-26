@file:Suppress("SpringDataRepositoryMethodParametersInspection")

package no.nav.eux.rinasak.persistence

import no.nav.eux.rinasak.model.entity.Fagsak
import no.nav.eux.rinasak.model.entity.NavRinasak
import no.nav.eux.rinasak.model.entity.Sed
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NavRinasakRepository : JpaRepository<NavRinasak, UUID> {
    fun findAllByRinasakId(rinasakId: String): List<NavRinasak>
}

@Repository
interface FagsakRepository : JpaRepository<Fagsak, UUID>

@Repository
interface SedRepository : JpaRepository<Sed, UUID> {
    fun findByNavRinasakUuidIn(navRinasakUuids: List<UUID>): List<Sed>
}
