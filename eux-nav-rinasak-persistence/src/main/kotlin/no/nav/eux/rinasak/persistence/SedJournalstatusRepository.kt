package no.nav.eux.rinasak.persistence

import no.nav.eux.rinasak.model.entity.SedJournalstatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SedJournalstatusRepository : JpaRepository<SedJournalstatus, UUID> {
    fun findBySedIdAndSedVersjon(sedId: UUID, sedVersjon: Int): SedJournalstatus?
}
