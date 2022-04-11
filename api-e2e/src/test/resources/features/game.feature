Feature: Player

  Background: I init and cleanup database
    Given I cleanup database
    * I create league "Star Wars"
    * I use league "Star Wars"

  Scenario: Show empty games list
    Then I have no games

  Scenario: Play multiple games to check pagination
    * I create player "Darth Vader"
    * I create player "Han Solo"
    When I play 12 games between "Darth Vader" and "Han Solo"
    * I schedule 13 games between "Han Solo" and "Darth Vader" in 2 hours
    Then I have first 14 of 25 games listed
    * I have first 25 of 25 games listed
    * I have first 17 after 3 of 25 games listed
    * I have first 21 after 4 of 25 games listed
    * I have first 24 before 25 of 25 games listed
    * I have first 13 before 18 of 25 games listed
    And I have first 7 of 12 completed games listed
    * I have first 12 of 12 completed games listed
    * I have first 5 after 3 of 12 completed games listed
    * I have first 8 after 4 of 12 completed games listed
    * I have first 11 before 12 of 12 completed games listed
    * I have first 5 before 8 of 12 completed games listed
    And I have first 9 of 13 scheduled games listed
    * I have first 13 of 13 scheduled games listed
    * I have first 3 after 6 of 13 scheduled games listed
    * I have first 11 after 2 of 13 scheduled games listed
    * I have first 12 before 13 of 13 scheduled games listed
    * I have first 8 before 11 of 13 scheduled games listed

  Scenario: Play multiple games to check games data
    * I create player "Luke Skywalker"
    * I create player "R2D2"
    When I play game between "Luke Skywalker" and "R2D2" with result 1 : 3
    * I play game between "R2D2" and "Luke Skywalker" with result 2 : 5
    Then I have 2 games:
      | firstName      | firstScore | firstRating | firstRatingDelta | firstDeviation | firstDeviationDelta | secondName     | secondScore | secondRating | secondRatingDelta | secondDeviation | secondDeviationDelta |
      | R2D2           | 2          | 1430        | -232             | 263            | -27                 | Luke Skywalker | 5           | 1570         | 232               | 263             | -27                  |
      | Luke Skywalker | 1          | 1338        | -162             | 290            | -60                 | R2D2           | 3           | 1662         | 162               | 290             | -60                  |

  Scenario: Schedule and complete game
    * I create player "Chewbacca"
    * I create player "Yoda"
    When I play game between "Chewbacca" and "Yoda" with result 0 : 1
    * I schedule game between "Yoda" and "Chewbacca" in 2 hours
    Then I have 2 games:
      | firstName | firstScore | firstRating | firstRatingDelta | firstDeviation | firstDeviationDelta | secondName | secondScore | secondRating | secondRatingDelta | secondDeviation | secondDeviationDelta |
      | Yoda      |            | 1662        |                  | 290            |                     | Chewbacca  |             | 1338         |                   | 290             |                      |
      | Chewbacca | 0          | 1338        | -162             | 290            | -60                 | Yoda       | 1           | 1662         | 162               | 290             | -60                  |
    When I complete game between "Yoda" and "Chewbacca" with result 2 : 3
    Then I have 2 games:
      | firstName | firstScore | firstRating | firstRatingDelta | firstDeviation | firstDeviationDelta | secondName | secondScore | secondRating | secondRatingDelta | secondDeviation | secondDeviationDelta |
      | Yoda      | 2          | 1430        | -232             | 263            | -27                 | Chewbacca  | 3           | 1570         | 232               | 263             | -27                  |
      | Chewbacca | 0          | 1338        | -162             | 290            | -60                 | Yoda       | 1           | 1662         | 162               | 290             | -60                  |