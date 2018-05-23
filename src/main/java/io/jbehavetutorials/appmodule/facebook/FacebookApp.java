package io.jbehavetutorials.appmodule.facebook;

import io.jbehavetutorials.interfaces.Commons;
import io.jbehavetutorials.pageobjects.facebook.HeaderPage;
import io.jbehavetutorials.pageobjects.facebook.HomePage;
import io.jbehavetutorials.pageobjects.facebook.LoginPage;
import io.jbehavetutorials.pageobjects.facebook.NewsFeedPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * Created by prasantabiswas on 21/05/18.
 */
public final class FacebookApp implements Commons {
    private static Logger log = LogManager.getLogger(FacebookApp.class);
    private final WebDriverWait wait;
    public final LoginPage loginPage;
    public final HeaderPage headerPage;
    public final HomePage homePage;
    public final NewsFeedPage newsFeedPage;
    private final WebDriver driver;

    public FacebookApp(WebDriver driver)
    {
        log.info("Initializing page objects...");
        this.driver = driver;
        loginPage = PageFactory.initElements(this.driver, LoginPage.class);
        headerPage = PageFactory.initElements(this.driver, HeaderPage.class);
        homePage = PageFactory.initElements(this.driver, HomePage.class);
        newsFeedPage = PageFactory.initElements(this.driver, NewsFeedPage.class);
        wait = new WebDriverWait(driver, 20);
        log.info("Page object initialization finished");
    }

    public void open(String url) throws MalformedURLException {
        log.debug("Opening facebook url: "+url);
        URL _url = new URL(url);
        log.info("URL: "+_url);
        driver.navigate().to(_url);
        wait.until(ExpectedConditions.visibilityOf(loginPage.logIn));
        log.info("Facebook opened successfully");
    }

    public void login(String email, String password)
    {
        log.debug("Login into the portal with email: "+email+" and password: "+password);
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLogin();
    }

    public void postStatus() {
        homePage.clickPost();
        wait.until(ExpectedConditions.elementSelectionStateToBe(homePage.status,false));
        log.info("Status posted successfully");
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

    public void putStatusMessage(String message) {
        log.info("Putting status message: "+message);
        homePage.clickStatus();
        new Actions(driver).moveToElement(homePage.status).sendKeys(message).perform();
    }

    public void waitForLogin() {
        wait.until(ExpectedConditions.visibilityOf(headerPage.accountSettings));
    }
}
