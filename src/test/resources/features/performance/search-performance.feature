@performance
Feature: Search
    To find news articles about a subject quickly
    As a user
    I want search functionality which feels quick and smooth

    Scenario: Scrolling trough the results screen
        Given user is on the searchpage, start perfomance logs
        When user performs a searchquery
        Then a scrollable list with news items is shown