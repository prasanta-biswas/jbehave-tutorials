package io.jbehavetutorials.pageobjects.wallethub;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * Created by prasantabiswas on 22/05/18.
 */
public class TestInsuranceCompanyPage {
    @FindBy(how = How.XPATH, using = "//a[@class='whbl-link']")
    public WebElement writeReview;

    @FindBy(how = How.XPATH, using = "//*[@id='wh-body-inner']/div[2]/div[3]/span")
    public WebElement ratingChoice;

    @FindBy(how = How.XPATH, using = ".//*[@id='footer_cta']/span/span/i[2]")
    public WebElement popupClose;

    @FindBy(how = How.XPATH, using = ".//*[@id='footer_cta']/span/span/i[1]")
    public WebElement popupOpen;

    @FindBy(how = How.XPATH, using = ".//*[@id='footer_cta']/div")
    public WebElement popup;

    @FindBy(how = How.XPATH, using = "//*[@id='wh-body-inner']/div[2]/div[3]/div[1]/div/a")
    public List<WebElement> stars;

    public boolean isStarLitUp()
    {
        boolean isStarLitUp = false;
        for(WebElement star: stars)
        {
            if(star.getAttribute("class").equals("hover"))
                isStarLitUp = true;
            else
                isStarLitUp = false;
        }
        return isStarLitUp;
    }

    public void applyRating(int rating)
    {
        stars.get(rating - 1).click();
    }

    public void clickPopupClose()
    {
        popupClose.click();
    }

    public void clickPopupOpen() {
        popupOpen.click();
    }
}
