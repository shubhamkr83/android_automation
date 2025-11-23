package Bizupautomation.testUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Base extends AndroidActions {

	private static final Logger logger = LogManager.getLogger(Base.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		logger.info("Base class initialized ✅");
	}

	public static AndroidDriver driver;
	protected static AppiumDriverLocalService service;
	private static Properties prop;

	// ------ Local Appium File Path -----
	public static String NODE_JS_MAIN_PATH = "C:\\Users\\HP\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";

	// ------ Jenkins Appium File Path -----
	// private static final String NODE_JS_MAIN_PATH = "/home/ubuntu/.nvm/versions/node/v20.15.0/lib/node_modules/appium/build/lib/main.js";

	public static String appVersionMessage = "Running tests for app version: Local Execution"; // Default message

	@BeforeSuite(alwaysRun = true)
	public void configureAppium() throws URISyntaxException, IOException {
		// Load properties using the static method from AndroidActions
		prop = AndroidActions.loadProperties();
		if (prop == null || prop.isEmpty()) {
			throw new RuntimeException(
					"Failed to load properties from data.properties. Check file path and contents.");
		}

		loadBrowserStackConfig(); // Load YAML config

		// Print which app is running
		String browserStackConfig = System.getProperty("browserstack.config");
		// Simple condition to print the correct app version
		if ("browserstack_app151.yml".equals(browserStackConfig)) {
			logger.info("✨✨✨------------ Running tests on App Version: 3.0.4 (151) -----------✨✨✨");
			appVersionMessage = "🔹🔹 Test Executed on App Version: 3.0.4 (151) 🔹🔹";
		} else if ("browserstack_app148.yml".equals(browserStackConfig)) {
			logger.info("✨✨✨------------ Running tests on App Version: 3.0.1 (148) -----------✨✨✨");
			appVersionMessage = "🔹🔹 Test Executed on App Version: 3.0.1 (148) 🔹🔹";
		} else if ("browserstack_app143.yml".equals(browserStackConfig)) {
			logger.info("✨✨✨------------ Running tests on App Version: 2.16.4 (143) ----------✨✨✨");
			appVersionMessage = "🔹🔹 App Version: 2.16.4 (143) 🔹🔹";
		} else {
			logger.info("Running tests for app: Local Execution");
		}

		String ipAddress = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress")
				: prop.getProperty("ipAddress");
		String port = prop.getProperty("port");

		startAppiumServer(ipAddress, Integer.parseInt(port));

		// Set capabilities
		UiAutomator2Options options = new UiAutomator2Options();
		// options.setDeviceName(deviceName);
		// options.setApp(APP_PATH); // Uncomment if needed

		driver = new AndroidDriver(service.getUrl(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		logger.info("✅ Base constructor called. Driver initialized: " + (driver != null));
	}

	private void loadBrowserStackConfig() {
		try {
			// Get YAML file from system property or default to browserstack_app1.yml
			String yamlFile = System.getProperty("browserstack.config", "browserstack_app151.yml");
			logger.info("Loading BrowserStack config: " + yamlFile);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load YAML file: " + e);
		}
	}

	private void startAppiumServer(String ipAddress, int port) {
		if (service == null) {
			service = new AppiumServiceBuilder().withAppiumJS(new File(NODE_JS_MAIN_PATH)).withIPAddress(ipAddress)
					.usingPort(port).build();
			service.start();
		}
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		if (service != null) {
			service.stop();
		}
	}
}

// ----------------------------------------------------------------------------------------------------

// --------------------------------------- Local
// ------------------------------------------
// package Bizupautomation.testUtils;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.net.URISyntaxException;
// import java.time.Duration;
// import java.util.Properties;

// import org.testng.annotations.AfterClass;
// import org.testng.annotations.BeforeClass;

// import io.appium.java_client.android.AndroidDriver;
// import io.appium.java_client.android.options.UiAutomator2Options;
// import io.appium.java_client.service.local.AppiumDriverLocalService;
// import io.appium.java_client.service.local.AppiumServiceBuilder;

// public class Base extends AndroidActions {

// public static AndroidDriver driver;
// public AppiumDriverLocalService service;
// public static String NodeJsMainPath =
// "C:\\\\Users\\\\lenovo\\\\AppData\\\\Roaming\\\\npm\\\\node_modules\\\\appium\\\\build\\\\lib\\\\main.js";
// public static String App =
// "C:\\Users\\lenovo\\eclipse-workspace\\bizup\\src\\test\\java\\resources\\Bizup-2.15.0-debug.apk";

// @BeforeClass(alwaysRun = true)
// public void ConfigureAppium() throws URISyntaxException, IOException {
// // Run appium server automatically
// Properties prop = new Properties();
// FileInputStream fis = new FileInputStream(
// System.getProperty("user.dir") +
// "\\src\\main\\java\\buyer\\resources\\data.properties");
// prop.load(fis);
// String ipAddress = System.getProperty("ipAddress") != null ?
// System.getProperty("ipAddress")
// : prop.getProperty("ipAddress");
// // String ipAddress = prop.getProperty("ipAddress");
// String port = prop.getProperty("port");
// String Device1 = prop.getProperty("Device1");

// service = new AppiumServiceBuilder().withAppiumJS(new
// File(NodeJsMainPath)).withIPAddress(ipAddress)
// .usingPort(Integer.parseInt(port)).build();

// // Create capablities
// UiAutomator2Options options = new UiAutomator2Options();
// options.setDeviceName(Device1);
// // options.setApp(App);

// driver = new AndroidDriver(service.getUrl(), options);
// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

// }

// @AfterClass(alwaysRun = true)
// public void tearDown() {
// if (driver != null) {
// driver.quit();
// }
// if (service != null) {
// service.stop();
// }
// }

// }
