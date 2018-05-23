package io.jbehavetutorials.pageobjects.wallethub;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by prasantabiswas on 22/05/18.
 */
public class HomePage {
    @FindBy(how = How.CLASS_NAME, using = "username")
    public WebElement username;

    public String getUsername()
    {
        return username.getText();
    }
}
