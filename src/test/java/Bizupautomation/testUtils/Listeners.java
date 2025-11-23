package Bizupautomation.testUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;

public class Listeners extends AndroidActions implements ITestListener, ISuiteListener {

	private static final Logger logger = LogManager.getLogger(Listeners.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("Listeners class initialized ✅");
	}

	ExtentReports extent = ExtentReporterNG.getReporterObject();
	private Map<String, ExtentTest> testMap = new HashMap<>(); // Map to track ExtentTest instances
	private List<String> failedTestCases = new ArrayList<>();
	private List<String> failedErrorMessages = new ArrayList<>();
	private boolean hasFailedOrSkippedTests = false;

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		// If test already exists (retry case), reuse it; otherwise, create a new one
		if (!testMap.containsKey(testName)) {
			testMap.put(testName, extent.createTest(testName));
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentTest test = testMap.get(testName);
		test.log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentTest test = testMap.get(testName);
		test.fail(result.getThrowable());
		hasFailedOrSkippedTests = true;

		// Use the static driver from Base class
		AndroidDriver driver = Base.driver;

		if (driver != null) {
			try {
				String screenshotPath = getScreenshotPath(testName, driver);
				test.addScreenCaptureFromPath(screenshotPath, testName + " failed");
			} catch (Exception e) {
				logger.error("Error capturing screenshot: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.error("Skipping screenshot: Driver is null.");
		}

		// Add failed test case to list
		failedTestCases.add(testName);
		failedErrorMessages.add(result.getThrowable().getMessage());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentTest test = testMap.get(testName);
		if (result.wasRetried()) {
			logger.warn("⏳ Test retried and now failed: " + testName);
			test.log(Status.FAIL, "Test failed after retry");
			result.setStatus(ITestResult.FAILURE);
		} else {
			logger.error("⚠️ Test Skipped: " + testName);
			test.log(Status.SKIP, "Test Skipped");
		}
		hasFailedOrSkippedTests = true;
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		// logger.info("Test context finished: " + context.getName());
	}

	@Override
	public void onStart(ISuite suite) {
		logger.info("Test suite started: " + suite.getName());
	}

	@Override
	public void onFinish(ISuite suite) {
		try {
			extent.flush();

			// Only send email if there are failed or skipped tests across the entire suite
			if (hasFailedOrSkippedTests) {
				String appVersionInfo = Base.appVersionMessage;
				// String appVersionInfo = "Demo Version";
				Properties prop = AndroidActions.loadProperties();

				// Get the last test context from the suite
				ITestContext lastContext = suite.getResults().values().iterator().next().getTestContext();
				String emailBody = AndroidActions.buildEmailBody(lastContext, appVersionInfo, prop);
				String reportFilePath = AndroidActions.generateReportFilePath();
				logger.info("Report will be saved at: " + reportFilePath);

				// AndroidActions.sendReportEmailWithLogs(
				// 		"shubham.bizup@gmail.com, soumya@bizup.app, nikhil.mathew@bizup.app, naman.jawa@bizup.app, ankit.yadav@bizup.app",
				// 		"🚀 Bizup Buyer App Test Execution Report",
				// 		emailBody,
				// 		reportFilePath);

						AndroidActions.sendReportEmailWithLogs(
						"shubham.bizup@gmail.com",
						"🚀 Bizup Buyer App Test Execution Report",
						emailBody,
						reportFilePath);
			} else {
				logger.error("No failed test cases across the entire suite. Email not sent.");
			}
		} catch (Exception e) {
			System.err.println("Error in onFinish(): " + e.getMessage());
			e.printStackTrace();
		}
	}
}