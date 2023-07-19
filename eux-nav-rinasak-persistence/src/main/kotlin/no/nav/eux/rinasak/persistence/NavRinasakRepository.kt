@file:Suppress("SpringDataRepositoryMethodParametersInspection")

package no.nav.eux.rinasak.persistence

import no.nav.eux.rinasak.model.entity.Fagsak
import no.nav.eux.rinasak.model.entity.NavRinasak
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NavRinasakRepository : JpaRepository<NavRinasak, UUID>

@Repository
interface FagsakRepository : JpaRepository<Fagsak, UUID>
