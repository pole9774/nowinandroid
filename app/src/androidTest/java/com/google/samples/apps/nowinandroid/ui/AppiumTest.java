package com.google.samples.apps.nowinandroid.ui;

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

            // Swipe up and uncheck topic
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
    public void newsResourceCard_showsCorrectFields() {
        int maxSwipes = 20;
        int swipes = 0;

        try {
            // Search and check the 'Headlines' topic
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(sample_topic.isDisplayed(), "Sample topic 'Headlines' is not visible!");
            sample_topic.click();

            // Swipe until title of the first resource card is found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }
            WebElement title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]"));
            Assert.assertTrue(title.isDisplayed(), "Title is not displayed!");

            // Swipe until bookmark of the first resource card is found
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }
            WebElement bookmark = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));
            Assert.assertTrue(bookmark.isDisplayed(), "Bookmark is not displayed!");

            // Swipe until date-type of the first resource card is found
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"7 Ott 2022 • Article \uD83D\uDCDA\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }
            WebElement date_type = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"7 Ott 2022 • Article \uD83D\uDCDA\"]"));
            Assert.assertTrue(date_type.isDisplayed(), "Date-type is not displayed!");

            // Swipe until description of the first resource card is found
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"We launched the Google Pixel Watch, powered by Wear OS 3.5, at the Made by Google event — the perfect device to showcase apps built with Compose for Wear OS. With Compose for Wear OS, the Tiles Material library, and the tools in Android Studio Dolphin, it’s now simpler and more efficient than ever to make apps for WearOS.\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }
            WebElement description = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"We launched the Google Pixel Watch, powered by Wear OS 3.5, at the Made by Google event — the perfect device to showcase apps built with Compose for Wear OS. With Compose for Wear OS, the Tiles Material library, and the tools in Android Studio Dolphin, it’s now simpler and more efficient than ever to make apps for WearOS.\"]"));
            Assert.assertTrue(description.isDisplayed(), "Description is not displayed!");

            // Swipe up and uncheck topic
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

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
