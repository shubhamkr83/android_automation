package Bizupautomation.testCases.version_143;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Bizupautomation.testUtils.RetryAnalyzer;
import Bizupautomation.testUtils.Base;
import buyer.pageObjects.ProfilePage;

public class TestProfile extends Base {

	private static final Logger logger = LogManager.getLogger(TestProfile.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		logger.info("TestProfile class initialized ✅");
	}

	@BeforeMethod
	public void setupMethod() {
		// Reset app state before each test method
		if (driver != null) {
			try {
				// Restart the app to ensure a clean state for each test
				driver.terminateApp("com.sot.bizup.debug");
				driver.activateApp("com.sot.bizup.debug");
				Thread.sleep(2000); // Give app time to stabilize
				logger.info("App restarted before test method ✅");
			} catch (Exception e) {
				logger.error("Error resetting app state: " + e.getMessage());
			}
		}
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify profile functionality [Steps:- Home >> Profile >> Edit Profile >>Refer>>Setting]")
	public void ProfileFlow() throws InterruptedException {

		ProfilePage profilePage = new ProfilePage(driver);

		logger.info("✨✨------------ Profile Flow start ------------✨✨");

		// Navigate to profile page
		profilePage.Profile();

		// Add small waits between actions to ensure stability
		Thread.sleep(1000);

		// Edit profile
		profilePage.EditProfile("Test Seller", "Test Shop", "Delhi");
		Thread.sleep(1000);

		// Refer
		profilePage.Refer();
		Thread.sleep(1000);

		// Setting
		profilePage.Setting();
		Thread.sleep(1000);

		logger.info("✨✨------------ Profile Flow End ------------✨✨");

	}
}