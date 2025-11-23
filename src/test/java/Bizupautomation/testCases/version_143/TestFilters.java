package Bizupautomation.testCases.version_143;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import Bizupautomation.testUtils.Base;
import Bizupautomation.testUtils.RetryAnalyzer;
import buyer.pageObjects.HomePage;
import buyer.pageObjects.VideoPage;

public class TestFilters extends Base {

	private static final Logger logger = LogManager.getLogger(TestFilters.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		logger.info("TestFilters class initialized ✅");
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

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify home page filters reset functionality")
	public void HomeReset() throws InterruptedException {
		HomePage home = new HomePage(driver);

		logger.info("✨✨------------ Home Filter Reset Flow start ------------✨✨");

		home.HomeWait();

		// ---------- Product reset message check
		home.HomeClearFilters();

		home.GenderFilter("Women");

		home.HomeProductSelect("Shirts");

		home.ResetMsg();

		// ---------- Price reset mesage check
		home.HomeProductSelect("Silk Saree");

		home.HomePriceSelect("200 and below");

		home.ResetMsg();

		// ----------- City reset message check
		home.HomeClearFilters();

		home.HomeProductSelect("Silk Saree");

		home.HomeCitySelect(2);

		home.ResetMsg();

		logger.info("✨✨------------ Home Filter Reset Flow End ------------✨✨");
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verify video page filters reset functionality")
	public void VideoReset() throws InterruptedException {
		VideoPage video = new VideoPage(driver);

		logger.info("✨✨------------ VideoFeed Filter Reset Flow start ------------✨✨");

		video.HomeToVideoFeed();

		// ---------- Product reset message check
		video.VideoClearFilters();

		video.VideoPriceSelect("200 and below");

		video.VideoProductSelect("Silk Saree", "सिल्क सरी");

		video.ResetMsg();

		// ---------- Price reset mesage check
		video.VideoClearFilters();

		video.VideoProductSelect("Silk Saree", "सिल्क सरी");

		video.VideoPriceSelect("200 and below");

		video.ResetMsg();

		// ----------- City reset message check
		video.VideoClearFilters();

		video.VideoProductSelect("Fancy Saree", "फैंसी सरी");

		video.VideoCitySelect(2);

		video.ResetMsg();

		logger.info("✨✨------------ VideoFeed Filter Reset Flow End ------------✨✨");
	}
}