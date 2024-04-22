package no.nav.eux.rinasak.persistence

import no.nav.eux.rinasak.model.entity.Dokument
import no.nav.eux.rinasak.model.entity.InitiellFagsak
import no.nav.eux.rinasak.model.entity.NavRinasak
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NavRinasakRepository : JpaRepository<NavRinasak, UUID> {
    fun findAllByRinasakId(rinasakId: Int): List<NavRinasak>
    fun findByRinasakId(rinasakId: Int): NavRinasak?

    @Query(
        value = """SELECT nr FROM NavRinasak nr 
                    JOIN InitiellFagsak if ON if.navRinasakUuid = nr.navRinasakUuid 
                    WHERE if.id = ?1
                """
    )
    fun findAllByFagsakId(fagsakId: String): List<NavRinasak>

    @Query(
        value = """SELECT nr FROM NavRinasak nr 
                    JOIN InitiellFagsak if ON if.navRinasakUuid = nr.navRinasakUuid 
                    WHERE if.id = ?1
                    AND nr.rinasakId = ?2
                """
    )
    fun findAllByFagsakIdAndId(fagsakId: String, id: Int): List<NavRinasak>
}

@Repository
interface FagsakRepository : JpaRepository<InitiellFagsak, UUID>

@Repository
interface DokumentRepository : JpaRepository<Dokument, UUID> {
    fun findByNavRinasakUuidIn(navRinasakUuids: List<UUID>): List<Dokument>
    fun findByNavRinasakUuid(navRinasakUuid: UUID): List<Dokument>
    fun findBySedIdAndSedVersjon(sedId: UUID, sedVersjon: Int): List<Dokument>
}
