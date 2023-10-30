@file:Suppress("SpringDataRepositoryMethodParametersInspection")

package no.nav.eux.rinasak.persistence

import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.model.entity.InitiellFagsak
import no.nav.eux.rinasak.model.entity.NavRinasak
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NavRinasakRepository : JpaRepository<NavRinasak, UUID> {
    fun findAllByRinasakId(rinasakId: Int): List<NavRinasak>
}

@Repository
interface FagsakRepository : JpaRepository<InitiellFagsak, UUID>

@Repository
interface DokumentRepository : JpaRepository<Dokument, UUID> {
    fun findByNavRinasakUuidIn(navRinasakUuids: List<UUID>): List<Dokument>
}
