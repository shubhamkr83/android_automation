package buyer.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchPage extends HomePage {

	private static final Logger logger = LogManager.getLogger(SearchPage.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("SearchPage class initialized ✅");
	}

	AndroidDriver driver;

	public SearchPage(AndroidDriver driver)

	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbSearch\"]")
	private WebElement SearchIcon;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/search_src_text")
	private WebElement searchInputType;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtClear")
	private WebElement clearResult;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/tabText\" and @text=\"Videos (99+)\"]")
	private WebElement videoTab;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbGood")
	private WebElement sampleDekhe;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivThumbnail\"])[1]")
	private WebElement playVideo;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbGood")
	private WebElement videoLikeButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/tabText\" and @text=\"Samples\"]")
	private WebElement sampleTab;

	@AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtOrder\"])[1]")
	private WebElement sampleOrder;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/tabText\" and @text=\"Sellers\"]")
	private WebElement sellerTab;

	@AndroidFindBy(xpath = "(//android.view.ViewGroup[@resource-id=\"com.sot.bizup.debug:id/clItem\"])[1]")
	private WebElement sellerCard;

	@AndroidFindBy(xpath = "(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbExploreCatalog\"])[1]")
	private WebElement exploreCatalog;

	public void SearchWait() {
		By searchResultText = By.id("com.sot.bizup.debug:id/mtTitle");
		waitForElement(searchResultText);
	}

	public void SearchProduct(String product) {
		try {
			if (SearchIcon.isDisplayed()) {
				SearchIcon.click();
				searchInputType.sendKeys(product);
				driver.hideKeyboard();
				logger.info("Sucessfully Searched " + product + " ✅");
			}
		} catch (Exception e) {
			logger.error(product + " searched failed" + e);
			Assert.fail(product + " Product searched failed" + e);
		}

		// String resultsSearch =
		// driver.findElement(By.id("com.sot.bizup.debug:id/mtTitle")).getText();
//		Assert.assertEquals("Showing results for \"" + product + "\"", resultsSearch);
	}

	public void ClearResult() {
		clearResult.click();
		logger.info("Result cleared ✅");
	}

	public void PlayVideo() {
		playVideo.click();
		logger.info("Video Played ✅");
	}

	public void VideoLikeButton() {
		videoLikeButton.click();
	}

	public void VideoTab() throws InterruptedException {
		if (videoTab.isDisplayed()) {
			videoTab.click();
			SearchWait();
			logger.info("Landed on Video Tab ✅");
		}

	}

	public void SampleTab() throws InterruptedException {
		if (sampleTab.isDisplayed()) {
			sampleTab.click();
			SearchWait();
			logger.info("Landed on Sample Tab ✅");
		}
	}

	public void SellerTab() throws InterruptedException {
		if (sellerTab.isDisplayed()) {
			sellerTab.click();
			logger.info("Landed on Seller Tab ✅");
		}
	}

//	public void VideoTabEnq() throws InterruptedException {
//		try {
//			playVideo.click();
//			videoLikeButton.click();
//			shortSellerEnquiry();
//			Back();
//			Back();
//			Back();
//			Back();
//		} catch (Exception e) {
// logger.error("Video Search Enquiry failed" + e);
//		}
//
//	}

	public void SampleToCollection() throws InterruptedException {
		try {
			sampleOrder.click();
			Thread.sleep(3000);
			logger.info("Clicked on Order button ✅");
		} catch (Exception e) {
			logger.error("Sample Search Enquiry failed" + e);
			Assert.fail("Sample Search Enquiry failed" + e);
		}
	}

	public void SellerCard() {
		try {
			if (sellerCard.isDisplayed()) {
				sellerCard.click();
				logger.info("Seller Card Clicked ✅");
			}
		} catch (Exception e) {
			logger.error("Seller Card failed" + e);
			Assert.fail("Seller Card failed" + e);
		}
	}

	public void SellerEnquiry() throws InterruptedException {
		try {
			shortSellerEnquiry();
			logger.info("Seller Search Enquiry working ✅");
		} catch (Exception e) {
			logger.error("Seller Search Enquiry failed" + e);
			Assert.fail("Seller Search Enquiry failed" + e);
		}
	}

	public void NavSeller() {
		ClickId("com.sot.bizup.debug:id/tvViewCatalog");
	}

}
