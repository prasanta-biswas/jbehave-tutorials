package io.jbehavetutorials.pageobjects.facebook;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public class NewsFeedPage {
    @FindBy(how = How.XPATH, using = "//div[contains(@class,'userContentWrapper')]//div[contains(@class,'userContent')]//p")
    public List<WebElement> newsFeedList;

    public String getNewsFeedText(int rowNumber)
    {
        String newsFeedText = newsFeedList.get(rowNumber).getText();
        return newsFeedText;
    }
}
