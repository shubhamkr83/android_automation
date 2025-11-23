package buyer.pageObjects;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import buyer.utils.AndroidUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ChatPage extends AndroidUtils {

	// private static final Logger logger = LogManager.getLogger(ChatPage.class); //
	// Logger instance

	// static {
	// // Force log4j2 to initialize at class load time
	// System.setProperty("log4j2.debug", "true");
	// // logger.info("ChatPage class initialized ✅");
	// }

	AndroidDriver driver;

	public ChatPage(AndroidDriver driver)

	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.sot.bizup.debug:id/fab")
	private WebElement AgentIcon;

	@AndroidFindBy(xpath = "//android.widget.EditText")
	private WebElement message1;

	@AndroidFindBy(xpath = "//android.widget.Button[@text=\"\"]")
	private WebElement send;

	@AndroidFindBy(xpath = "//android.widget.EditText")
	private WebElement message2;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"ओनर से बात करे\"]")
	private WebElement whatsAppEnableTop;

	@AndroidFindBy(xpath = "//android.widget.Button[@text=\"बात करे\"]")
	private WebElement whatsAppEnableBottom;

	public void clickAgent() {
		AgentIcon.click();
	}

	public void ChatWait() {
		By chatLocator = By.xpath("//android.widget.Image[@text='Salesman Logo']");
		if (waitForElement(chatLocator)) {
			WebElement chat = driver.findElement(chatLocator);
			waitUntilText(chat, "Salesman Logo");
		}
	}

}
