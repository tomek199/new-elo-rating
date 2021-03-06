package com.tm.rankme.api.query.league

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class LeagueQueryHandler(
    private val restTemplate: RestTemplate,
    @Value("\${query-service.url}") private val url: String
) {

    fun handle(query: GetLeagueQuery): League? {
        return restTemplate.getForObject("$url/leagues/${query.id}", League::class.java)
    }
}