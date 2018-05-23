package io.jbehavetutorials.pageobjects.facebook;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public class HeaderPage {
    @FindBy(how = How.ID, using = "userNavigationLabel")
    public WebElement accountSettings;

    @FindBy(how = How.XPATH, using = "//a[@data-gt='{\"ref\":\"async_menu\",\"logout_menu_click\":\"menu_logout\"}']")
    public WebElement logout;

    public void clickAccountSetting() throws WebDriverException {
        accountSettings.click();
    }

    public void clickLogout() throws WebDriverException {
        logout.click();
    }
}
