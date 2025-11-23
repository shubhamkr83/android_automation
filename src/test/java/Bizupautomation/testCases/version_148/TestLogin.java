package Bizupautomation.testCases.version_148;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Bizupautomation.testUtils.RetryAnalyzer;
import Bizupautomation.testUtils.Base;
import buyer.pageObjects.LoginPage;

public class TestLogin extends Base {

	private static final Logger logger = LogManager.getLogger(TestLogin.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		logger.info("TestLogin class initialized ✅");
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

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify the language implementation in the login page")
	public void LoginLangCheck() throws InterruptedException {
		LoginPage login = new LoginPage(driver);

		logger.info("✨✨------------ Login Language Check Start ------------✨✨");

		// Select language
		login.SelectLanguage();

		login.LoginLang();

		logger.info("✨✨------------ Login Language Check End ------------✨✨");
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verify user can successfully login")
	public void LoginFlow() throws InterruptedException {
		LoginPage login = new LoginPage(driver);

		logger.info("✨✨------------ Login Flow start ------------✨✨");

		login.SelectLanguage();

		login.Login();

		login.LoginCheck();

		logger.info("✨✨------------ Login Flow End ------------✨✨");
	}
}
