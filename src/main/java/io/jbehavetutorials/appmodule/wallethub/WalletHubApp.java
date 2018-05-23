package io.jbehavetutorials.appmodule.wallethub;

import io.jbehavetutorials.interfaces.Commons;
import io.jbehavetutorials.pageobjects.wallethub.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by prasantabiswas on 22/05/18.
 */
public class WalletHubApp implements Commons {
    private static Logger log = LogManager.getLogger(WalletHubApp.class);
    private final WebDriverWait wait;
    private final Actions builder;
    private final WebDriver driver;
    public final LoginPage loginPage;
    public final MenuBar menuBar;
    public final ReviewPage reviewPage;
    public final TestInsuranceCompanyPage testInsuranceCompanyPage;
    public final WriteReviewPage writeReviewPage;
    public final HomePage homePage;

    public WalletHubApp(WebDriver driver)
    {
        log.info("Initializing page objects...");
        this.driver = driver;
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        menuBar = PageFactory.initElements(driver, MenuBar.class);
        reviewPage = PageFactory.initElements(driver, ReviewPage.class);
        testInsuranceCompanyPage = PageFactory.initElements(driver, TestInsuranceCompanyPage.class);
        writeReviewPage = PageFactory.initElements(driver, WriteReviewPage.class);
        homePage = PageFactory.initElements(driver, HomePage.class);
        wait = new WebDriverWait(driver, 20);
        builder = new Actions(driver);
        log.info("Page object initialization finished");
    }

    public boolean isElementPresent(WebElement webElement)
    {
        try {
            if (webElement.isDisplayed()) {
                log.info("Element is visible");
                return true;
            }
            else {
                log.info("Element is not visible");
                return false;
            }
        }
        catch (WebDriverException e)
        {
            log.info("Element not present in current DOM");
            return false;
        }
    }

    public void open(String url) throws MalformedURLException {
        log.debug("Opening wallethub url: "+url);
        URL _url = new URL(url);
        log.info("URL: "+_url);
        driver.navigate().to(_url);
        wait.until(ExpectedConditions.visibilityOf(menuBar.login));
        log.info("Wallethub opened successfully");
    }

    public void login(String email, String password)
    {
        log.debug("Login into the portal with email: "+email+" and password: "+password);
        menuBar.clickLogin();
        wait.until(ExpectedConditions.visibilityOf(loginPage.login));
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();
        wait.until(ExpectedConditions.visibilityOf(menuBar.user));
        log.info("Login successful");
    }

    public void reviewHealthPolicy(int rating, String policy, String reviewText, String expectedConfirmationText)
    {
        log.info("Applying review for "+policy+" policy");
        //Closing unwanted "Join Wallethub" popup
        log.debug("Closing unwanted popup");
        closePopup();

        log.debug("Performing hover on star rating element");
        //Performing hover
        performHoverOnStars();

        log.debug("Checking if stars are lighting up on hover");
        //Asserting if the stars are lit up
        Assert.assertTrue("Stars are not lighting up",testInsuranceCompanyPage.isStarLitUp());

        log.debug("Applying 5 star rating");
        //Applying 5 star review
        testInsuranceCompanyPage.applyRating(rating); //3. applying 5 star review

        log.debug("Writing review for "+policy+" policy with review text:\n"+reviewText);
        wait.until(ExpectedConditions.visibilityOf(writeReviewPage.policyDropdown));
        writeReviewPage.clickPolicy();
        wait.until(ExpectedConditions.visibilityOf(writeReviewPage.policy));
        writeReviewPage.selectPolicy(policy);
        wait.until(ExpectedConditions.invisibilityOf(writeReviewPage.loadingImage));
        writeReviewPage.applyRating(5);
        writeReviewPage.setReview(reviewText);
        writeReviewPage.clickSubmit();
        wait.until(ExpectedConditions.visibilityOf(writeReviewPage.confirmation));
        String actualConfirmationMessage = writeReviewPage.getConfirmationText();
        log.debug("Verifying review post status");
        if(actualConfirmationMessage.contains(expectedConfirmationText))
            Assert.assertTrue(true);
        else
            Assert.assertTrue("Review not submitted",false);
        log.info("Review posted successfully");
    }

    private void performHoverOnStars() {
        //Hovering on rating choices to show rating stars
        builder.moveToElement(testInsuranceCompanyPage.ratingChoice).build().perform();

        for(int i =0 ;i<testInsuranceCompanyPage.stars.size();i++) {
            builder.moveToElement(testInsuranceCompanyPage.stars.get(i)).build().perform(); // hovering on ith star
            wait.until(ExpectedConditions.visibilityOf(testInsuranceCompanyPage.stars.get(i)));
        }
    }

    public void navigate(String url) {
        log.info("Navigating to url: "+url);
        driver.navigate().to(url);
        wait.until(ExpectedConditions.visibilityOf(menuBar.creditCards));
    }

    public void closePopup()
    {
        if(isElementPresent(testInsuranceCompanyPage.popupClose)) // closing unwanted "Join wallethub" popup appearing automatically
        {
            testInsuranceCompanyPage.clickPopupClose();
            wait.until(ExpectedConditions.attributeToBe(testInsuranceCompanyPage.popup,"style","display: none;"));
        }
        else if(isElementPresent(testInsuranceCompanyPage.popupOpen))
        {
            testInsuranceCompanyPage.clickPopupOpen();
            wait.until(ExpectedConditions.attributeToBe(testInsuranceCompanyPage.popup,"style","display: block;"));
            testInsuranceCompanyPage.clickPopupClose();
            wait.until(ExpectedConditions.attributeToBe(testInsuranceCompanyPage.popup,"style","display: none;"));
        }
    }

    public void validateNewReview(int oldReviewCount, int newReviewCount, String reviewText) {
        if(newReviewCount > oldReviewCount)
        {
            Assert.assertTrue("Review not added successfully",newReviewCount > oldReviewCount);
            Assert.assertTrue("Review text not matching",reviewPage.findMatchingReview(reviewText));
        }
        else if(newReviewCount == newReviewCount)
        {
            Assert.assertTrue("Review text not matching",reviewPage.findMatchingReview(reviewText));
        }
        else
            Assert.assertTrue("Review not added or review text not matching",false);
    }

    public void waitForVisibilityOf(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitForInvisibilityOf(WebElement webElement) {
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }
}
