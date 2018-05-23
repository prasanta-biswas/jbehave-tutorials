package io.jbehavetutorials.steps.wallethub;

import io.jbehavetutorials.appmodule.wallethub.WalletHubApp;
import io.jbehavetutorials.constants.Browser;
import io.jbehavetutorials.steps.common.CommonSteps;
import io.jbehavetutorials.testutility.BrowserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jbehave.core.annotations.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.MalformedURLException;

/**
 * Created by prasantabiswas on 22/05/18.
 */
public class WalletHubReviewInsurance extends CommonSteps {
    WalletHubApp wallethubApp;
    int reviewCount = 0;
    Logger logger = LogManager.getLogger(WalletHubReviewInsurance.class);

    @Given("I have a WalletHub test account")
    public void iHaveAWallerHubTestAccount()
    {
        try{
            wallethubApp = new WalletHubApp(webDriver);
        }
        catch (Exception e)
        {
            logger.error("Error",e);
        }
    }

    @Given("I open wallethub in the web browser using url <url>")
    public void iOpenWalletHubInBrowser(@Named("url") String url)
    {
        try {
            logger.info("Opening "+url);
            wallethubApp.open(url);
        }
        catch (MalformedURLException e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Given("I login to my wallethub account with email <email> and password <password>")
    public void iLoginToMyWalletHubAccount(@Named("email") String email, @Named("password") String password)
    {
        try {
            wallethubApp.login(email,password);
            Assert.assertTrue("WalletHub login failed", wallethubApp.isElementPresent(wallethubApp.menuBar.user));
            logger.info("Login is successful");
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Then("I should see my username <username> on my homepage")
    public void iShouldLandOnMyHomePage(@Named("username") String username)
    {
        try{
            wallethubApp.waitForVisibilityOf(wallethubApp.menuBar.user);
            Assert.assertTrue("Username does not match on home page",wallethubApp.homePage.getUsername().contains(username));
        }
        catch (Exception e){
            logger.error("Error occurred",e);
        }
    }

    @Given("I navigate to review url <reviewUrl> after login")
    public void iNavigateToReviewPage(@Named("reviewUrl") String reviewUrl)
    {
        try {
            logger.info("Navigating to "+reviewUrl);
            wallethubApp.navigate(reviewUrl);
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Given("I count the current review count in my review list")
    public void iCountTheExistingReviews()
    {
        try {
            reviewCount = wallethubApp.reviewPage.getReviewCount();
            logger.info("Current reviewHealthPolicy count: "+reviewCount);
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Given("I navigate to insurance company url <insuranceCompanyUrl>")
    public void iNavigateToInsuranceCompany(@Named("insuranceCompanyUrl") String insuranceCompanyUrl)
    {
        try {
            logger.info("Navigating to "+insuranceCompanyUrl);
            wallethubApp.navigate(insuranceCompanyUrl);
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @When("I click Write a Review button")
    public void iClickWriteAReviewButton()
    {
        try {
            //Closing unwanted "Join Wallethub" popup
            logger.debug("Closing unwanted popup");
            wallethubApp.closePopup();
            logger.info("Click Write a Review button");
            wallethubApp.testInsuranceCompanyPage.writeReview.click();
        }
        catch (Exception e){
            logger.error("Error occurred",e);
        }
    }

    @Then("I should see new review page")
    public void iShouldSeeNewReviewPage()
    {
        try {
            Assert.assertTrue("New review page not loaded", wallethubApp.isElementPresent(wallethubApp.writeReviewPage.policyDropdown));
            logger.info("Landed on new review page successfully");
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Given("I select insurance policy <policy> from insurance list in new review page")
    public void iSelectInsurancePolicy(@Named("policy") String policy)
    {
        try {
            logger.info("Select policy "+policy);
            wallethubApp.writeReviewPage.clickPolicy();
            wallethubApp.waitForVisibilityOf(wallethubApp.writeReviewPage.policy);
            wallethubApp.writeReviewPage.selectPolicy(policy);
            wallethubApp.waitForInvisibilityOf(wallethubApp.writeReviewPage.loadingImage);
        }
        catch (Exception e){
            logger.error("Error occurred",e);
        }
    }

    @Given("I apply rating <star> star")
    public void iApplyStartRating(@Named("star") int star)
    {
        try {
            logger.info("Apply "+star+" star(s)");
            wallethubApp.writeReviewPage.applyRating(5);
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }

    @Given("I put my review text <text>")
    public void iPutMyReviewText(@Named("text") String text)
    {
        try {
            logger.info("Review text: "+text);
            wallethubApp.writeReviewPage.setReview(text);
        }
        catch (Exception e){
            logger.error("Error occurred",e);
        }
    }

    @When("I click submit button")
    public void iClickSubmitButton()
    {
        try {
            logger.info("Submit review");
            wallethubApp.writeReviewPage.clickSubmit();
        }
        catch (Exception e){
            logger.error("Error occurred",e);
        }
    }

    @Then("I should see confirmation message <message>")
    public void iClickSubmitButton(@Named("message") String message)
    {
        try {
            logger.info("Checking confirmation message: "+message);
            wallethubApp.waitForVisibilityOf(wallethubApp.writeReviewPage.confirmation);
            String actualConfirmationMessage = wallethubApp.writeReviewPage.getConfirmationText();
            Assert.assertTrue("Confirmation message does not match",actualConfirmationMessage.contains(message));
            logger.info("Review submission confirmed");
        }
        catch (Exception e){
            logger.error("Error occurred",e);
        }
    }

    @Then("I should see a my new review text <text> in my review url <reviewUrl>")
    public void iShouldSeeMyNewReview(@Named("text") String text,@Named("reviewUrl") String reviewUrl)
    {
        try {
            logger.info("Validating submitted review");
            wallethubApp.navigate(reviewUrl);
            int newReviewCount = wallethubApp.reviewPage.getReviewCount();
            logger.info("New reviewHealthPolicy count: "+newReviewCount);
            wallethubApp.validateNewReview(reviewCount,newReviewCount,text);
            logger.info("Review feed added successfully");
        }
        catch (Exception e){
            logger.error("Error occurred",e);
        }
    }

}
