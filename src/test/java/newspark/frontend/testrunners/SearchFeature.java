package newspark.frontend.testrunners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import newspark.utils.RunnerHooks;

@ComponentScan(basePackages = {"newspark"})
@Configuration
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "json:target/cucumber.json", features = {
        "src/test/resources/features/news/search.feature"},
        glue = "newspark")
public class SearchFeature extends RunnerHooks {
}
