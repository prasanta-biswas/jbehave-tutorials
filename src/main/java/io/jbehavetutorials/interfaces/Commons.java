package io.jbehavetutorials.interfaces;

import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public interface Commons {
    public void open(String url) throws MalformedURLException;
    public void login(String email, String password);
    public boolean isElementPresent(WebElement element);
}
