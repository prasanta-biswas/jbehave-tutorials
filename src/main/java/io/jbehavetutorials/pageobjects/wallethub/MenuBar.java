package io.jbehavetutorials.pageobjects.wallethub;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by prasantabiswas on 22/05/18.
 */
public class MenuBar {
    @FindBy(how = How.CLASS_NAME, using = "login")
    public WebElement login;

    @FindBy(how = How.CLASS_NAME, using = "joinforfree")
    public WebElement joinNow;

    @FindBy(how = How.XPATH, using = "//a[contains(@class,'user') and @data-menu='m-user']")
    public WebElement user;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Credit Cards')]")
    public WebElement creditCards;

    public void clickLogin()
    {
        login.click();
    }

    public void clickJoinNow()
    {
        joinNow.click();
    }

    public void clickUser()
    {
        user.click();
    }

    public String getUserName()
    {
        return user.getText();
    }

    public void clickCreditCards() {
        creditCards.click();
    }
}
