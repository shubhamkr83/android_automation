package buyer.pageObjects;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import buyer.utils.AndroidUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class VideoPage extends AndroidUtils {

	private static final Logger logger = LogManager.getLogger(VideoPage.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("VideoPage class initialized ✅");
	}

	AndroidDriver driver;

	public VideoPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbProductTag")
	private WebElement productFilter;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbDone")
	private WebElement productApply;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbPriceRangeText")
	private WebElement priceFilter;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbMarketText")
	private WebElement cityFilter;

	@AndroidFindBy(xpath = "(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivSelected\"])")
	private List<WebElement> citySelect;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvClear")
	private WebElement clear;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvNoResult")
	private WebElement resetResult;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtChangeFilter")
	private WebElement resetBtn;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtSellerNameD")
	private WebElement sellerName;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivSavedSeller")
	private WebElement saveIcon;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbGood")
	private WebElement sampleDekhe;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbNotGood")
	private WebElement nextVideo;

	public void SampleDekhe() {
		sampleDekhe.click();
	}

	public void NextVideo() {
		nextVideo.click();
	}

	// Product Select
	public void VideoProductSelect(String prod1, String prod2) {
		try {

			By ProdEng = By
					.xpath("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtFilterName\" and @text=\""
							+ prod1 + "\"]");
			By ProdHin = By
					.xpath("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtFilterName\" and @text=\""
							+ prod2 + "\"]");

			if (productFilter.isDisplayed()) {
				productFilter.click();
				if (driver.findElements(ProdEng).size() > 0) {
					ClickXp("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtFilterName\" and @text=\""
							+ prod1 + "\"]");
					productApply.click();
//					VideoWait();
logger.info(prod1 + " Product selected ✅");
				} else if (driver.findElements(ProdHin).size() > 0) {
					ClickXp("//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/mtFilterName\" and @text=\""
							+ prod2 + "\"]");
					productApply.click();
//					VideoWait();
logger.info(prod2 + " Product selected ✅");
				}
			}
		} catch (Exception e) {
			logger.error("Product filter failed " + e);
			Assert.fail("Product filter failed " + e);
		}
	}

	// Price Select
	public void VideoPriceSelect(String price) {
		try {
			if (priceFilter.isDisplayed()) {
				priceFilter.click();
				ClickXp("//android.widget.Button[@text=\"" + price + "\"]");
//				VideoWait();
logger.info(price + " Price selected ✅");
			}
		} catch (Exception e) {
			logger.error("Price filter failed ❌" + e);
			Assert.fail("Price filter failed ❌" + e);
		}
	}

	// City Select
	public void VideoCitySelect(int index) {
		try {
			if (cityFilter.isDisplayed()) {
				cityFilter.click();
				citySelect.get(index).click();
				VideoWait();
				logger.info("City selected ✅");
			}
		} catch (Exception e) {
			logger.error("City filter failed ❌" + e);
			Assert.fail("City filter failed ❌" + e);
		}
	}

	// Clear Filter
	public void VideoClearFilters() {

		By cityFilterVideocheck = By.id("com.sot.bizup.debug:id/mbMarketText");
		try {
//			VideoWait();
			productFilter.click();
			clear.click();
//			VideoWait();
			priceFilter.click();
			clear.click();

			if (driver.findElements(cityFilterVideocheck).size() > 0) {
				driver.findElement(cityFilterVideocheck).click();
				clear.click();
			}
//			VideoWait();
logger.info("All Filters are Clear ✅");
		} catch (Exception e) {
			logger.error("Filters are not clear ❌");
			Assert.fail("Filters are not clear ❌");
		}
	}

	// -------------- Old video check --------------
	public String VideoCheck() {
		String videoSeller = "";

		By saveIcon = By.id("com.sot.bizup.debug:id/ivSavedSeller");

		boolean isSaveIconPresent = driver.findElements(saveIcon).size() > 0;

		while (isSaveIconPresent) {
			if (isSaveIconPresent) {
				nextVideo.click();
			}
		}
		videoSeller = sellerName.getText();
		sampleDekhe.click();
		logger.info("VideoFeed Save Icon Checked ✅");

		return videoSeller;

	}

	// Video Save Check
	public void VideoSaveCheck() {
		By saveIcon = By.id("com.sot.bizup.debug:id/ivSavedSeller");
		boolean isSaveIconPresent = driver.findElements(saveIcon).size() > 0;

		if (sampleDekhe.isDisplayed()) {
			logger.info("Landed on VideoFeed Page ✅");
			if (isSaveIconPresent) {
				logger.info("Video Save Icon Present ✅");
				Back();
			} else {
				logger.error("Video Save Icon not Present ❌");
			}
		} else {
			logger.error("Not landed on VideoFeed Page ❌");
		}
	}

	// Filter Reset message
	public void ResetMsg() {
		if (resetResult.isDisplayed()) {
			String resetMsg = resetResult.getText();
			String resetBtnMsg = resetBtn.getText();
			logger.info("Reset Message is " + resetMsg + " " + resetBtnMsg);
		} else {
			logger.error("Reset Message is not Displayed ❌");
			Assert.fail("Reset Message is not Displayed ❌");
		}
	}

	// Seller Recommendation
	public void RecomProdEnq(int num) throws InterruptedException {
		for (int i = 0; i <= num; i++) {
			SampleDekhe();
			CatalogEnquiry();
			Back();
			Back();
			NextVideo();
		}

	}

}
