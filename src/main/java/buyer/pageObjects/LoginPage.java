// LoginPage.java
package buyer.pageObjects;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage extends HomePage {

	private static final Logger logger = LogManager.getLogger(LoginPage.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("LoginPage class initialized ✅");
	}

	private final AndroidDriver driver;
	private static final String APK_PATH = "C:\\Users\\lenovo\\eclipse-workspace\\bizup\\src\\test\\java\\resources\\Bizup-3.0.1-debug(148).apk";
	private static final String APP_PACKAGE = "com.sot.bizup.debug";

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivHindiBg")
	private WebElement hindi;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvEnglish")
	private WebElement english;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbContinue")
	private WebElement continueBtn;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvTitle")
	private WebElement loginTitle;

	@AndroidFindBy(id = "com.truecaller:id/tv_continueWithDifferentNumber")
	private WebElement trueCaller;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/etMobileNo")
	private WebElement mobileNumber;

	public LoginPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public void LoginLang() {
		String expectedTextEng = "Unleash your potential";
		String expectedTextHindi = "सफलता की ओर आगे बढे";
		String currentText = loginTitle.getText();

		try {
			if (currentText.equals(expectedTextEng)) {
				logger.info("English Language implemented in login page ✅");
				logger.info("Text:- " + currentText);
			} else if (currentText.equals(expectedTextHindi)) {
				logger.info("Text:- " + currentText);
				logger.info("Hindi Language implemented in login page ✅");
			}
		} catch (Exception e) {
			logger.error("Language not implemented in login page ❌");
			Assert.fail("Language not implemented in login page ❌" + e);
		}

	}

	public LoginPage setMobileNumber(String number) {
		try {
			handleTrueCaller();
			mobileNumber.sendKeys(number);
			logger.info(number + " Mobile number set successfully ✅");

		} catch (Exception e) {
			logger.error("Failed to set mobile number: " + e.getMessage());
			throw new RuntimeException("Mobile number setup failed", e);
		}
		return this;
	}

	private void handleTrueCaller() {
		By trueCallerLocator = By.id("com.truecaller:id/tv_continueWithDifferentNumber");
		if (driver.findElements(trueCallerLocator).size() > 0) {
			driver.findElement(trueCallerLocator).click();
			logger.info("TrueCaller handled ✅");
		} else {
			logger.error("TrueCaller not enabled on device ❌");
		}
	}

	public LoginPage SelectLanguage() {
		try {
			By langTitle = By.id("com.sot.bizup.debug:id/tvChooseLanguage");
			if (driver.findElements(langTitle).size() > 0) {
				String language = english.getText();
				english.click();
				logger.info(language + " Language selected successfully ✅");
				continueBtn.click();
				logger.info("Continue Button clicked ✅");

			}
		} catch (Exception e) {
			logger.error("Language selection failed: " + e.getMessage());
			Assert.fail("Language selection failed", e);
		}
		return this;
	}

	public LoginPage Install() {
		try {
			if (!driver.isAppInstalled(APP_PACKAGE)) {
				driver.installApp(APK_PATH);
				logger.info("App installed successfully ✅");
			} else {
				logger.info("App already installed ✅");
				clearAppData();
			}
		} catch (Exception e) {
			logger.error("Installation failed: " + e.getMessage());
			Assert.fail("App installation failed", e);
		}
		return this;
	}

	public LoginPage Login() {
		try {
			String randomNumber = generateRandomPhoneNumber();
			return setMobileNumber(randomNumber);
		} catch (Exception e) {
			logger.error("Login failed: " + e.getMessage());
			Assert.fail("Login process failed", e);
		}
		return null;
	}

	private String generateRandomPhoneNumber() {
		Random rand = new Random();
		StringBuilder phoneNumber = new StringBuilder("11");
		for (int i = 0; i < 8; i++) {
			phoneNumber.append(rand.nextInt(10));
		}
		return phoneNumber.toString();
	}
}