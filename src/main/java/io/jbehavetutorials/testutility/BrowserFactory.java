package io.jbehavetutorials.testutility;

import io.jbehavetutorials.constants.ConfigPath;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public class BrowserFactory {
    private static String chromeDriverPath= ConfigPath.CHROME_DRIVER_PATH;
    private static String firefoxGeckoDriverPath = ConfigPath.GECKO_DRIVER_PATH;
    private static String ieDriverPath = ConfigPath.IE_DRIVER_PATH;
    private static Logger log = LogManager.getLogger(BrowserFactory.class);
    private static String os;
    private static String browserDownloadFolder;
    static {
        browserDownloadFolder ="tmp"+File.separator+"downloads"+File.separator;
        File file = new File(browserDownloadFolder);
        if(!file.exists())
            file.mkdirs();
        os = System.getProperty("os.name");
        if(StringUtils.containsIgnoreCase(os,"Mac")) {
            firefoxGeckoDriverPath = firefoxGeckoDriverPath + "mac";
            chromeDriverPath = chromeDriverPath+"mac";
        }
        else if(StringUtils.containsIgnoreCase(os,"Linux")) {
            firefoxGeckoDriverPath = firefoxGeckoDriverPath + "linux";
            chromeDriverPath = chromeDriverPath+"linux";
        }
        else if(StringUtils.containsIgnoreCase(os,"Windows"))
        {
            firefoxGeckoDriverPath = firefoxGeckoDriverPath + "win.exe";
            chromeDriverPath = chromeDriverPath+"win.exe";
        }
        else {
            log.info("Unidentified OS Platform. Linux browser driver will be used.");
            firefoxGeckoDriverPath = firefoxGeckoDriverPath + "linux";
            chromeDriverPath = chromeDriverPath+"linux";
        }
    }

    public static WebDriver getDriver(String browser)
    {
        log.debug("Method called: getDriver");
        try
        {
            switch (browser)
            {
                case "chrome":
                    log.info("Launching driver: googlechrome");
                    return createChromeDriver();
                case "firefox":
                    log.info("Launching driver: firefox");
                    return createFirefoxDriver();
                case "ie":
                    log.info("Launching driver: Internet Explorer");
                    return createIEDriver();
                case "safari":
                    log.error("Launching driver: Safari");
                    return createSafariDriver();
                default:
                    log.warn("Invalid driver name. Launching default driver: firefox");
                    return createFirefoxDriver();
            }
        }
        catch(Exception e)
        {
            log.error("Error occurred",e);
            return null;
        }
    }

    private static WebDriver createSafariDriver() {
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setJavascriptEnabled(true);
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        SafariOptions options = new SafariOptions(capability);
        options.useCleanSession(true);
        WebDriver webDriver = new SafariDriver(options);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        return webDriver;
    }

    private static WebDriver createIEDriver() {
        System.setProperty("webdriver.ie.driver", ieDriverPath);
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setJavascriptEnabled(true);
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        InternetExplorerOptions options = new InternetExplorerOptions(capability);
        options.ignoreZoomSettings();
        WebDriver webDriver = new InternetExplorerDriver(options);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        return webDriver;
    }

    private static WebDriver createChromeDriver() throws Exception
    {
        log.debug("Method called: createChromeDriver");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", browserDownloadFolder);
        chromePrefs.put("download.prompt_for_download",false);
        chromePrefs.put("download.directory_upgrade",true);
        chromePrefs.put("safebrowsing.enabled",false);
        chromePrefs.put("safebrowsing.disable_download_protection",true);
        chromePrefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.setAcceptInsecureCerts(true);

        if(Boolean.getBoolean("headless")==true) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1024,768");
            options.addArguments("--test-type");
            options.addArguments("--disable-extensions");
        }
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        return webDriver;
    }

    private static WebDriver createFirefoxDriver() throws Exception
    {
        log.debug("Method called: createFirefoxDriver");
        System.setProperty("webdriver.gecko.driver", firefoxGeckoDriverPath);
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.setProfile(getFirefoxProfile());
        if(Boolean.getBoolean("headless") == true)
            options.setHeadless(true);
        WebDriver webDriver = new FirefoxDriver(options);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        return webDriver;
    }

    public static FirefoxProfile getFirefoxProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", browserDownloadFolder);
        profile.setPreference("browser.helperApps.neverAsk.openFile",
                "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/x-xls,application/xls,application/x-dos_ms_excel,application/x-ms-excel,application/msexcel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/x-xls,application/xls,application/x-dos_ms_excel,application/x-ms-excel,application/msexcel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.focusWhenStarting", false);
        profile.setPreference("browser.download.manager.useWindow", false);
        profile.setPreference("browser.download.manager.showAlertOnComplete", false);
        profile.setPreference("browser.download.manager.closeWhenDone", false);
        return profile;
    }

    public static String getBrowserDownloadFolder()
    {
        return browserDownloadFolder;
    }
}
