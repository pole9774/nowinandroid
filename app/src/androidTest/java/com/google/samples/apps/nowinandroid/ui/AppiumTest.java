package com.google.samples.apps.nowinandroid.ui;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class AppiumTest {

    private AndroidDriver driver;

    @BeforeClass
    public void setup() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("ce031713e8c3a03e0c")
                .setApp("C:\\Users\\pole9\\Desktop\\Poli\\Tesi\\App\\nowinandroid\\app\\build\\intermediates\\apk\\demo\\release\\app-demo-release.apk")
                .setAutomationName("UiAutomator2");

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void firstScreen_isForYou() {
        try {
            // Search for the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");

            // Verify that 'For you' is selected
            Assert.assertTrue(for_you.isSelected(), "'For you' is not selected!");
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void navigationBar_navigateToPreviouslySelectedTab_restoresContent() {
        try {
            // Search for the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");

            // Check the 'Headlines' topic (by clicking on it)
            sample_topic.click();

            // Search for the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
            Assert.assertTrue(interests.isDisplayed(), "'Interests' is not visible!");

            // Select the 'Interests' tab (by clicking on it)
            interests.click();

            // Verify that 'Interests' is selected
            Assert.assertTrue(interests.isSelected(), "'Interests' is not selected!");

            // Search for the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");

            // Select the 'For you' tab (by clicking on it)
            for_you.click();

            // Verify that 'For you' is selected
            Assert.assertTrue(for_you.isSelected(), "'For you' is not selected!");

            // Search for the 'Headlines' topic (it searches the view this time, since it is where we can verify if it is checked or not)
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"forYou:topicSelection\"]/android.view.View[1]/android.view.View"));
            Assert.assertTrue(sample_topic2.isDisplayed(), "Sample topic 'Headlines' is not visible!");

            // Verify that the 'Headlines' topic is checked
            boolean isChecked = Boolean.parseBoolean(sample_topic2.getAttribute("checked"));
            Assert.assertTrue(isChecked, "Sample topic 'Headlines' is not checked!");

            // Uncheck the 'Headlines' topic (by clicking on it)
            sample_topic2.click();

            // Verify that the 'Headlines' topic is unchecked
            isChecked = Boolean.parseBoolean(sample_topic2.getAttribute("checked"));
            Assert.assertFalse(isChecked, "Sample topic 'Headlines' is checked!");
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void navigationBar_reselectTab_keepsState() {
        try {
            // Search for the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");

            // Check the 'Headlines' topic (by clicking on it)
            sample_topic.click();

            // Search for the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");

            // Select the 'For you' tab (by clicking on it)
            for_you.click();

            // Search for the 'Headlines' topic (it searches the view this time, since it is where we can verify if it is checked or not)
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"forYou:topicSelection\"]/android.view.View[1]/android.view.View"));
            Assert.assertTrue(sample_topic2.isDisplayed(), "Sample topic 'Headlines' is not visible!");

            // Verify that the 'Headlines' topic is checked
            boolean isChecked = Boolean.parseBoolean(sample_topic2.getAttribute("checked"));
            Assert.assertTrue(isChecked, "Sample topic 'Headlines' is not checked!");

            // Uncheck the 'Headlines' topic (by clicking on it)
            sample_topic2.click();

            // Verify that the 'Headlines' topic is unchecked
            isChecked = Boolean.parseBoolean(sample_topic2.getAttribute("checked"));
            Assert.assertFalse(isChecked, "Sample topic 'Headlines' is checked!");
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void topLevelDestinations_doNotShowBackArrow() {
        try {
            // Search for back arrow and verify it is not displayed
            List<WebElement> back_arrow = driver.findElements(AppiumBy.accessibilityId("Back"));
            Assert.assertTrue(back_arrow.isEmpty(), "Back arrow is displayed!");

            // Search and select the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]"));
            Assert.assertTrue(saved.isDisplayed(), "'Saved' is not visible!");
            saved.click();

            // Search for back arrow and verify it is not displayed
            back_arrow = driver.findElements(AppiumBy.accessibilityId("Back"));
            Assert.assertTrue(back_arrow.isEmpty(), "Back arrow is displayed!");

            // Search and select the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
            Assert.assertTrue(interests.isDisplayed(), "'Interests' is not visible!");
            interests.click();

            // Search for back arrow and verify it is not displayed
            back_arrow = driver.findElements(AppiumBy.accessibilityId("Back"));
            Assert.assertTrue(back_arrow.isEmpty(), "Back arrow is displayed!");

            // Search and select the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void topLevelDestinations_showTopBarWithTitle() {
        try {
            // Search for the app name on the top
            WebElement app_name = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Now in Android\"]"));
            Assert.assertTrue(app_name.isDisplayed(), "App Name is not displayed!");

            // Search and select the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]"));
            Assert.assertTrue(saved.isDisplayed(), "'Saved' is not visible!");
            saved.click();

            // Search for 'Saved' on the top
            WebElement saved1 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Saved\"])[1]"));
            Assert.assertTrue(saved1.isDisplayed(), "'Saved' (1) is not displayed!");

            // Search for 'Saved' on the bottom
            WebElement saved2 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Saved\"])[2]"));
            Assert.assertTrue(saved2.isDisplayed(), "'Saved' (2) is not displayed!");

            // Search and select the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
            Assert.assertTrue(interests.isDisplayed(), "'Interests' is not visible!");
            interests.click();

            // Search for 'Interests' on the top
            WebElement interests1 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Interests\"])[1]"));
            Assert.assertTrue(interests1.isDisplayed(), "'Interests' (1) is not displayed!");

            // Search for 'Interests' on the bottom
            WebElement interests2 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Interests\"])[2]"));
            Assert.assertTrue(interests2.isDisplayed(), "'Interests' (2) is not displayed!");

            // Search and select the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void topLevelDestinations_showSettingsIcon() {
        try {
            // Search the settings icon
            WebElement settings = driver.findElement(AppiumBy.accessibilityId("Settings"));
            Assert.assertTrue(settings.isDisplayed(), "Settings icon (for you) is not displayed!");

            // Search and select the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]"));
            Assert.assertTrue(saved.isDisplayed(), "'Saved' is not visible!");
            saved.click();

            // Search the settings icon
            WebElement settings2 = driver.findElement(AppiumBy.accessibilityId("Settings"));
            Assert.assertTrue(settings2.isDisplayed(), "Settings icon (saved) is not displayed!");

            // Search and select the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
            Assert.assertTrue(interests.isDisplayed(), "'Interests' is not visible!");
            interests.click();

            // Search the settings icon
            WebElement settings3 = driver.findElement(AppiumBy.accessibilityId("Settings"));
            Assert.assertTrue(settings3.isDisplayed(), "Settings icon (interests) is not displayed!");

            // Search and select the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void whenSettingsIconIsClicked_settingsDialogIsShown() {
        try {
            // Search and click the settings icon
            WebElement settings = driver.findElement(AppiumBy.accessibilityId("Settings"));
            Assert.assertTrue(settings.isDisplayed(), "Settings icon (for you) is not displayed!");
            settings.click();

            // Verify the brand 'Android' is displayed
            WebElement brand_android = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android\"]"));
            Assert.assertTrue(brand_android.isDisplayed(), "Brand 'Android' is not displayed!");

            // Search 'OK' and click it
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            Assert.assertTrue(ok.isDisplayed(), "'OK' is not displayed!");
            ok.click();

            // Search and select the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void whenSettingsDialogDismissed_previousScreenIsDisplayed() {
        try {
            // Search and select the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]"));
            Assert.assertTrue(saved.isDisplayed(), "'Saved' is not visible!");
            saved.click();

            // Search and click the settings icon
            WebElement settings = driver.findElement(AppiumBy.accessibilityId("Settings"));
            Assert.assertTrue(settings.isDisplayed(), "Settings icon (for you) is not displayed!");
            settings.click();

            // Search 'OK' and click it
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            Assert.assertTrue(ok.isDisplayed(), "'OK' is not displayed!");
            ok.click();

            // Search for 'Saved' on the top
            WebElement saved_title = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Saved\"])[1]"));
            Assert.assertTrue(saved_title.isDisplayed(), "'Saved' title is not displayed!");

            // Search and select the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

//    @Test
//    public void homeDestination_back_quitsApp() {
//        try {
//            // Search and select the 'Interests' tab
//            WebElement interests = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
//            Assert.assertTrue(interests.isDisplayed(), "'Interests' is not visible!");
//            interests.click();
//
//            // Search and select the 'For you' tab
//            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
//            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");
//            for_you.click();
//
//            // Clicks back, expecting the app to quit
//            driver.navigate().back();
//
//            // Verify the app has quit
//            String currentPackage = driver.getCurrentPackage();
//            System.out.println(currentPackage);
//            Assert.assertNotEquals(currentPackage, "com.google.samples.apps.nowinandroid.demo");
//        } catch (NoSuchElementException e) {
//            Assert.fail("Element not found: " + e.getMessage());
//        } catch (Exception e) {
//            Assert.fail("Unexpected error: " + e.getMessage());
//        }
//    }

    @Test
    public void navigationBar_backFromAnyDestination_returnsToForYou() {
        try {
            // Search and select the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
            Assert.assertTrue(interests.isDisplayed(), "'Interests' is not visible!");
            interests.click();

            // Search and select the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]"));
            Assert.assertTrue(saved.isDisplayed(), "'Saved' is not visible!");
            saved.click();

            // Clicks back
            driver.navigate().back();

            // Verify we are in 'For you'
            WebElement app_name = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Now in Android\"]"));
            Assert.assertTrue(app_name.isDisplayed(), "App Name is not displayed!");
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
