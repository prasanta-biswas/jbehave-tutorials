package io.jbehavetutorials.pageobjects.wallethub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * Created by prasantabiswas on 22/05/18.
 */
public class WriteReviewPage {
    @FindBy(how = How.CLASS_NAME, using = "drop-arrow")
    public WebElement policyDropdown;

    @FindBy(how = How.XPATH, using = "//ul[contains(@class,'drop-el')]")
    public WebElement policy;

    @FindBy(how = How.ID, using = "review-content")
    public WebElement writeReview;

    @FindBy(how = How.XPATH, using = "//input[@class='btn blue']")
    public WebElement submit;

    @FindBy(how = How.TAG_NAME, using = "h1")
    public WebElement confirmation;

    @FindBy(how = How.CLASS_NAME, using = "loading-image")
    public WebElement loadingImage;

    @FindBy(how = How.XPATH, using = "//span[@id='overallRating']/a")
    public List<WebElement> stars;

    public void clickPolicy()
    {
        policyDropdown.click();
    }

    public void selectPolicy(String policy)
    {
        this.policy.findElement(By.linkText(policy)).click();
    }

    public String getReview()
    {
        return writeReview.getAttribute("value");
    }

    public void setReview(String reviewText)
    {
        writeReview.clear();
        writeReview.sendKeys(reviewText);
    }

    public String getConfirmationText()
    {
        return confirmation.getText();
    }

    public void applyRating(int rating)
    {
        stars.get(rating - 1).click();
    }

    public  void clickSubmit()
    {
        submit.click();
    }
}
