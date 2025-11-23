package buyer.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SellerPage extends VideoPage {

	private static final Logger logger = LogManager.getLogger(SellerPage.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("SellerPage class initialized ✅");
	}

	AndroidDriver driver;

	public SellerPage(AndroidDriver driver)

	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(xpath = "//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivDealsCoachmarkText\"]")
	private WebElement dealCoachmark;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ImageView")
	private WebElement saveCoachmark;

	@AndroidFindBy(xpath = "//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivDealsCoachmarkText\"]")
	private WebElement superCoachmark;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtSellerName")
	private WebElement sellerName;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbSave")
	private WebElement saveButton;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbDealKare")
	private WebElement smallBaatKareBtn;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivSuperProfileBadge")
	private WebElement superSellerIcon;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivSuperSellerline")
	private WebElement superSellerLine;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtSellerVideos")
	private WebElement VideoTab;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtSellerCatalog")
	private WebElement CatalogTab;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtSellerAboutStroke")
	private WebElement InfoTab;

	@AndroidFindBy(xpath = "(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[1]")
	private WebElement Catalog1stItemShortlist;

	@AndroidFindBy(xpath = "(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[2]")
	private WebElement Catalog2ndItemShortlist;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivBackBg")
	private WebElement BackButton;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbChat")
	private WebElement VideoBaatKareButton;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbPlaceOrder")
	private WebElement baatKareButton;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbPositive")
	private WebElement EnquiryActiQue;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbButton")
	private WebElement SkipEnqVideo;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/gpSelectedItems")
	private WebElement gpSelectedItems;

	public void SellerPageCoachMark() {

		By coachmark = By
				.xpath("//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivDealsCoachmarkText\"]");

		if (driver.findElements(coachmark).size() > 0) {
			driver.findElement(coachmark).click();
			logger.info("Coachmark Displayed ✅");
		} else {
			logger.error("No coachmark is displayed ❌");
		}

	}

	public void SmallBaatKareBtn() {
		if (smallBaatKareBtn.isDisplayed()) {
			smallBaatKareBtn.click();
		} else {
			logger.error("Not redirect to seller page ❌");
			Assert.fail("Condition failed, marking test as failed ❌");
		}
	}

	public void SellerPageWait() {
		By sellerPageLocator = By.id("com.sot.bizup.debug:id/mbDealKare");
		if (waitForElement(sellerPageLocator)) {
			WebElement sellerPage = driver.findElement(sellerPageLocator);
			waitUntilText(sellerPage, "Chat");
		}
	}

	public void VideoToSellerPage() throws InterruptedException {
		ClickId("com.sot.bizup.debug:id/mbGood");
		SellerPageWait();
		logger.info("Landed on the Seller page ✅");
	}

	public void VideoTabClick() {
		VideoTab.click();
		VideoTab.click();
	}

	public void CatalogTabClick() {
		CatalogTab.click();
	}

	public void InfoTabClick() {
		InfoTab.click();
	}

	public void VideoBaatKare() {
		VideoBaatKareButton.click();
	}

	public void BaatKareButton() throws InterruptedException {
		baatKareButton.click();
		Thread.sleep(3000);
	}

	public void CatalogSelect() {
		if (Catalog1stItemShortlist.isDisplayed()) {
			Catalog1stItemShortlist.click();
			Catalog2ndItemShortlist.click();
			logger.info("Catalog selected ✅");
		} else {
			logger.error("Catalog not selected ❌");
		}

	}

	public void ProdFilterSelect() {
		driver.findElements(AppiumBy.className("android.widget.TextView")).get(1).click();
	}

	public void EnquiryActivationQue() {
		EnquiryActiQue.click();
	}

	public void SkipEnquiryVideo() {
		SkipEnqVideo.click();
	}

//	public void HomeToSeller() throws InterruptedException {
//
//		String VSeller = HomeToCollection();
//		String seller = sellerName.getText();
//
//		if (VSeller.equals(seller)) {
// logger.info("Seller matched " + seller + "✅");
//		} else {
// logger.error("Seller name not match ❌");
//		}
//	}

	// Last catalog select message
	public void LastCatalogMsg() {
		Back();
		SampleDekhe();
		gpSelectedItems.isDisplayed();
	}

}
