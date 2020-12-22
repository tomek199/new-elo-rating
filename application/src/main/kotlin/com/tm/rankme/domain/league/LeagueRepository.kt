package com.tm.rankme.domain.league

import com.tm.rankme.domain.base.EventEmitter
import com.tm.rankme.domain.base.EventStorage
import com.tm.rankme.domain.base.Repository
import java.util.*

abstract class LeagueRepository(
    protected val eventStorage: EventStorage<League>,
    protected val eventEmitter: EventEmitter
) : Repository<League> {

    override fun byId(id: UUID): League = eventStorage.events(id.toString()).let {
        League.from(it)
    }

    override fun store(aggregate: League) = aggregate.pendingEvents.forEach {
        eventStorage.save(it)
        eventEmitter.emit(it)
    }

    abstract fun exist(id: UUID): Boolean
}