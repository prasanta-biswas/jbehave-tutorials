package io.jbehavetutorials.pageobjects.wallethub;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by prasantabiswas on 22/05/18.
 */
public class LoginPage {
    @FindBy(how = How.NAME, using = "em")
    public WebElement email;

    @FindBy(how = How.NAME, using = "pw")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//label[@class='toggle inline-block small']")
    public WebElement rememberMyEmail;

    @FindBy(how = How.XPATH, using = "//button[@class='btn blue touch-element-cl']")
    public WebElement login;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Forgot Password?')]")
    public WebElement forgotPassword;

    public String getEmail() {
        return email.getAttribute("value");
    }

    public void setEmail(String email) {
        this.email.clear();
        this.email.sendKeys(email);
    }

    public String getPassword() {
        return password.getAttribute("value");
    }

    public void setPassword(String password) {
        this.password.clear();
        this.password.sendKeys(password);
    }


    public void clickRememberMyEmail() {
        rememberMyEmail.click();
    }

    public void clickLogin() {
        login.click();
    }

    public void clickForgotPassword() {
        forgotPassword.click();
    }
}
