package newspark.frontend.steps;

import newspark.frontend.screenobjects.BottomNavigationObject;
import newspark.frontend.screenobjects.FavouritesScreen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Component
@Scope("cucumber-glue")
public class FavouritesSteps {

    @Autowired
    private BottomNavigationObject bottomNavigation;
    @Autowired
    private FavouritesScreen favouritesScreen;

    public void navigateToFavourites() {
        bottomNavigation.navigateToFavourites();
    }

    public void checkNoFavourites() {
        assertThat("No favourites message not visible", favouritesScreen.getNoFavouritesMessage().isDisplayed(), is(true));
    }
}
