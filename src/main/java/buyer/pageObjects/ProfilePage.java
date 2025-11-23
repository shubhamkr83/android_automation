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

public class ProfilePage extends AndroidUtils {

	private static final Logger logger = LogManager.getLogger(ProfilePage.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("ProfilePage class initialized ✅");
	}

	AndroidDriver driver;

	public ProfilePage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mtProfile")
	private WebElement profile;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvEditProfile")
	private WebElement editProfile;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Refer\"]")
	private WebElement refer;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivSetting")
	private WebElement setting;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/llVideo")
	private WebElement myVideos;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/etName")
	private WebElement editName;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/etBizName")
	private WebElement editBussName;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/etCity")
	private WebElement editCity;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/btnNext")
	private WebElement nextBtn;

	@AndroidFindBy(xpath = "(//android.widget.CheckBox[@resource-id=\"com.sot.bizup.debug:id/checkBox\"])")
	private List<WebElement> categorySelect;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/btnFinish")
	private WebElement save;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/btnChat")
	private WebElement referShare;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/text1\" and @text=\"FAQs\"]")
	private WebElement faq;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/text1\" and @text=\"About Us\"]")
	private WebElement about;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/text1\" and @text=\"Support\"]")
	private WebElement support;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/text1\" and @text=\"Terms & Conditions\"]")
	private WebElement terms;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.sot.bizup.debug:id/text1\" and @text=\"Privacy Policy\"]")
	private WebElement privacy;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/btnRedirect")
	private WebElement videoUploadSupport;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvYourName")
	private WebElement cName;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvCity")
	private WebElement cCity;

	public void Profile() {
		try {
			if (profile.isDisplayed()) {
				profile.click();
				logger.info("Landed Profile Page ✅");
			}
		} catch (Exception e) {
			logger.error("Profile icon missing " + e);
			Assert.fail("Profile icon missing ", e);
		}

	}

	public void ProfileWait() {
		By profileLocator = By.id("com.sot.bizup.debug:id/tvYourProfile");
		if (waitForElement(profileLocator)) {
			WebElement profile = driver.findElement(profileLocator);
			waitUntilText(profile, "Your Profile,");
		}
	}

	public void EditProfile(String name, String bizname, String city) {
		try {
			if (editProfile.isDisplayed()) {
				editProfile.click();
				editName.sendKeys(name);
				editBussName.sendKeys(bizname);
				editCity.sendKeys(city);
				nextBtn.click();

				CategoryEdit();

				String Name = cName.getText();
				String City = cCity.getText();

				if (Name.equals(name) && City.equals(city)) {
					logger.info("Name: " + name + ", " + "Business Name: " + bizname + ", " + "City: " + city);
					logger.info("Profile Edit success ✅");
				}
			}
		} catch (Exception e) {
			logger.error("Profile edit failed " + e);
			Assert.fail("Profile edit failed ", e);
		}
	}

	public void CategoryEdit() {
		try {
			if (save.isDisplayed()) {
				categorySelect.get(1).click();
				categorySelect.get(2).click();
				categorySelect.get(3).click();
				save.click();
			}
		} catch (Exception e) {
			logger.error("Category edit failed " + e);
			Assert.fail("Category edit failed ", e);
		}
	}

	public void Refer() {
		try {
			if (refer.isDisplayed()) {
				refer.click();
//				referShare.click();
//				Back();
				Back();
				logger.info("Refer success ✅");
			}
		} catch (Exception e) {
			logger.error("Refer failed " + e);
			Assert.fail("Refer failed ", e);
		}
	}

	public void Setting() {
		try {
			if (setting.isDisplayed()) {
				setting.click();
				faq.click();
				Back();
				about.click();
				Back();
				Back();
				logger.info("Setting checked ✅");
			}
		} catch (Exception e) {
			logger.error("Setting failed " + e);
			Assert.fail("Setting failed ", e);
		}
	}

	public void MyVideo() {
		try {
			if (setting.isDisplayed()) {
				myVideos.click();
//				videoUploadSupport.click();
//				Back();
//				Back();
				Back();
				logger.info("MyVideo checked ✅");
			}
		} catch (Exception e) {
			logger.error("MyVideo failed " + e);
			Assert.fail("MyVideo failed ", e);
		}
	}

}
