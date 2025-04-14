package com.google.samples.apps.nowinandroid.ui;

import com.beust.ah.A;
import dagger.hilt.internal.aggregatedroot.codegen._com_google_samples_apps_nowinandroid_ui_NavigationTest;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

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
                .setDeviceName("ce031713e8c3a03e0c")
                .setApp("C:\\Users\\pole9\\Desktop\\Poli\\Tesi\\App\\nowinandroid\\app\\build\\intermediates\\apk\\demo\\release\\app-demo-release.apk")
                .setAutomationName("UiAutomator2");

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    // Navigation

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

    @Test
    public void navigationBar_multipleBackStackInterests() {
        int maxSwipes = 10;
        int swipes = 0;

        try {
            // Search and select the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
            Assert.assertTrue(interests.isDisplayed(), "'Interests' is not visible!");
            interests.click();

            // Xpath of the view that contains the topics
            String containerXPath = "//android.view.View[@resource-id='interests:topics']";

            // Swipe vertically until the last topic is on screen
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Wear OS\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(containerXPath);
                swipes++;
            }

            // Verify that last topic is displayed and click it
            WebElement last_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Wear OS\"]"));
            Assert.assertTrue(last_topic.isDisplayed(), "Last topic is not displayed!");
            last_topic.click();

            // Search and select the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you.isDisplayed(), "'For you' is not visible!");
            for_you.click();

            // Search and select the 'Interests' tab
            WebElement interests2 = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
            Assert.assertTrue(interests2.isDisplayed(), "'Interests' is not visible!");
            interests.click();

            // Verify that last topic is still opened, then search for the back arrow and click it
            WebElement last_topic_title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Wear OS\"]"));
            Assert.assertTrue(last_topic_title.isDisplayed(), "Last topic title is not displayed!");
            WebElement last_topic_description = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"The latest news on app development for Wear OS.\"]"));
            Assert.assertTrue(last_topic_description.isDisplayed(), "Last topic description is not displayed!");

            WebElement back_arrow = driver.findElement(AppiumBy.accessibilityId("Back"));
            Assert.assertTrue(back_arrow.isDisplayed(), "Back arrow is not displayed!");
            back_arrow.click();

            // Search and select the 'For you' tab
            WebElement for_you2 = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]"));
            Assert.assertTrue(for_you2.isDisplayed(), "'For you' is not visible!");
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void navigatingToTopicFromForYou_showsTopicDetails() {
        int maxSwipes = 20;
        int swipes = 0;

        try {
            // Search and check the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic.click();

            // Swipe until topic tag is found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Headlines is followed\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Click on topic tag
            WebElement topic_tag = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Headlines is followed\"]"));
            Assert.assertTrue(topic_tag.isDisplayed(), "Topic tag is not displayed!");
            topic_tag.click();

            // Verify we are on topic page, then go back
            WebElement topic_title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(topic_title.isDisplayed(), "Topic title is not displayed!");
            WebElement back_arrow = driver.findElement(AppiumBy.accessibilityId("Back"));
            Assert.assertTrue(back_arrow.isDisplayed(), "Back arrow is not displayed!");
            back_arrow.click();

            // Swipe up and uncheck the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]"));
            Assert.assertTrue(sample_topic2.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }


    // News Resource Card

    @Test
    public void newsResourceCard_withDateAndResourceType() {
        int maxSwipes = 20;
        int swipes = 0;

        try {
            // Search and check the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic.click();

            // Swipe until date and resource type of the first resource card is found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"7 Ott 2022 • Article \uD83D\uDCDA\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }
            WebElement date_type = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"7 Ott 2022 • Article \uD83D\uDCDA\"]"));
            Assert.assertTrue(date_type.isDisplayed(), "Date-type is not displayed!");

            // Swipe up and uncheck the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]"));
            Assert.assertTrue(sample_topic2.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void testTopicsChipColorBackground_matchesFollowedState() {
        int maxSwipes = 20;
        int swipes = 0;

        try {
            // Search and check the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic.click();

            // Swipe until topic tag is found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Headlines is followed\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Verify topic tags are correctly followed / not followed
            WebElement topic_tag1 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Headlines is followed\"]"));
            Assert.assertTrue(topic_tag1.isDisplayed(), "Topic tag (1) is not displayed!");

            WebElement topic_tag2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Wear OS is not followed\"]"));
            Assert.assertTrue(topic_tag2.isDisplayed(), "Topic tag (2) is not displayed!");

            WebElement topic_tag3 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Compose is not followed\"]"));
            Assert.assertTrue(topic_tag3.isDisplayed(), "Topic tag (3) is not displayed!");

            // Swipe up and uncheck the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]"));
            Assert.assertTrue(sample_topic2.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }


    // Bookmarks screen (saved)

    @Test
    public void feed_whenHasBookmarks_showsBookmarks() {
        int maxSwipes = 20;
        int swipes = 0;

        try {
            // Search and check the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic.click();

            // Swipe until the first resource's title is found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Bookmark the first resource
            WebElement bookmark1 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));
            Assert.assertTrue(bookmark1.isDisplayed(), "First bookmark is not displayed!");
            bookmark1.click();

            // Swipe until the second resource's title is found
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Bookmark the second resource
            WebElement bookmark2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));
            Assert.assertTrue(bookmark2.isDisplayed(), "First bookmark is not displayed!");
            bookmark2.click();

            // Go to 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            Assert.assertTrue(saved.isDisplayed(), "Saved is not displayed!");
            saved.click();

            // Swipe until the titles of both the bookmarked resources are found
            String saved_view_xpath = "//android.view.View[@resource-id=\"bookmarks:feed\"]";

            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(saved_view_xpath);
                swipes++;
            }

            WebElement saved_title1 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]"));
            Assert.assertTrue(saved_title1.isDisplayed(), "First saved title is not displayed!");

            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(saved_view_xpath);
                swipes++;
            }

            WebElement saved_title2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]"));
            Assert.assertTrue(saved_title2.isDisplayed(), "Second saved title is not displayed!");

            // Go to 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            Assert.assertTrue(for_you.isDisplayed(), "For you is not displayed!");
            for_you.click();

            // Swipe up and un-bookmark resources
            swipes = 0;
            while ((driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() || driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]")).isEmpty()) && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }

            WebElement un_bookmark1 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]"));
            Assert.assertTrue(un_bookmark1.isDisplayed(), "Second un-bookmark is not displayed!");
            un_bookmark1.click();

            swipes = 0;
            while ((driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() || driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]")).isEmpty()) && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }

            WebElement un_bookmark = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]"));
            Assert.assertTrue(un_bookmark.isDisplayed(), "First un-bookmark is not displayed!");
            un_bookmark.click();

            // Swipe up and uncheck the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]"));
            Assert.assertTrue(sample_topic2.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void feed_whenRemovingBookmark_removesBookmark() {
        int maxSwipes = 20;
        int swipes = 0;

        try {
            // Search and check the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic.click();

            // Swipe until the first resource's title is found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Bookmark the first resource
            WebElement bookmark1 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));
            Assert.assertTrue(bookmark1.isDisplayed(), "First bookmark is not displayed!");
            bookmark1.click();

            // Swipe until the second resource's title is found
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Bookmark the second resource
            WebElement bookmark2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));
            Assert.assertTrue(bookmark2.isDisplayed(), "First bookmark is not displayed!");
            bookmark2.click();

            // Go to 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            Assert.assertTrue(saved.isDisplayed(), "Saved is not displayed!");
            saved.click();

            // Un-bookmark
            String saved_view_xpath = "//android.view.View[@resource-id=\"bookmarks:feed\"]";

            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(saved_view_xpath);
                swipes++;
            }

            WebElement un_bookmark1 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]"));
            Assert.assertTrue(un_bookmark1.isDisplayed(), "Un-bookmark (1) is not displayed!");
            un_bookmark1.click();

            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(saved_view_xpath);
                swipes++;
            }

            WebElement un_bookmark2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]"));
            Assert.assertTrue(un_bookmark2.isDisplayed(), "Un-bookmark (2) is not displayed!");
            un_bookmark2.click();

            // Verify it is empty
            WebElement placeholder = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"No saved updates\"]"));
            Assert.assertTrue(placeholder.isDisplayed(), "Placeholder message is not displayed!");

            // Go to 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            Assert.assertTrue(for_you.isDisplayed(), "For you is not displayed!");
            for_you.click();

            // Swipe up and uncheck the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Headlines\"]"));
            Assert.assertTrue(sample_topic2.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void feed_whenHasNoBookmarks_showsEmptyState() {
        try {
            // Go to 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            Assert.assertTrue(saved.isDisplayed(), "Saved is not displayed!");
            saved.click();

            // Verify it is empty
            WebElement placeholder = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"No saved updates\"]"));
            Assert.assertTrue(placeholder.isDisplayed(), "Placeholder message is not displayed!");

            WebElement placeholder2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Updates you save will be stored here\nto read later\"]"));
            Assert.assertTrue(placeholder2.isDisplayed(), "Placeholder message is not displayed!");

            // Go to 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            Assert.assertTrue(for_you.isDisplayed(), "For you is not displayed!");
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }


    // For you

    @Test
    public void topicSelector_whenNoTopicsSelected_showsTopicChipsAndDisabledDoneButton() {
        try {
            // Search 'Done' button and verify it is not enabled
            WebElement done_button = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Done\"]"));
            Assert.assertTrue(done_button.isDisplayed(), "Done button is not displayed!");

            WebElement done_view = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"forYou:feed\"]/android.view.View[2]"));
            Assert.assertFalse(done_view.isEnabled(), "Done view is enabled!");
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void topicSelector_whenSomeTopicsSelected_showsTopicChipsAndEnabledDoneButton() {
        try {
            // Search and check the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic.click();

            // Search 'Done' button and verify it is enabled
            WebElement done_button = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Done\"]"));
            Assert.assertTrue(done_button.isDisplayed(), "Done button is not displayed!");

            WebElement done_view = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"forYou:feed\"]/android.view.View[2]"));
            Assert.assertTrue(done_view.isEnabled(), "Done view is not enabled!");

            // Search and uncheck the 'Headlines' topic
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic2.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void feed_whenNoInterestsSelectionAndLoaded_showsFeed() {
        int maxSwipes = 20;
        int swipes = 0;

        try {
            // Search and check the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic.click();

            // Search and click 'Done' button
            WebElement done_button = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Done\"]"));
            Assert.assertTrue(done_button.isDisplayed(), "Done button is not displayed!");
            done_button.click();

            // Verify that you can see the first news
            WebElement first_news = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]"));
            Assert.assertTrue(first_news.isDisplayed(), "First news is not displayed!");

            // Search and select the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//O0.r0/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[3]"));
            Assert.assertTrue(interests.isDisplayed(), "'Interests' is not visible!");
            interests.click();

            // Swipe to 'Headlines' topic and uncheck it
            String containerXPath = "//android.view.View[@resource-id='interests:topics']";
            while ((driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]")).isEmpty() || driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unfollow interest\"]")).isEmpty()) && swipes < maxSwipes) {
                swipeUp(containerXPath);
                swipes++;
            }

            WebElement unfollow = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unfollow interest\"]"));
            Assert.assertTrue(unfollow.isDisplayed(), "Unfollow button is not displayed!");
            unfollow.click();

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


    // Interests

    @Test
    public void interestsWithTopics_whenTopicsFollowed_showFollowedAndUnfollowedTopicsWithInfo() {
        try {
            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Follow the second topic
            WebElement follow_button = driver.findElement(AppiumBy.xpath("(//android.view.View[@content-desc=\"Follow interest\"])[2]"));
            follow_button.click();

            // Verify the first 3 topics are displayed
            WebElement first_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Accessibility\"]"));
            Assert.assertTrue(first_topic.isDisplayed(), "First topic is not displayed!");

            WebElement second_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Auto\"]"));
            Assert.assertTrue(second_topic.isDisplayed(), "First topic is not displayed!");

            WebElement third_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Studio & Tools\"]"));
            Assert.assertTrue(third_topic.isDisplayed(), "First topic is not displayed!");

            // Verify the unfollow button is present and unfollow the second topic
            WebElement unfollow_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unfollow interest\"]"));
            Assert.assertTrue(unfollow_button.isDisplayed(), "Unfollow button is not displayed!");
            unfollow_button.click();

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }


    // Search

    @Test
    public void searchTextField_isFocused() {
        try {
            // Verify the search button is displayed and click it
            WebElement search_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search\"]"));
            Assert.assertTrue(search_button.isDisplayed(), "Search button is not displayed!");
            search_button.click();

            // Verify the search bar is displayed and focused
            WebElement search_bar = driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"searchTextField\"]"));
            Assert.assertTrue(search_bar.isDisplayed(), "Search bar is not displayed!");
            boolean isFocused = Boolean.parseBoolean(search_bar.getAttribute("focused"));
            Assert.assertTrue(isFocused, "Search bar is not focused!");

            // Click the back button
            WebElement back_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void emptySearchResult_emptyScreenIsDisplayed() {
        try {
            // Open search
            WebElement search_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search\"]"));
            search_button.click();

            // Search something that gives an empty result
            WebElement search_bar = driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"searchTextField\"]"));
            search_bar.sendKeys("qw");

            // Verify that the placeholder message is displayed
            WebElement placeholder_message = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Try another search or explorer Interests to browse topics\"]"));
            Assert.assertTrue(placeholder_message.isDisplayed(), "Placeholder message is not displayed!");

            // Click the back button
            WebElement back_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void emptySearchResult_nonEmptyRecentSearches_emptySearchScreenAndRecentSearchesAreDisplayed() {
        try {
            // Open search
            WebElement search_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search\"]"));
            search_button.click();

            // Search 'kotlin'
            WebElement search_bar = driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"searchTextField\"]"));
            search_bar.sendKeys("kotlin");
            driver.pressKey(new KeyEvent(AndroidKey.ENTER));

            // Click the back button
            WebElement back_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button.click();

            // Open search
            WebElement search_button2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search\"]"));
            search_button2.click();

            // Search something that gives an empty result
            WebElement search_bar2 = driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"searchTextField\"]"));
            search_bar2.sendKeys("qw");

            // Verify that clear searches and 'kotlin' recent search are displayed
            WebElement clear_searches = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Clear searches\"]"));
            Assert.assertTrue(clear_searches.isDisplayed(), "Clear searches is not displayed!");
            WebElement old_search = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"kotlin\"]"));
            Assert.assertTrue(old_search.isDisplayed(), "Old search 'kotlin' is not displayed!");

            // Verify that the placeholder message is displayed
            WebElement placeholder_message = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Try another search or explorer Interests to browse topics\"]"));
            Assert.assertTrue(placeholder_message.isDisplayed(), "Placeholder message is not displayed!");

            // Clear recent searches
            clear_searches.click();

            // Click the back button
            WebElement back_button2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void searchResultWithTopics_allTopicsAreVisible_followButtonsVisibleForTheNumOfFollowedTopics() {
        try {
            // Open search
            WebElement search_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search\"]"));
            search_button.click();

            // Search 'kotlin'
            WebElement search_bar = driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"searchTextField\"]"));
            search_bar.sendKeys("kotlin");

            // Verify follow button is displayed and click it
            WebElement follow_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Follow interest\"]"));
            Assert.assertTrue(follow_button.isDisplayed(), "Follow button is not displayed!");
            follow_button.click();

            // Verify unfollow button is displayed and click it
            WebElement unfollow_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unfollow interest\"]"));
            Assert.assertTrue(unfollow_button.isDisplayed(), "Unfollow button is not displayed!");
            unfollow_button.click();

            // Click the back button
            WebElement back_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void searchResultWithNewsResources_firstNewsResourcesIsVisible() {
        try {
            // Open search
            WebElement search_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search\"]"));
            search_button.click();

            // Search 'sa'
            WebElement search_bar = driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"searchTextField\"]"));
            search_bar.sendKeys("sa");
            driver.pressKey(new KeyEvent(AndroidKey.ENTER));

            // Verify the first news is displayed
            WebElement first_news = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Privacy Sandbox developer preview 5 \uD83D\uDD10\"]"));
            Assert.assertTrue(first_news.isDisplayed());

            // Clear search text and recent search
            WebElement clear_text = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Clear search text\"]"));
            Assert.assertTrue(clear_text.isDisplayed(), "Clear search text button is not displayed!");
            clear_text.click();
            WebElement clear_searches = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Clear searches\"]"));
            Assert.assertTrue(clear_searches.isDisplayed(), "Clear searches is not displayed!");
            clear_searches.click();

            // Click the back button
            WebElement back_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void emptyQuery_notEmptyRecentSearches_verifyClearSearchesButton_displayed() {
        try {
            // Open search
            WebElement search_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search\"]"));
            search_button.click();

            // Search 'kotlin' and 'testing'
            WebElement search_bar = driver.findElement(AppiumBy.xpath("//android.widget.EditText[@resource-id=\"searchTextField\"]"));
            search_bar.sendKeys("kotlin");
            driver.pressKey(new KeyEvent(AndroidKey.ENTER));
            WebElement clear_text = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Clear search text\"]"));
            clear_text.click();
            search_bar.sendKeys("testing");
            driver.pressKey(new KeyEvent(AndroidKey.ENTER));

            // Click the back button
            WebElement back_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button.click();

            // Open search
            WebElement search_button2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search\"]"));
            search_button2.click();

            // Verify that clear searches, 'kotlin' and 'testing' recent searches are displayed
            WebElement clear_searches = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Clear searches\"]"));
            Assert.assertTrue(clear_searches.isDisplayed(), "Clear searches is not displayed!");
            WebElement old_search1 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"kotlin\"]"));
            Assert.assertTrue(old_search1.isDisplayed(), "Old search 'kotlin' is not displayed!");
            WebElement old_search2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"testing\"]"));
            Assert.assertTrue(old_search2.isDisplayed(), "Old search 'testing' is not displayed!");

            // Clear recent searches
            clear_searches.click();

            // Click the back button
            WebElement back_button2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }


    // Settings

    // This test is written for a device that does NOT support dynamic colors
    @Test
    public void whenStateIsSuccess_allDefaultSettingsAreDisplayed() {
        try {
            // Open settings
            WebElement settings_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            settings_button.click();

            // Check that all the possible settings are displayed
            WebElement default_theme = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Default\"]"));
            Assert.assertTrue(default_theme.isDisplayed(), "Default theme is not displayed!");
            WebElement android_theme = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android\"]"));
            Assert.assertTrue(android_theme.isDisplayed(), "Android theme is not displayed!");
            WebElement default_mode = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"System default\"]"));
            Assert.assertTrue(default_mode.isDisplayed(), "Default mode is not displayed!");
            WebElement light_mode = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Light\"]"));
            Assert.assertTrue(light_mode.isDisplayed(), "Light mode is not displayed!");
            WebElement dark_mode = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Dark\"]"));
            Assert.assertTrue(dark_mode.isDisplayed(), "Dark mode is not displayed!");

            // Verify the default options are selected
            WebElement default_theme_view = driver.findElement(AppiumBy.xpath("//android.widget.ScrollView/android.view.View[1]/android.view.View[1]"));
            WebElement default_mode_view = driver.findElement(AppiumBy.xpath("//android.widget.ScrollView/android.view.View[2]/android.view.View[1]"));

            boolean is_theme_checked = Boolean.parseBoolean(default_theme_view.getAttribute("checked"));
            boolean is_mode_checked = Boolean.parseBoolean(default_mode_view.getAttribute("checked"));

            Assert.assertTrue(is_theme_checked && is_mode_checked, "Default options are not checked");

            // Click 'OK' to go back
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            ok.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    // This test is written for a device that does support dynamic colors
    @Test
    public void whenStateIsSuccess_supportsDynamicColor_usesDefaultBrand_DynamicColorOptionIsDisplayed() {
        try {
            // Open settings
            WebElement settings_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            settings_button.click();

            // Verify dynamic color options are displayed
            WebElement dynamic_color_title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Use Dynamic Color\"]"));
            Assert.assertTrue(dynamic_color_title.isDisplayed(), "Dynamic color title is not displayed!");
            WebElement dynamic_color_yes = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Yes\"]"));
            Assert.assertTrue(dynamic_color_yes.isDisplayed(), "'Yes' option is not displayed!");
            WebElement dynamic_color_no = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"No\"]"));
            Assert.assertTrue(dynamic_color_no.isDisplayed(), "'No' option is not displayed!");

            // Verify the 'No' option is selected
            WebElement no_view = driver.findElement(AppiumBy.xpath("//android.widget.ScrollView/android.view.View[2]/android.view.View[2]"));
            boolean is_no_checked = Boolean.parseBoolean(no_view.getAttribute("checked"));
            Assert.assertTrue(is_no_checked, "'No' is not selected");

            // Click 'OK' to go back
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            ok.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    // This test is written for a device that does NOT support dynamic colors
    @Test
    public void whenStateIsSuccess_notSupportDynamicColor_DynamicColorOptionIsNotDisplayed() {
        try {
            // Open settings
            WebElement settings_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            settings_button.click();

            // Verify dynamic color options are not displayed
            List<WebElement> dynamic_color_title = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Use Dynamic Color\"]"));
            Assert.assertTrue(dynamic_color_title.isEmpty(), "Dynamic color title is displayed!");
            List<WebElement> dynamic_color_yes = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Yes\"]"));
            Assert.assertTrue(dynamic_color_yes.isEmpty(), "'Yes' option is displayed!");
            List<WebElement> dynamic_color_no = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"No\"]"));
            Assert.assertTrue(dynamic_color_no.isEmpty(), "'No' option is displayed!");

            // Click 'OK' to go back
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            ok.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    // This test is written for a device that does support dynamic colors
    @Test
    public void whenStateIsSuccess_usesAndroidBrand_DynamicColorOptionIsNotDisplayed() {
        try {
            // Open settings
            WebElement settings_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            settings_button.click();

            // Select theme: Android
            WebElement android_theme = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android\"]"));
            android_theme.click();

            // Verify dynamic color options are not displayed
            List<WebElement> dynamic_color_title = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Use Dynamic Color\"]"));
            Assert.assertTrue(dynamic_color_title.isEmpty(), "Dynamic color title is displayed!");
            List<WebElement> dynamic_color_yes = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Yes\"]"));
            Assert.assertTrue(dynamic_color_yes.isEmpty(), "'Yes' option is displayed!");
            List<WebElement> dynamic_color_no = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"No\"]"));
            Assert.assertTrue(dynamic_color_no.isEmpty(), "'No' option is displayed!");

            // Select theme: default
            WebElement default_theme = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Default\"]"));
            default_theme.click();

            // Click 'OK' to go back
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            ok.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void whenStateIsSuccess_allLinksAreDisplayed() {
        try {
            // Open settings
            WebElement settings_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            settings_button.click();

            // Verify all the links are displayed
            WebElement privacy_policy_link = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Privacy policy\"]"));
            Assert.assertTrue(privacy_policy_link.isDisplayed(), "'Privacy Policy' link is not displayed!");
            WebElement licenses_link = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Licenses\"]"));
            Assert.assertTrue(licenses_link.isDisplayed(), "'Licenses' link is not displayed!");
            WebElement brand_guidelines_link = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Brand Guidelines\"]"));
            Assert.assertTrue(brand_guidelines_link.isDisplayed(), "'Brand Guidelines' link is not displayed!");
            WebElement feedback_link = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Feedback\"]"));
            Assert.assertTrue(feedback_link.isDisplayed(), "'Feedback' link is not displayed!");

            // Click 'OK' to go back
            WebElement ok = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"OK\"]"));
            ok.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }


    // Topic

    @Test
    public void topicTitle_whenTopicIsSuccess_isShown() {
        try {
            // Go to 'Interests' tab
            WebElement interests_tab = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests_tab.click();

            // Click topic 'Accessibility'
            WebElement topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Accessibility\"]"));
            topic.click();

            // Verify title and description are displayed
            WebElement title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Accessibility\"]"));
            Assert.assertTrue(title.isDisplayed(), "Title is not displayed!");
            WebElement description = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"The latest news on accessibility features and services, helping you to improve your app's usability, particularly for users with disabilities.\"]"));
            Assert.assertTrue(description.isDisplayed(), "Description is not displayed!");

            // Click back button
            WebElement back_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button.click();

            // Go to 'For you' tab
            WebElement for_you_tab = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you_tab.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void news_whenSuccessAndTopicIsSuccess_isShown() {
        int maxSwipes = 20;
        int swipes = 0;

        try {
            // Go to 'Interests' tab
            WebElement interests_tab = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests_tab.click();

            // Click topic 'Accessibility'
            WebElement topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Accessibility\"]"));
            topic.click();

            // Xpath of the view that contains the topics
            String containerXPath = "//android.view.View[@resource-id=\"topic:14\"]/android.view.View";

            // Swipe vertically until the first news title is found
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Listen to our major Text to Speech upgrades for 64 bit devices \uD83D\uDCAC\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(containerXPath);
                swipes++;
            }

            WebElement news_title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Listen to our major Text to Speech upgrades for 64 bit devices \uD83D\uDCAC\"]"));
            Assert.assertTrue(news_title.isDisplayed(), "Title is not displayed!");

            // Swipe to the back button and click it
            while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(containerXPath);
                swipes++;
            }
            WebElement back_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Back\"]"));
            back_button.click();

            // Go to 'For you' tab
            WebElement for_you_tab = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you_tab.click();
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
