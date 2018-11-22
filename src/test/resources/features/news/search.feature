Feature: Search
    To find news articles about a subject quickly
    As a user
    I want search functionality which provides news articles relating to a searchquery

    Scenario: Scrolling trough the results screen
        Given user is on the search screen
        When user performs a searchquery
        Then a scrollable list with news items is shown

    Scenario: Cancel search
        Given user is on the search screen
        When user enter a searchquery and presses cancel
        Then the searchquery is canceled