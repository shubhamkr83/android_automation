package Bizupautomation.testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import Bizupautomation.testUtils.Base;
import buyer.pageObjects.HomePage;

public class TestCollectionloop extends Base {

	@Test
	public void CollectionToCollectionFlow() throws InterruptedException {

		HomePage home = new HomePage(driver);

		int forwardMove = 0;

		System.out.println("✨✨✨------------ Collection Loop start -----------✨✨✨");

		home.HomeToVideoFeedDebug();

		home.VideoToCollectionDebug();

		By DemoButton = By.xpath("//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/demo\"]");

		while (true) {
			try {
				// Click on Demo Button
				driver.findElement(DemoButton).click();

				// Wait Collection page to appear
				home.CollectionPageWait();

				// Increase forwardMove count
				forwardMove++;
				System.out.println("Forward move count: " + forwardMove);

			} catch (Exception e) {
				System.out.println("❌ Test Failed: Select button is not visible. Pages navigated: " + forwardMove);
				Assert.fail("Test Failed: Select button is not visible. Pages navigated: " + forwardMove, e);
			}
		}
	}
}
