package io.jbehavetutorials.pageobjects.wallethub;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * Created by prasantabiswas on 22/05/18.
 */
public class ReviewPage {
    @FindBy(how = How.XPATH,using = "//div[@class='reviews']/div[not(@class='reviews-none')]")
    public List<WebElement> reviews;

    public int getReviewCount()
    {
        return reviews.size();
    }

    public String getReviewText(int row)
    {
        return reviews.get(row - 1).findElement(By.tagName("p")).getText();
    }

    public boolean findMatchingReview(String expectedReviewText) {
        String actualReviewText;
        for(WebElement review : reviews)
        {
            actualReviewText = review.findElement(By.tagName("p")).getText();
            if(actualReviewText.equals(expectedReviewText))
                return true;
        }
        return false;
    }
}
