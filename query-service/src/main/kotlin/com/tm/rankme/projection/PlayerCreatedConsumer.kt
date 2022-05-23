package com.tm.rankme.projection

import com.tm.rankme.model.player.Player
import com.tm.rankme.model.player.PlayerRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service("playerCreatedMessageConsumer") // todo remove Message from consumer name
class PlayerCreatedConsumer(
    private val repository: PlayerRepository
) : Consumer<PlayerCreatedMessage> {

    private val log = LoggerFactory.getLogger(PlayerCreatedConsumer::class.java)

    override fun accept(message: PlayerCreatedMessage) {
        log.info("Consuming message player-created for aggregate ${message.aggregateId}")
        val player = Player(message.aggregateId, message.leagueId, message.name, message.deviation, message.rating)
        repository.store(player)
    }
}

data class PlayerCreatedMessage(
    val aggregateId: String,
    val leagueId: String,
    val name: String,
    val deviation: Int,
    val rating: Int
)