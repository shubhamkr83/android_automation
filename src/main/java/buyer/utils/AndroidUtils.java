// AndroidUtils.java
package buyer.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AndroidUtils {

	private static final Logger logger = LogManager.getLogger(AndroidUtils.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("AndroidUtils class initialized ✅");
	}

	protected final AndroidDriver driver;
	private static final String APP_PACKAGE = "com.sot.bizup.debug";
	private static final Duration DEFAULT_WAIT = Duration.ofSeconds(10);

	public AndroidUtils(AndroidDriver driver) {
		this.driver = driver;
	}

	public void launchApp() {
		try {
			driver.activateApp(APP_PACKAGE);
			Thread.sleep(DEFAULT_WAIT.toMillis());
			logger.info("App Launched ✅");
		} catch (Exception e) {
			logger.error("App Launch Failed ❌: " + e.getMessage());
			throw new RuntimeException("Failed to launch app", e);
		}

	}

	public void RestartApp() {
		try {
			driver.terminateApp(APP_PACKAGE);
			driver.activateApp(APP_PACKAGE);
			Thread.sleep(DEFAULT_WAIT.toMillis());
			logger.info("App Restarted ✅");

		} catch (Exception e) {
			logger.error("App Restart Failed ❌: " + e.getMessage());
			throw new RuntimeException("Failed to restart app", e);
		}

	}

	protected void waitForStability() {
		try {
			Thread.sleep(2000); // Base stability wait
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	// Back Method
	public void Back() {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}

	// Enter Method
	public void Enter() {
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
	}

	// Clear App Data Method
	public void clearAppData() {
		String appPackage = "com.sot.bizup.debug";
		ProcessBuilder processBuilder = new ProcessBuilder("adb", "shell", "pm", "clear", appPackage);

		try {
			Process process = processBuilder.start();
			int exitCode = process.waitFor();

			if (exitCode == 0) {
				logger.info("App Data Cleared ✅");
			} else {
				logger.error("App Data Not Cleared ❌");
			}
		} catch (Exception e) {
			logger.error("App Data Not Cleared ❌");
			e.printStackTrace();
		}
	}

	// Click method for ID
	public void ClickId(String element) {
		driver.findElement(By.id(element)).click();
	}

	// Click method for xpath
	public void ClickXp(String element) {
		driver.findElement(By.xpath(element)).click();
	}

	public String GetText(WebElement element) {
		String text = element.getText();
		logger.info("Text is :- " + text);
		return text;
	}

	// Send message in the chat
	public void SendKey(String message) throws InterruptedException {
		driver.findElement(By.xpath("//android.widget.EditText")).sendKeys(message);
		ClickXp("//android.widget.Button[@text=\"\"]");
		driver.hideKeyboard();
		Thread.sleep(4000);
	}

	public boolean waitForElement(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			// logger.info("Element found: " + locator);
			return true; // Element is visible
		} catch (Exception e) { // Catch any exception, including TimeoutException
			logger.error("Element not found within 30 seconds: " + locator);
			return false; // Element is not visible
		}
	}

	public boolean waitUntilText(WebElement element, String expectedText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));
			// logger.info("Text found: " + expectedText);
			return true;
		} catch (Exception e) {
			logger.error("Expected text not found within 30 seconds: " + expectedText);
			return false;
		}
	}

	// Method to perform pull-down refresh
	public void pullToRefresh() {
		int screenWidth = driver.manage().window().getSize().width;
		int screenHeight = driver.manage().window().getSize().height;

		int startX = screenWidth / 2;
		int startY = (int) (screenHeight * 0.8); // Start from 80% height
		int endY = (int) (screenHeight * 0.2); // End at 20% height

		// Creating touch action using W3C actions
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
		Sequence swipe = new Sequence(finger, 1);

		// Move finger to start position
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
		// Press down
		swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		// Move to end position (swipe action)
		swipe.addAction(
				finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), startX, endY));
		// Release
		swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		driver.perform(Collections.singletonList(swipe));
		logger.info("Pull-to-refresh gesture performed.");
	}

	// Main method to wait for element and refresh if needed
	public void findElementWithRefresh(By locator) {
		if (!waitForElement(locator)) {
			logger.error("Element not found, performing pull-down refresh...");
			pullToRefresh();
			waitForElement(locator);
		}
	}

	public void VideoWait() {
		By videoLocator = By.id("com.sot.bizup.debug:id/mbGood");
		if (waitForElement(videoLocator)) {
			WebElement video = driver.findElement(videoLocator);
			waitUntilText(video, "Catalog");
		}
	}

	public void CollectionPageWait() {
		By collectionPageLocator = By.id("com.sot.bizup.debug:id/ivSuperSellertext");
		if (waitForElement(collectionPageLocator)) {
			WebElement collectionPage = driver.findElement(collectionPageLocator);
			waitUntilText(collectionPage, "FAST SELLING PRODUCTS CURATED BY BIZUP");
		}
	}

	public void VideoWaitDebug() {
		By videoLocator = By.id("com.sot.bizup.debug:id/mbGood");
		if (waitForElement(videoLocator)) {
			WebElement video = driver.findElement(videoLocator);
			waitUntilText(video, "Catalog");
		}
	}

	public void CollectionPageWaitDebug() {
		By collectionPageLocator = By.id("com.sot.bizup.debug:id/ivSuperSellertext");
		if (waitForElement(collectionPageLocator)) {
			WebElement collectionPage = driver.findElement(collectionPageLocator);
			waitUntilText(collectionPage, "FAST SELLING PRODUCTS CURATED BY BIZUP");
		}
	}

	// Home to Video page navigation
	public void HomeToVideoFeed() throws InterruptedException {
		By videoThumb = By
				.xpath("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivThumbnail\"])[1]");
		By banner = By.xpath("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivBanner\"])[2]");

		if (driver.findElements(videoThumb).size() > 0) {
			driver.findElement(videoThumb).click();
		} else {
			if (driver.findElements(banner).size() > 0) {
				driver.findElement(banner).click();
			} else {
				ScrollEle("Surat hit design");
				driver.findElement(videoThumb).click();
			}
		}

		VideoWait();
		logger.info("Landed on Videofeed ✅");
	}

	public void HomeToVideoFeedDebug() throws InterruptedException {
		By videoThumb = By
				.xpath("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivThumbnail\"])[1]");
		By banner = By.xpath("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivBanner\"])[2]");

		if (driver.findElements(videoThumb).size() > 0) {
			driver.findElement(videoThumb).click();
		} else {
			if (driver.findElements(banner).size() > 0) {
				driver.findElement(banner).click();
			} else {
				ScrollEle("Surat hit design");
				driver.findElement(videoThumb).click();
			}
		}

		VideoWait();
		logger.info("Landed on Videofeed ✅");
	}

	// Getting seller name from seller page
	public String SpSellerName() {
		String sellerName = driver.findElement(By.id("com.sot.bizup.debug:id/mtSellerName")).getText();
		logger.info("Seller name is :- " + sellerName);
		return sellerName;
	}

	// Home to seller page navigation
	public void VideoToCollection() throws InterruptedException {
		ClickId("com.sot.bizup.debug:id/mbGood");
		CollectionPageWait();
		logger.info("Landed on the Collection page ✅");
	}

	public void VideoToCollectionDebug() throws InterruptedException {
		ClickId("com.sot.bizup.debug:id/mbGood");
		CollectionPageWait();
		logger.info("Landed on the Collection page ✅");
	}

	// Scroll without element
	public void Scroll() {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap
					.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "down", "percent", 3.0));
		} while (canScrollMore);
	}

	// Scroll to find Element
	public void ScrollEle(String ele) {
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + ele + "\"));"));
	}

	// Toast Message Method
	@SuppressWarnings("deprecation")
	public String Toast() {

		String message = "";
		By toast = By.xpath("//android.widget.Toast");

		boolean istoast = driver.findElements(toast).size() > 0;

		if (istoast) {
			message = driver.findElement(toast).getAttribute("name");
			logger.info("Toast Message Appers ✅");
			logger.info(message);
		} else {
			logger.error("Toast Message not Appers ❌");
			Assert.fail("Condition failed, marking test as failed");
		}

		return message;
	}

	// Chat Methods
	public void ShortChat() throws InterruptedException {
		checkAndSend("Hello");
		checkAndSend("Shirt chahiye");
	}

	public void LongChat() throws InterruptedException {
		checkAndSend("Hi", "Pant chahiye", "aur dekhao");
		checkAndSend("COD milega??", "Delivery charge kitna lagega??", "Delivery kab tak hogi??");
	}

	private void checkAndSend(String... messages) throws InterruptedException {
		By chatButton = By.xpath("//android.widget.Button[@text=\"\"]");
		By sendButton = By.id("com.whatsapp:id/send");

		if (driver.findElements(chatButton).size() > 0) {
			ClickXp("//android.widget.Button[@text=\"\"]");
			logger.info("Message send ✅");
			for (String message : messages) {
				SendKey(message);
			}
		} else if (driver.findElements(sendButton).size() > 0) {
			logger.info("Landed on WhatsApp ✅");
			Thread.sleep(2000);
			Back();
			Back();
		}
	}

	public void WhatsAppEnable() throws InterruptedException {

		// WhatsApp Enable Click
		By secondElement = By.xpath("//android.widget.TextView[@text=\"ओनर से बात करे\"]");
		By firstElement = By.xpath("//android.widget.Button[@text=\"बात करे\"]");

		// Check if the first element exists
		if (driver.findElements(firstElement).size() > 0) {
			// If the first element exists, click it
			driver.findElement(firstElement).click();
		} else {
			// If the first element doesn't exist, check if the second element exists
			driver.findElement(secondElement).click();
		}

		// Check for PreEnquiryVideo
		PreEnquiryVideoCheck();

		// WhatsApp Check
		WhatsAppCheck();

		// Answer feedback question
		FeedbackQue();
	}

//	public void CatalogEnq() throws InterruptedException {
//
//		// Full screen catalog enquiry
// ClickXp("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivItem\"])[1]");
// logger.info("Catalog Full view displayed ✅");
// ClickXp("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[1]");
// ClickId("com.sot.bizup.debug:id/ivCross");
//
//		// Selecting Catalog
// ClickXp("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[2]");
// ClickId("com.sot.bizup.debug:id/mbPlaceOrder");
//
//		// PreEnquiryQuestion Check
//		PreEnquiryQue();
//
//		// Check for PreEnquiryVideo
//		PreEnquiryVideoCheck();
//
//		// Enter the text on the Chat box
//		LongChat1();
//		driver.hideKeyboard();
//		Back(); 
//	}

public void PayCartPage() {
	By payButton = By.xpath("//android.widget.TextView[@text='Pay']");
	By payButton2 = By.xpath("//android.view.View[@content-desc=\"PLACE ORDER\"]");

	try {
		if (driver.findElements(payButton).size() > 0) {
			logger.info("Landed on Old Cart Page ✅");

			} else if (driver.findElements(payButton2).size() > 0) {
				logger.info("Landed on New Cart Page ✅");
				driver.findElement(payButton2).click();

			}
		} catch (Exception e) {
			logger.error("Not Landed on Cart Page ❌" + e);
			Assert.fail("Not Landed on Cart Page ❌" + e);
		}

	}

	public void CartPageCheck() {

		By cartPage = By.xpath("//android.widget.TextView[@text=\"Cart\"]");

		if (driver.findElements(cartPage).size() > 0) {
			logger.info("Landed on Cart Page ✅");
		} else {
			logger.error("Not Landed on Cart Page ❌");
			Assert.fail("Not Landed on Cart Page ❌");
		}

	}

	public void CatalogAddToCart() throws InterruptedException {

		By cartPage = By.xpath("//android.widget.TextView[@text=\"Cart\"]");

		By catalogImg = By.xpath("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivItem\"])[1]");
		By catalogSelect1 = By
				.xpath("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[1]");
		By catalogSelect2 = By
				.xpath("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[2]");
		By closeButton = By.id("com.sot.bizup.debug:id/ivCross");
		By orderbutton = By.id("com.sot.bizup.debug:id/mbPlaceOrder");

		try {

			if (driver.findElements(orderbutton).size() > 0) {
				driver.findElement(catalogImg).click();

				if (driver.findElements(closeButton).size() > 0) {
					logger.info("Full catalog view open ✅");

					driver.findElement(catalogSelect1).click();
					logger.info("Catalog added ✅");

					driver.findElement(closeButton).click();
					logger.info("Full catalog view closed ✅");

				} else {
					logger.error("Full catalog view not open ❌");
					Assert.fail("Full catalog view not open ❌");
				}

				if (driver.findElements(catalogSelect2).size() > 0) {
					ScrollEle("Select");

					driver.findElement(catalogSelect2).click();
					logger.info("Catalog added ✅");
				}

				driver.findElement(orderbutton).click();
				logger.info("Order button clicked ✅");

				// PreEnquiryQuestion Check
				PreEnquiryQue();

				if (driver.findElements(cartPage).size() > 0) {
					logger.info("Landed on Cart Page ✅");
				} else {
					logger.error("Not Landed on Cart Page ❌");
					Assert.fail("Not Landed on Cart Page ❌");
				}
			}

			else {
				logger.error("Order button not displayed ❌");
				Assert.fail("Order button not displayed ❌");
			}

		}

		catch (Exception e) {
			logger.error("Order button not displayed ❌" + e);
			Assert.fail("Order button not displayed ❌" + e);
		}

	}

	public void CatalogEnquiry() throws InterruptedException {

		// CoachMark Check
		CoachmarkCheck("//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivDealsCoachmarkText\"]");
		CoachmarkCheck("/hierarchy/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ImageView");
		ClickId("com.sot.bizup.debug:id/mtSellerCatalog");

		// Selecting Catalog
		ClickXp("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[1]");
		ClickXp("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[2]");
		ClickId("com.sot.bizup.debug:id/mbDealKare");

		// PreEnquiryQuestion Check
		PreEnquiryQue();

		// Check for PreEnquiryVideo
		PreEnquiryVideoCheck();
	}

	public void shortSellerEnquiry() throws InterruptedException {

		By chatButton = By.id("com.sot.bizup.debug:id/mbDealKare");

		try {
			if (driver.findElements(chatButton).size() > 0) {

				// Click on Chat button
				driver.findElement(chatButton).click();
				logger.info("Chat button clicked ✅");

				// PreEnquiryQuestion Check
				PreEnquiryQue();

				// Check for PreEnquiryVideo
				PreEnquiryVideoCheck();

				// Enter the text on the Chat box
				ShortChat();

				// Back from Chat
				Back();
			} else {
				logger.error("Chat button not displayed ❌");
				Assert.fail("Chat button not displayed ❌");
			}

		} catch (Exception e) {
			logger.error("Short Seller Enquiry error" + e);
		}
	}

	public void shortestEnquiry() throws InterruptedException {

		// Click on Baat Kare button
		ClickId("com.sot.bizup.debug:id/mbDealKare");

		// PreEnquiryQuestion Check
		PreEnquiryQue();

		// Check for PreEnquiryVideo
		PreEnquiryVideoCheck();

		// Enter the text on the Chat box
		ShortChat();

	}

	// CoachMark Check
	public void CoachmarkCheck(String CoachEle) {
		By coachMark = By.xpath(CoachEle);

		if (driver.findElements(coachMark).size() > 0) {
			// If the coachMark exists, click it
			driver.findElement(coachMark).click();
			logger.info("CoachMark Displayed ✅");
		}
	}

	// PreEnquiryVideo Check
//	public void PreEnquiryVideoCheck() throws InterruptedException {
//
// By preEnquiryVideo = By.id("com.sot.bizup.debug:id/mbButton");
//		WebElement element = driver.findElement(preEnquiryVideo);
//
//		if (driver.findElements(preEnquiryVideo).size() > 0) {
//			// If the PreEnquiryVideo exists, skip it
//			Wait(element, "SKIP VIDEO");
// ClickId("com.sot.bizup.debug:id/mbButton");
// logger.info("Pre-Enquiry Video Displayed ✅");
//		}
//	}

	public void PreEnquiryVideoCheck() throws InterruptedException {

		By surakshaBanner = By.id("com.sot.bizup.debug:id/mtSurakshaTitle");
		By preEnquiryVideo = By.id("com.sot.bizup.debug:id/mbButton");

		if (driver.findElements(surakshaBanner).size() > 0) {
			// If the PreEnquiryVideo exists, skip it
			Thread.sleep(9000);
			driver.findElement(preEnquiryVideo).click();
			logger.info("Pre-Enquiry Video Displayed ✅");
		}
	}

	// Feedback Question
	public void FeedbackQue() {
		try {
			By FeedbackQ = By.id("com.sot.bizup.debug:id/mtQuestion");

			if (driver.findElements(FeedbackQ).size() > 0) {
				ClickId("com.sot.bizup.debug:id/mbPositive");
				ClickId("com.sot.bizup.debug:id/mbMessage");
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
				logger.info("Feedback Question working ✅");
			}
		} catch (Exception e) {
			logger.error("Feedback Question error" + e);
			Assert.fail("Feedback Question error" + e);
		}
	}

	// Feedback Question
	public void PreEnquiryQue() {

		By PreEnqQ = By.id("com.sot.bizup.debug:id/mtQuestion");
		try {
			if (driver.findElements(PreEnqQ).size() > 0) {
				ClickId("com.sot.bizup.debug:id/mbPositive");
				logger.info("PreEnquiry Question working ✅");
			}
		} catch (Exception e) {
			logger.error("PreEnquiry Question error" + e);
			Assert.fail("PreEnquiry Question error" + e);
		}

	}

	// WhatsApp Check
	public void WhatsAppCheck() {
		try {
			By WhatsAppCheck = By.id("com.whatsapp:id/send");

			if (driver.findElements(WhatsAppCheck).size() > 0) {
				logger.info("Landed on WhatsApp ✅");
				Thread.sleep(2000);
				Back();
				Back();
			}
		} catch (Exception e) {
			logger.error("WhatsApp Landing Failed" + e);
			Assert.fail("WhatsApp Landing Failed" + e);
		}
	}

	public void Agent() {
		try {
			By agent = By.id("com.sot.bizup.debug:id/fab");

			if (driver.findElements(agent).size() > 0) {
				ClickId("com.sot.bizup.debug:id/fab");
				logger.info("Agent Clicked ✅");
			}
		} catch (Exception e) {
			logger.error("Agent failed " + e);
			Assert.fail("Agent failed " + e);
		}

	}

	public void Chat() {
		try {
			ShortChat();
			driver.hideKeyboard();
			Thread.sleep(2000);
			logger.info("Chat Sucessfull ✅");
		} catch (Exception e) {
			logger.error("Chat error" + e);
			Assert.fail("Chat error" + e);
		}
	}

}
