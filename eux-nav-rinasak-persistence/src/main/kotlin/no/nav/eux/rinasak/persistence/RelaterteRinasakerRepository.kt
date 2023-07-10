@file:Suppress("SpringDataRepositoryMethodParametersInspection")

package no.nav.eux.rinasak.persistence

import no.nav.eux.rinasak.model.NavRinasak
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NavRinasakRepository : JpaRepository<NavRinasak, String>
