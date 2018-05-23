package io.jbehavetutorials.steps.weatherapi;

import io.jbehavetutorials.testutility.HelperClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jbehave.core.annotations.*;
import org.junit.Assert;

import java.io.IOException;

/**
 * Created by prasantabiswas on 23/05/18.
 */
public class WeatherAPITest {

    String requestUrl;
    String response;
    HelperClass helper = HelperClass.getInstance();
    Logger logger = LogManager.getLogger(WeatherAPITest.class);

    @Given("I have the API url <url> and city name <city>")
    public void iHaveWeatherAPIUrl(@Named("url") String url, @Named("city") String city) {
        requestUrl = url+city;
        logger.info("Request url: "+requestUrl);
    }

    @When("I send an HTTP GET request to the API")
    public void iSendHTTPGETRequest() {
        try {
            response = helper.sendHttpGet(requestUrl);
            logger.info("Response received: \n"+response);
        }
        catch (IOException e){
            logger.error("Error occurred", e);
        }
    }

    @Then("I shall get the weather report of city <city>")
    public void iShallGetWeatherReport(@Named("city") String city) {
        logger.info("#############Validating response#############");
        Assert.assertTrue("City name not found in response",response.contains(city));
        Assert.assertTrue("Temperature not found in response",response.contains("Temperature"));
        Assert.assertTrue("Humidity not found in response",response.contains("Humidity"));
        Assert.assertTrue("Weather description not found in response",response.contains("WeatherDescription"));
        Assert.assertTrue("Wind speed not found in response",response.contains("WindSpeed"));
        Assert.assertTrue("Wind direction not found in response",response.contains("WindDirectionDegree"));
    }

    @Then("I shall get the validation error message <message>")
    public void iShallGetTheValidationMessage(@Named("message") String message)
    {
        logger.info("#############Validating response#############");
        Assert.assertTrue("Error message does not match",response.contains(message));
    }
}
