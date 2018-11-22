package newspark.frontend.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import newspark.frontend.steps.FavouritesSteps;
import org.springframework.beans.factory.annotation.Autowired;

public class FavouritesStepDefs {

    @Autowired
    private FavouritesSteps favouritesSteps;

    @Given("^user has no favourite articles$")
    public void userHasNoFavourites() {
        //no implementation needed
    }

    @When("^user navigates to the favourites section$")
    public void navigateToFavourites() {
        favouritesSteps.navigateToFavourites();
    }

    @Then("^the favourite searches screen is shown with the message that there are no favourite searches yet$")
    public void noFavouritesScreenShown() {
        favouritesSteps.checkNoFavourites();
    }
}
