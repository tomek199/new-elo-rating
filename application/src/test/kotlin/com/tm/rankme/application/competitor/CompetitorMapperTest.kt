package com.tm.rankme.application.competitor

import com.tm.rankme.application.Mapper
import com.tm.rankme.domain.competitor.Competitor
import com.tm.rankme.domain.competitor.Statistics
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class CompetitorMapperTest {
    private val leagueId = "l-111"
    private val competitorId = "c-111"
    private val competitorUsername = "Optimus Prime"
    private val mapper: Mapper<Competitor, CompetitorModel> = CompetitorMapper()

    @Test
    internal fun `Should map domain to model`() {
        // given
        val statistics = Statistics(154, 2564, 53, 34, 95, null) // fixme lastGame
        val domain = Competitor(leagueId, competitorId, competitorUsername, statistics)
        // when
        val model = mapper.toModel(domain)
        // then
        assertEquals(domain.id, model.id)
        assertEquals(domain.username, model.username)
        assertEquals(domain.leagueId, model.leagueId)
        assertEquals(domain.statistics.deviation, model.statistics.deviation)
        assertEquals(domain.statistics.rating, model.statistics.rating)
        assertEquals(domain.statistics.won, model.statistics.won)
        assertEquals(domain.statistics.lost, model.statistics.lost)
        assertEquals(domain.statistics.draw, model.statistics.draw)
    }

    @Test
    internal fun `Should throw IllegalStateException when domain competitor id is null`() {
        // when
        val domain = Competitor(leagueId, competitorUsername, Statistics())
        // then
        assertFailsWith<IllegalStateException> { mapper.toModel(domain) }
    }
}