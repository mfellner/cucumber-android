Feature: Hello Activity
  In order to have a little bit of fun
  The activity should greet me the way I want

  Scenario: Say hello
    Given I have a hello activity with "Howdy"
    When I press "say hello"
    Then it should answer with "Howdy, World!"
