Feature: Hello Activity
  In order to impress my friends
  I want to my activity to greet me

  Scenario: Say hello
    Given I have a hello app with "Howdy"
    #And the weather is fine
    When I ask it to say hi
    Then it should answer with "Howdy World"
