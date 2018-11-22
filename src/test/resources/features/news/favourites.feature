Feature: Favourites
    To search for articles I like quickly
    As a user
    I want store favourite searches and articles so I can use these later

    Scenario: No favourite searches
        Given user has no favourite articles
        When user navigates to the favourites section
        Then the favourite searches screen is shown with the message that there are no favourite searches yet
