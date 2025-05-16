package com.google.samples.apps.nowinandroid.ui;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class AppiumTest {

    private AndroidDriver driver;

    // Utility function to swipe vertically (the finger goes from bottom to top)
    public void swipeUp(String containerXPath) {
        WebElement container = driver.findElement(AppiumBy.xpath(containerXPath));

        int startX = container.getLocation().getX() + (container.getSize().getWidth() / 2);
        int startY = container.getLocation().getY() + (int) (container.getSize().getHeight() * 0.8);
        int endY = container.getLocation().getY() + (int) (container.getSize().getHeight() * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY)); // Start
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), startX, endY)); // Move
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    // Utility function to swipe vertically (the finger goes top to bottom)
    public void swipeDown(String containerXPath) {
        WebElement container = driver.findElement(AppiumBy.xpath(containerXPath));

        int startX = container.getLocation().getX() + (container.getSize().getWidth() / 2);
        int startY = container.getLocation().getY() + (int) (container.getSize().getHeight() * 0.2);
        int endY = container.getLocation().getY() + (int) (container.getSize().getHeight() * 0.8);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY)); // Start
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), startX, endY)); // Move
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    @BeforeClass
    public void setup() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setApp("C:\\Users\\pole9\\Desktop\\Poli\\Tesi\\App\\nowinandroid\\app\\build\\intermediates\\apk\\demo\\debug\\app-demo-debug.apk")
                .setAutomationName("UiAutomator2");

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Navigation

    @Test
    public void firstScreen_isForYou() {
        try {
            // Search for the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"NiaBottomBar\"]/android.view.View/android.view.View[1]"));
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
            // Follow the 'Headlines' topic (from the 'For you' screen)
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic.click();

            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you.click();

            // Verify the 'Headlines' topic is still followed (the view containing the follow button is used this time, since it is where we can verify if it is checked or not)
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"forYou:topicSelection\"]/android.view.View[1]/android.view.View"));
            boolean isChecked = Boolean.parseBoolean(sample_topic2.getAttribute("checked"));
            Assert.assertTrue(isChecked, "Sample topic 'Headlines' is not checked!");

            // Unfollow the 'Headlines' topic
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void navigationBar_reselectTab_keepsState() {
        try {
            // Follow the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic.click();

            // Click the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you.click();

            // Verify the 'Headlines' topic is still followed (the view containing the follow button is used this time, since it is where we can verify if it is checked or not)
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"forYou:topicSelection\"]/android.view.View[1]/android.view.View"));
            boolean isChecked = Boolean.parseBoolean(sample_topic2.getAttribute("checked"));
            Assert.assertTrue(isChecked, "Sample topic 'Headlines' is not checked!");

            // Unfollow the 'Headlines' topic
            sample_topic2.click();
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
            List<WebElement> back_arrow = driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            Assert.assertTrue(back_arrow.isEmpty(), "Back arrow is displayed!");

            // Go to the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            saved.click();

            // Search for back arrow and verify it is not displayed
            back_arrow = driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            Assert.assertTrue(back_arrow.isEmpty(), "Back arrow is displayed!");

            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Search for back arrow and verify it is not displayed
            back_arrow = driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            Assert.assertTrue(back_arrow.isEmpty(), "Back arrow is displayed!");

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
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

            // Go to the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            saved.click();

            // Search for 'Saved' on the top
            WebElement saved1 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Saved\"])[1]"));
            Assert.assertTrue(saved1.isDisplayed(), "'Saved' (top) is not displayed!");

            // Search for 'Saved' on the bottom
            WebElement saved2 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Saved\"])[2]"));
            Assert.assertTrue(saved2.isDisplayed(), "'Saved' (bottom) is not displayed!");

            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Search for 'Interests' on the top
            WebElement interests1 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Interests\"])[1]"));
            Assert.assertTrue(interests1.isDisplayed(), "'Interests' (top) is not displayed!");

            // Search for 'Interests' on the bottom
            WebElement interests2 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Interests\"])[2]"));
            Assert.assertTrue(interests2.isDisplayed(), "'Interests' (bottom) is not displayed!");

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
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
            WebElement settings = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            Assert.assertTrue(settings.isDisplayed(), "Settings icon (for you) is not displayed!");

            // Go to the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            saved.click();

            // Search the settings icon
            WebElement settings2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            Assert.assertTrue(settings2.isDisplayed(), "Settings icon (saved) is not displayed!");

            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Search the settings icon
            WebElement settings3 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            Assert.assertTrue(settings3.isDisplayed(), "Settings icon (interests) is not displayed!");

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
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
            // Open settings
            WebElement settings = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            settings.click();

            // Verify the brand 'Android' is displayed
            WebElement brand_android = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android\"]"));
            Assert.assertTrue(brand_android.isDisplayed(), "Brand 'Android' is not displayed!");

            // Click 'OK'
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            ok.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void whenSettingsDialogDismissed_previousScreenIsDisplayed() {
        try {
            // Go to the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            saved.click();

            // Open settings
            WebElement settings = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            settings.click();

            // Click 'OK'
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            ok.click();

            // Search for 'Saved' on the top
            WebElement saved1 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Saved\"])[1]"));
            Assert.assertTrue(saved1.isDisplayed(), "'Saved' (top) is not displayed!");

            // Search for 'Saved' on the bottom
            WebElement saved2 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Saved\"])[2]"));
            Assert.assertTrue(saved2.isDisplayed(), "'Saved' (bottom) is not displayed!");

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void homeDestination_back_quitsApp() {
        try {
            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you.click();

            // Click the device's back button, expecting the app to quit
            driver.navigate().back();

            // Verify the app has quit
            String currentPackage = driver.getCurrentPackage();
            Assert.assertNotEquals(currentPackage, "com.google.samples.apps.nowinandroid.demo");
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void navigationBar_backFromAnyDestination_returnsToForYou() {
        try {
            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Go to the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            saved.click();

            // Click the device's back button, expecting the app to quit
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

    @Test
    public void navigationBar_multipleBackStackInterests() {
        int maxSwipes = 10;
        int swipes = 0;

        try {
            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Xpath of the view that contains the topics
            String containerXPath = "//android.view.View[@resource-id='interests:topics']";

            // Swipe vertically until the last topic is on screen
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Wear OS\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(containerXPath);
                swipes++;
            }

            // Click the last topic
            WebElement last_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Wear OS\"]"));
            last_topic.click();

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you.click();

            // Go to the 'Interests' tab
            WebElement interests2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests2.click();

            // Verify that last topic is still opened
            WebElement last_topic_title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Wear OS\"]"));
            Assert.assertTrue(last_topic_title.isDisplayed(), "Last topic title is not displayed!");
            WebElement last_topic_description = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"The latest news on app development for Wear OS.\"]"));
            Assert.assertTrue(last_topic_description.isDisplayed(), "Last topic description is not displayed!");

            // Go back
            WebElement back_arrow = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_arrow.click();

            // Go to the 'For you' tab
            WebElement for_you2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you2.click();
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
