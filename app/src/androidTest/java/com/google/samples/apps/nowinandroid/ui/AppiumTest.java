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


    // News Resource Card

    @Test
    public void newsResourceCard_withDateAndResourceType() {
        int maxSwipes = 10;
        int swipes = 0;

        try {
            // Follow the 'Headlines' topic (from the 'For you' screen)
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic.click();

            // Swipe until date and resource type of the first resource card are found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Oct 6, 2022 • Article \uD83D\uDCDA\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }
            WebElement date_type = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Oct 6, 2022 • Article \uD83D\uDCDA\"]"));
            Assert.assertTrue(date_type.isDisplayed(), "Date-type is not displayed!");

            // Swipe up to the top and unfollow the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"What are you interested in?\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void testTopicsChipColorBackground_matchesFollowedState() {
        int maxSwipes = 10;
        int swipes = 0;

        try {
            // Follow the 'Headlines' topic (from the 'For you' screen)
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
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

            // Swipe up to the top and unfollow the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"What are you interested in?\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }


    // Bookmarks (saved)

    @Test
    public void feed_whenHasBookmarks_showsBookmarks() {
        int maxSwipes = 10;
        int swipes = 0;

        try {
            // Follow the 'Headlines' topic (from the 'For you' screen)
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic.click();

            // Swipe until the first resource's title is found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Bookmark the first resource
            WebElement bookmark1 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));

            do {
                bookmark1.click();
            } while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]")).isEmpty());

            // Swipe until the second resource's title is found
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Bookmark the second resource
            WebElement bookmark2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));

            do {
                bookmark2.click();
            } while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]")).isEmpty());

            // Go to the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
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

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you.click();

            // Swipe up and un-bookmark resources
            swipes = 0;
            while ((driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() || driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]")).isEmpty()) && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }

            WebElement un_bookmark1 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]"));
            un_bookmark1.click();

            swipes = 0;
            while ((driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() || driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]")).isEmpty()) && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }

            WebElement un_bookmark2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]"));
            un_bookmark2.click();

            // Swipe up to the top and unfollow the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"What are you interested in?\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void feed_whenRemovingBookmark_removesBookmark() {
        int maxSwipes = 10;
        int swipes = 0;

        try {
            // Follow the 'Headlines' topic (from the 'For you' screen)
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic.click();

            // Swipe until the first resource's title is found
            String news_view_xpath = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Bookmark the first resource
            WebElement bookmark1 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));

            do {
                bookmark1.click();
            } while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]")).isEmpty());

            // Swipe until the second resource's title is found
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(news_view_xpath);
                swipes++;
            }

            // Bookmark the second resource
            WebElement bookmark2 = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Bookmark\"]"));

            do {
                bookmark2.click();
            } while (driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unbookmark\"]")).isEmpty());

            // Go to the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
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

            // Verify the saved tab is empty
            WebElement placeholder = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"No saved updates\"]"));
            Assert.assertTrue(placeholder.isDisplayed(), "Placeholder message is not displayed!");

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
            for_you.click();

            // Swipe up to the top and unfollow the 'Headlines' topic
            swipes = 0;
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"What are you interested in?\"]")).isEmpty() && swipes < maxSwipes) {
                swipeDown(news_view_xpath);
                swipes++;
            }
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
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
            // Go to the 'Saved' tab
            WebElement saved = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Saved\"]"));
            saved.click();

            // Verify it is empty
            WebElement placeholder = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"No saved updates\"]"));
            Assert.assertTrue(placeholder.isDisplayed(), "Placeholder message is not displayed!");

            WebElement placeholder2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Updates you save will be stored here\nto read later\"]"));
            Assert.assertTrue(placeholder2.isDisplayed(), "Placeholder message is not displayed!");

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
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
            // Verify topic chips are displayed
            WebElement topic1 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(topic1.isDisplayed(), "Topic (1) is not displayed!");

            WebElement topic2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"UI\"]"));
            Assert.assertTrue(topic2.isDisplayed(), "Topic (2) is not displayed!");

            WebElement topic3 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Compose\"]"));
            Assert.assertTrue(topic3.isDisplayed(), "Topic (3) is not displayed!");

            // Search 'Done' button
            WebElement done_button = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Done\"]"));
            Assert.assertTrue(done_button.isDisplayed(), "'Done' button is not displayed!");

            // Verify the 'Done' button is not enabled (the view is used, since it is where we can check if enabled is true/false)
            WebElement done_view = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"forYou:feed\"]/android.view.View[2]"));
            Assert.assertFalse(done_view.isEnabled(), "'Done' button (view) is enabled!");
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void topicSelector_whenSomeTopicsSelected_showsTopicChipsAndEnabledDoneButton() {
        try {
            // Follow the 'Headlines' topic (from the 'For you' screen)
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic.click();

            // Verify topic chips are displayed
            WebElement topic1 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            Assert.assertTrue(topic1.isDisplayed(), "Topic (1) is not displayed!");

            WebElement topic2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"UI\"]"));
            Assert.assertTrue(topic2.isDisplayed(), "Topic (2) is not displayed!");

            WebElement topic3 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Compose\"]"));
            Assert.assertTrue(topic3.isDisplayed(), "Topic (3) is not displayed!");

            // Search 'Done' button
            WebElement done_button = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Done\"]"));
            Assert.assertTrue(done_button.isDisplayed(), "'Done' button is not displayed!");

            // Verify the 'Done' button is enabled (the view is used, since it is where we can check if enabled is true/false)
            WebElement done_view = driver.findElement(AppiumBy.xpath("//android.view.View[@resource-id=\"forYou:feed\"]/android.view.View[2]"));
            Assert.assertTrue(done_view.isEnabled(), "'Done' button view is not enabled!");

            // Unfollow the 'Headlines' topic
            WebElement sample_topic2 = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic2.click();
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected error: " + e.getMessage());
        }
    }

    @Test
    public void feed_whenNoInterestsSelectionAndLoaded_showsFeed() {
        int maxSwipes = 10;
        int swipes = 0;

        try {
            // Follow the 'Headlines' topic (from the 'For you' screen)
            WebElement sample_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]"));
            sample_topic.click();

            // Click 'Done' button
            WebElement done_button = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Done\"]"));
            done_button.click();

            // Verify that you can see the first news
            WebElement first_news = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"The new Google Pixel Watch is here  — start building for Wear OS! ⌚\"]"));
            Assert.assertTrue(first_news.isDisplayed(), "First news is not displayed!");

            // Swipe to the second news
            String containerXPath_forYou = "//android.view.View[@resource-id=\"forYou:feed\"]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(containerXPath_forYou);
                swipes++;
            }

            // Verify that you can see the second news
            WebElement second_news = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Dev Summit ’22: Coming to you, online and around the world! ⛰\uFE0F\"]"));
            Assert.assertTrue(second_news.isDisplayed(), "Second news is not displayed!");

            // Go to the 'Interests' tab
            WebElement interests = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests.click();

            // Swipe to 'Headlines' topic and unfollow it
            String containerXPath_interests = "//android.view.View[@resource-id='interests:topics']";
            swipes = 0;
            while ((driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Headlines\"]")).isEmpty() || driver.findElements(AppiumBy.xpath("//android.view.View[@content-desc=\"Unfollow interest button\"]")).isEmpty()) && swipes < maxSwipes) {
                swipeUp(containerXPath_interests);
                swipes++;
            }

            WebElement unfollow = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unfollow interest button\"]"));
            unfollow.click();

            // Go to the 'For you' tab
            WebElement for_you = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"For you\"]"));
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
            WebElement follow_button = driver.findElement(AppiumBy.xpath("(//android.view.View[@content-desc=\"Follow interest button\"])[2]"));
            follow_button.click();

            // Verify the first 3 topics are displayed
            WebElement first_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Accessibility\"]"));
            Assert.assertTrue(first_topic.isDisplayed(), "First topic is not displayed!");

            WebElement second_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Auto\"]"));
            Assert.assertTrue(second_topic.isDisplayed(), "Second topic is not displayed!");

            WebElement third_topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Android Studio & Tools\"]"));
            Assert.assertTrue(third_topic.isDisplayed(), "Third topic is not displayed!");

            // Unfollow the second topic
            WebElement unfollow_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Unfollow interest button\"]"));
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


    // Settings

    // This test is written for a device that does support dynamic colors
    @Test
    public void whenStateIsSuccess_allDefaultSettingsAreDisplayed() {
        try {
            // Open settings
            WebElement settings_button = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Settings\"]"));
            settings_button.click();

            // Check that all the default settings are displayed
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
            WebElement default_mode_view = driver.findElement(AppiumBy.xpath("//android.widget.ScrollView/android.view.View[3]/android.view.View[1]"));

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
        int maxSwipes = 10;
        int swipes = 0;

        try {
            // Go to 'Interests' tab
            WebElement interests_tab = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Interests\"]"));
            interests_tab.click();

            // Click topic 'Accessibility'
            WebElement topic = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Accessibility\"]"));
            topic.click();

            // Swipe vertically until the first news title is found
            String containerXPath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]";
            while (driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text=\"Listen to our major Text to Speech upgrades for 64 bit devices \uD83D\uDCAC\"]")).isEmpty() && swipes < maxSwipes) {
                swipeUp(containerXPath);
                swipes++;
            }

            WebElement news_title = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Listen to our major Text to Speech upgrades for 64 bit devices \uD83D\uDCAC\"]"));
            Assert.assertTrue(news_title.isDisplayed(), "Title is not displayed!");

            // Swipe to the back button and click it
            swipes = 0;
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
