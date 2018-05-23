package io.jbehavetutorials.steps.common;

import io.jbehavetutorials.testutility.BrowserFactory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.openqa.selenium.WebDriver;

/**
 * Created by prasantabiswas on 23/05/18.
 */
public class CommonSteps {

    protected WebDriver webDriver;

    @Given("I initialize my $browserName browser instance")
    public void setUpMethod(@Named("browserName") String browser)
    {
        webDriver = BrowserFactory.getDriver(browser);
    }

    @Given("I cleanup my browser instance")
    public void tearDownMethod()
    {
        // Cleanup
        if(webDriver != null)
            webDriver.quit();
    }
}
