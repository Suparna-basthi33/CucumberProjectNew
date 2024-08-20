Feature: Verify Login
  Scenario: Verify Login Test
    Given User is on LoginPage
    When User enter Username and password
    And click on submit
    Then Dashboard is displayed