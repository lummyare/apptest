package com.test.apptest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AppTest {

    AppiumDriver driver;

    MobileElement appTitle;

    @Before
    public void setup() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Moto");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.vending");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.finsky.activities.MainActivity");
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), desiredCapabilities);
    }

    @Test
    public void testGooglePlayApp() throws InterruptedException {
        String appName = "Amazon Now - Grocery Shopping";

        //How to scroll to specific text
        MobileElement scrollToText = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + appName + "\"));"));
        scrollToText.click();

        // Verifying the app detail page
        appTitle = (MobileElement) driver.findElementById("com.android.vending:id/title_title");

        Assert.assertTrue(appName.equals(appTitle.getText().trim()));

        driver.navigate().back();

        //Clicking the search bar icon

        MobileElement scrollToElement = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().description(\"Search\"));"));
        scrollToElement.click();


        MobileElement editText = (MobileElement) driver.findElementById("com.android.vending:id/search_box_text_input");

        editText.sendKeys(appName);

        Thread.sleep(1000);

        List<MobileElement> listOfSuggestedResults = driver.findElementsById("com.android.vending:id/suggest_text");

        for (MobileElement element : listOfSuggestedResults) {
            if (appName.equals(element.getText().trim())) {
                element.click();
                break;
            }
        }

        appTitle = (MobileElement) driver.findElementById("com.android.vending:id/title_title");

        Assert.assertTrue(appName.equals(appTitle.getText().trim()));

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
