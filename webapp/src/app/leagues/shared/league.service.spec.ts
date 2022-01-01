import { TestBed } from '@angular/core/testing';

import { LeagueService } from './league.service';
import { Apollo } from 'apollo-angular';
import { of } from 'rxjs';
import { ApolloQueryResult } from '@apollo/client';
import { Page } from '../../shared/model/page';
import { League } from '../../shared/model/league';
import { LEAGUE_WITH_PLAYERS_AND_LEAGUE, LEAGUES_PAGE } from '../../../testing/data';

describe('LeagueService', () => {
  let service: LeagueService;
  let apolloSpy = jasmine.createSpyObj('Apollo', ['query'])

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: Apollo, useValue: apolloSpy }
      ]
    });
    service = TestBed.inject(LeagueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return leagues page', () => {
    apolloSpy.query.and.returnValue(of({data: {leagues: LEAGUES_PAGE}} as ApolloQueryResult<{leagues: Page<League>}>));
    service.leagues(2).subscribe(({data}) => {
      expect(data.leagues.pageInfo).toEqual(LEAGUES_PAGE.pageInfo);
      expect(data.leagues.edges).toEqual(LEAGUES_PAGE.edges)
    })
  });

  it('should return leagues page after given cursor', () => {
    apolloSpy.query.and.returnValue(of({data: {leagues: LEAGUES_PAGE}} as ApolloQueryResult<{leagues: Page<League>}>));
    service.leagues(2, "league-3-cur").subscribe(({data}) => {
      expect(data.leagues.pageInfo).toEqual(LEAGUES_PAGE.pageInfo);
      expect(data.leagues.edges).toEqual(LEAGUES_PAGE.edges)
    })
  });

  it('should return league with players and games', () => {
    apolloSpy.query.and.returnValue(of({data: {league: LEAGUE_WITH_PLAYERS_AND_LEAGUE}} as ApolloQueryResult<{league: League}>));
    service.leagueWithPlayersAndGames(LEAGUE_WITH_PLAYERS_AND_LEAGUE.id).subscribe(({data}) => {
      expect(data.league.id).toEqual(LEAGUE_WITH_PLAYERS_AND_LEAGUE.id);
      expect(data.league.name).toEqual(LEAGUE_WITH_PLAYERS_AND_LEAGUE.name);
      expect(data.league.players).toEqual(LEAGUE_WITH_PLAYERS_AND_LEAGUE.players)
      expect(data.league.games.pageInfo).toEqual(LEAGUE_WITH_PLAYERS_AND_LEAGUE.games.pageInfo)
      expect(data.league.games.edges).toEqual(LEAGUE_WITH_PLAYERS_AND_LEAGUE.games.edges)
    })
  });
});
