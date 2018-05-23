package io.jbehavetutorials.steps.facebook;

import io.jbehavetutorials.appmodule.facebook.FacebookApp;
import io.jbehavetutorials.constants.Browser;
import io.jbehavetutorials.steps.common.CommonSteps;
import io.jbehavetutorials.testutility.BrowserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jbehave.core.annotations.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public class FacebookStatusPost extends CommonSteps{
    FacebookApp facebookApp;
    Logger logger = LogManager.getLogger(FacebookStatusPost.class);

    @Given("I have a Facebook account")
    public void iHaveAFacebookAccount()
    {
        try {
            facebookApp = new FacebookApp(webDriver);
            Assert.assertTrue("Facebook object creation failed",facebookApp!=null);
        }
        catch (Exception e)
        {
            logger.error("Error",e);
        }
    }

    @Given("I open Facebook in my web browser with the url <url>")
    public void iOpenFacebookInMyWebBrowser(@Named("url") String url)
    {
        try {
            facebookApp.open(url);
        }
        catch (MalformedURLException e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Given("I login to Facebook with username <username> and password <password>")
    public void iLoginToFacebook(@Named("username") String username, @Named("password") String password)
    {
        try {
            facebookApp.login(username, password);
            Assert.assertTrue("Facebook login failed",facebookApp.isElementPresent(facebookApp.headerPage.accountSettings));
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Then("I check if status message box is present in the homepage after successful login")
    public void iCheckIfStatusBoxIsPresent()
    {
        try {
            Assert.assertTrue("Status box is not available",facebookApp.isElementPresent(facebookApp.homePage.makePost));
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @When("I put my status <message> in the status box")
    public void iPutMyStatusMessageInStatusBox(@Named("message") String message)
    {
        try {
            facebookApp.putStatusMessage(message);
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @When("I click post button")
    public void iClickPostButton()
    {
        try{
            facebookApp.postStatus();
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Then("I should see my new status <message> in my news feeds")
    public void iShouldSeeMyNewStatusInNewsFeed(@Named("message") String message)
    {
        Assert.assertEquals("Facebook post not seen in news feed",message,facebookApp.newsFeedPage.getNewsFeedText(0));
    }

    @Then("I check if status message box is present in the homepage after unsuccessful login")
    public void iCheckAvailibilityOfStatusBox()
    {
        try {
            Assert.assertFalse("Status box is available",facebookApp.isElementPresent(facebookApp.homePage.makePost));
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }
}
