package buyer.pageObjects;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage extends SellerPage {

	private static final Logger logger = LogManager.getLogger(HomePage.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("HomePage class initialized ✅");
	}

	AndroidDriver driver;

	public HomePage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
	private WebElement notipermission;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_message")
	private WebElement locationPermission;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_deny_button")
	private WebElement locationPermissionDeny;

	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_one_time_button")
	private WebElement locationPermissionAllowID;

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_allow_one_time_button']")
	private WebElement locationPermissionAllowXpath;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbSearch")
	private WebElement searchBar;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtMore")
	private WebElement productMore;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivSavedSeller")
	private WebElement save;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtUserProfile")
	private WebElement profle;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivBanner")
	private WebElement banner;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtTopAll")
	private WebElement genderAll;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivCoachmarkText")
	private WebElement coachmark;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbCategory")
	private WebElement productFilter;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbDone")
	private WebElement productApply;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbMainCategory")
	private WebElement priceFilter;

	@AndroidFindBy(xpath = "//android.widget.Button[@text=\"400 and below\"]")
	private WebElement priceSelect;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbCity")
	private WebElement cityFilter;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivSelected\"])")
	private List<WebElement> citySelect;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvClear")
	private WebElement clear;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivThumbnail\"])[1]")
	private WebElement videoPlay;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/navigation_bar_item_icon_view\"])[1]")
	private WebElement homeTab;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/navigation_bar_item_icon_view\"])[2]")
	private WebElement searchTab;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/navigation_bar_item_icon_view\"])[3]")
	private WebElement mereSellerTab;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvNoResult")
	private WebElement resetResult;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtChangeFilter")
	private WebElement resetBtn;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Suggestion`s\"]")
	private WebElement suggestion;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvTitle")
	private WebElement suggestionTitle;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbOrder")
	private WebElement cartButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/navigation_bar_item_small_label_view\" and @text=\"Orders\"]")
	private WebElement orderTab;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbBook")
	private WebElement orderTabCartButton;

	// Coachmark
	public void HomePageCoachMark() {
		By coachmark = By.id("com.sot.bizup.debug:id/ivCoachmarkText");

		if (driver.findElements(coachmark).size() > 0) {
			save.click();
			logger.info("Coachmark Displayed ✅");
		} else {
			logger.error("No coachmark is displayed ❌");
		}
	}

	public void HomeWait() {
		try {
			waitUntilText(genderAll, "All");

			if (genderAll.isDisplayed()) {
				logger.info("Landed on HomePage ✅");
			} else {
				logger.info("Not Landed on HomePage ❌");
			}
		} catch (Exception e) {
			Assert.fail("Homepage Landing failed " + e);
			logger.error("An error occurred: " + e.getMessage());
		}
	}

	// Notification Permission
	public void Permission() {

		// By locationID = By
		// .id("com.android.permissioncontroller:id/permission_allow_one_time_button");
		// By locationxpath = By.xpath(
		// "//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_allow_one_time_button']");

		try {
			// if (notipermission.isDisplayed()) {
			// notipermission.click();
			// System.out.println("Notification working ✅");
			// Thread.sleep(2000);
			// if (locationPermission.isDisplayed()) {
			// locationPermissionAllow.click();
			// System.out.println("Location working ✅");
			// Thread.sleep(2000);
			// } else {
			// System.out.println("Location permission not displayed ❌");
			// }
			// } else {
			// System.out.println("Notification permission not displayed ❌");
			// }
			Thread.sleep(2000);
			// Check if location permission dialog is displayed

			// Try to find and click the allow button by ID first
			if (locationPermissionAllowID.isDisplayed()) {
				locationPermissionAllowID.click();
				logger.info("Location permission allowed via ID ✅");
			}
			// If ID approach fails, try using XPath
			else if (locationPermissionAllowXpath.isDisplayed()) {
				locationPermissionAllowXpath.click();
				logger.info("Location permission allowed via XPath ✅");
			} else {
				logger.error("Location permission dialog found but couldn't find allow button ⚠️");
			}

		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			Assert.fail("Location permission failed " + e);
		}
	}

	public void LoginCheck() {
		HomeWait();
		logger.info("Login successfully ✅");
	}

	// Gender filter
	public void GenderFilter(String gender) {
		try {
			if (genderAll.isDisplayed()) {
				ClickXp("//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mtTop" + gender + "\"]");
				logger.info(gender + " filter set ✅");
			}
		} catch (Exception e) {
			logger.error("Gender filter failed " + e.getMessage());
			Assert.fail("Gender filter failed " + e);
		}
	}

	// Product Select
	public void HomeProductSelect(String product) {
		try {
			if (productMore.isDisplayed()) {
				productMore.click();
				ClickXp("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtFilterName\" and @text=\""
						+ product + "\"]");
				productApply.click();
				// HomeWait();
				logger.info(product + " selected ✅");
			}
		} catch (Exception e) {
			logger.error("Product filter failed " + e.getMessage());
			Assert.fail("Product filter failed " + e);
		}
	}

	// Price Select
	public void HomePriceSelect(String price) {
		try {
			if (priceFilter.isDisplayed()) {
				priceFilter.click();
				ClickXp("//android.widget.Button[@text=\"" + price + "\"]");
				// HomeWait();
				logger.info("Price selected ✅");
			}
		} catch (Exception e) {
			logger.error("Price filter failed ❌" + e);
			Assert.fail("Price filter failed ❌" + e);
		}
	}

	// City Select
	public void HomeCitySelect(int index) {
		try {
			if (cityFilter.isDisplayed()) {
				cityFilter.click();
				citySelect.get(index).click();
				// HomeWait();
				logger.info("City selected ✅");
			}
		} catch (Exception e) {
			logger.error("City filter failed ❌" + e);
			Assert.fail("City filter failed ❌" + e);
		}
	}

	// Clear Filter
	public void HomeClearFilters() {
		By cityFiltercheck = By.id("com.sot.bizup.debug:id/mbCity");

		try {
			// HomeWait();
			genderAll.click();
			// HomeWait();
			productMore.click();
			clear.click();

			if (driver.findElements(cityFiltercheck).size() > 0) {
				driver.findElement(cityFiltercheck).click();
				clear.click();
			}
			// HomeWait();
			logger.info("All Filters are Clear ✅");
		} catch (Exception e) {
			logger.error("Filters are not clear ❌" + e);
			Assert.fail("Filters are not clear ❌" + e);
		}
	}

	// Save page
	public void Save() {
		save.click();
	}

	// Profile page
	public void Profile() {
		profle.click();
	}

	// Banner click
	public void Banner() {
		banner.click();
	}

	// Home tab
	public void HomeTab() {
		homeTab.click();
	}

	// Search tab
	public void SearchBar() {
		searchBar.click();
	}

	// Seller Journey
	public void SellerPresentCheck(String seller) throws InterruptedException {
		try {
			RestartApp();
			ScrollEle("Keep watching your sellers");
			By FindSeller = By
					.xpath("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtSellerName\" and @text=\""
							+ seller + "\"]");
			if (driver.findElements(FindSeller).size() > 0) {
				logger.info("Seller " + seller + " is present in the Seller Journey section ✅");
				ClickXp("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtSellerName\" and @text=\""
						+ seller
						+ "\"]");
				shortestEnquiry();
				RestartApp();
			}
		} catch (Exception e) {
			logger.error("Seller Present Check " + e);
			Assert.fail("Seller Present Check " + e);
		}
	}

	// Seller Journey
	public void sellerRemoveCheck(String seller) {
		try {
			ScrollEle("Keep watching your sellers");
			By FindSeller = By
					.xpath("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtSellerName\" and @text=\""
							+ seller + "\"]");
			if (driver.findElements(FindSeller).size() > 0) {
				logger.error("Seller " + seller + " is not removed from the section after doing Enquiry ❌");
				Assert.fail("Seller " + seller + " is not removed from the section after doing Enquiry ❌");
			} else {
				logger.info("Seller " + seller + " is remove from the section after doing Enquiry ✅");
			}
		} catch (Exception e) {
			logger.error("seller Remove failed " + e);
			Assert.fail("seller Remove failed " + e);
		}
	}

	// Seller Recommendation
	public void SellerRec(String product) {
		ScrollEle("Suggestion`s");

		String prod = suggestionTitle.getText();

		if (product == prod) {

		}

	}

	// Filter Reset message
	public void ResetMsg() {
		// waitUntilText(resetResult, "No Result Found");
		if (resetResult.isDisplayed()) {
			String resetMsg = resetResult.getText();
			String resetBtnMsg = resetBtn.getText();
			logger.info("Reset Message is " + resetMsg + " " + resetBtnMsg);
		} else {
			logger.error("Reset Message is not Displayed ❌");
			Assert.fail("Reset Message is not Displayed ❌");
		}
	}

	public void CartButton() {
		if (cartButton.isDisplayed()) {
			cartButton.click();
			logger.info("Cart Button clicked from Homepage ✅");
		} else {
			logger.error("Cart Button is not displayed ❌");
			Assert.fail("Cart Button is not displayed ❌");
		}
	}

	public void OrderTab() {
		if (orderTab.isDisplayed()) {
			orderTab.click();
			logger.info("Order tab clicked ✅");
		} else {
			logger.error("Order tab is not displayed ❌");
			Assert.fail("Order tab is not displayed ❌");
		}
	}

	public void OrderTabCartButton() {
		if (orderTabCartButton.isDisplayed()) {
			orderTabCartButton.click();
			logger.info("Order tab cart button clicked ✅");
		} else {
			logger.error("Order tab cart button is not displayed ❌");
			Assert.fail("Order tab cart button is not displayed ❌");
		}
	}

}
