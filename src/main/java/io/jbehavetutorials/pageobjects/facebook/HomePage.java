package io.jbehavetutorials.pageobjects.facebook;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public class HomePage {
    @FindBy(how = How.ID, using = "feedx_sprouts_container")
    public WebElement makePost;

    @FindBy(how = How.XPATH, using = "//div[@data-testid='status-attachment-mentions-input']//div[@class='_1mf _1mj']/span")
    public WebElement status;

    @FindBy(how = How.XPATH, using = "//div[@id='feedx_sprouts_container']//button[@data-testid='react-composer-post-button']")
    public WebElement post;

    public String getStatus() {
        return status.getText();
    }

    public void clickStatus() {
        this.makePost.click();
    }

    public void clickPost(){
        this.post.click();
    }
}
