package com.tm.rankme.domain.game

import com.tm.rankme.domain.competitor.Competitor
import com.tm.rankme.domain.competitor.Statistics
import com.tm.rankme.domain.competitorId1
import com.tm.rankme.domain.competitorId2
import com.tm.rankme.domain.competitorName1
import com.tm.rankme.domain.competitorName2
import com.tm.rankme.domain.leagueId
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class GameFactoryTest {
    @Test
    internal fun `Should throw exception when first competitor id is null`() {
        // given
        val competitorTwo = Competitor(leagueId, competitorId2, competitorName2, Statistics())
        val competitorOne = Competitor(leagueId, competitorName1)
        // when
        val exception = assertFailsWith<IllegalStateException> {
            GameFactory.scheduledGame(competitorOne, competitorTwo, leagueId, LocalDateTime.now())
        }
        // then
        assertEquals("Competitor ids cannot be null!", exception.message)
    }

    @Test
    internal fun `Should throw exception when second competitor id is null`() {
        // given
        val competitorOne = Competitor(leagueId, competitorId1, competitorName1, Statistics())
        val competitorTwo = Competitor(leagueId, competitorName2)
        // when
        val exception = assertFailsWith<IllegalStateException> {
            GameFactory.scheduledGame(competitorOne, competitorTwo, leagueId, LocalDateTime.now())
        }
        // then
        assertEquals("Competitor ids cannot be null!", exception.message)

    }

    @Test
    internal fun `Should create scheduled game without score`() {
        // given
        val lastGameDate = LocalDate.now()
        val statisticsOne = Statistics(204, 1344, 49, 38, 8, lastGameDate)
        val competitorOne = Competitor(leagueId, competitorId1, competitorName1, statisticsOne)
        val statisticsTwo = Statistics(279, 2043, 98, 93, 25, lastGameDate)
        val competitorTwo = Competitor(leagueId, competitorId2, competitorName2, statisticsTwo)
        // when
        val game = GameFactory.scheduledGame(competitorOne, competitorTwo, leagueId, LocalDateTime.now())
        // then
        assertNull(game.id)
        assertEquals(leagueId, game.leagueId)

        assertEquals(competitorOne.id, game.playerOne.competitorId)
        assertEquals(competitorOne.username, game.playerOne.username)
        assertEquals(competitorOne.statistics.deviation, game.playerOne.deviation)
        assertEquals(competitorOne.statistics.rating, game.playerOne.rating)
        assertNull(game.playerOne.score)
        assertNull(game.playerOne.ratingDelta)

        assertEquals(competitorTwo.id, game.playerTwo.competitorId)
        assertEquals(competitorTwo.username, game.playerTwo.username)
        assertEquals(competitorTwo.statistics.deviation, game.playerTwo.deviation)
        assertEquals(competitorTwo.statistics.rating, game.playerTwo.rating)
        assertNull(game.playerTwo.score)
        assertNull(game.playerTwo.ratingDelta)
    }

    @Test
    internal fun `Should create completed game`() {
        // given
        val lastGameDate = LocalDate.now()
        val oneStats = Statistics(245, 1397, 0, 0, 0, lastGameDate)
        val competitorOne = Competitor(leagueId, competitorId1, competitorName1, oneStats)
        val twoStats = Statistics(224, 1874, 0, 0, 0, lastGameDate)
        val competitorTwo = Competitor(leagueId, competitorId2, competitorName2, twoStats)
        // when
        val game = GameFactory.completedGame(Pair(competitorOne, 1), Pair(competitorTwo, 0), leagueId)
        // then
        assertNotNull(game.dateTime)
        assertEquals(leagueId, game.leagueId)

        assertEquals(236, game.playerOne.deviation)
        assertEquals(1631, game.playerOne.rating)
        assertEquals(234, game.playerOne.ratingDelta)

        assertEquals(218, game.playerTwo.deviation)
        assertEquals(1681, game.playerTwo.rating)
        assertEquals(-193, game.playerTwo.ratingDelta)
    }
}