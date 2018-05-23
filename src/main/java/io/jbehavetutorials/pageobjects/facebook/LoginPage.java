package io.jbehavetutorials.pageobjects.facebook;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public final class LoginPage {

    @FindBy(how = How.ID, using = "email")
    public WebElement email;

    @FindBy(how = How.ID, using = "pass")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//label[@id='loginbutton']//input[@type='submit']")
    public WebElement logIn;

    public String getEmail() throws WebDriverException {
        return email.getAttribute("value");
    }

    public void setEmail(String email) throws WebDriverException {
        this.email.clear();
        this.email.sendKeys(email);
    }

    public String getPassword() throws WebDriverException {
        return password.getAttribute("value");
    }

    public void setPassword(String password) throws WebDriverException {
        this.password.clear();
        this.password.sendKeys(password);
    }

    public void clickLogin() throws WebDriverException {
        logIn.click();
    }

}
