package Bizupautomation.testUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	static ExtentReports extent;

//	public static ExtentReports getReporterObject() {
//		// ExtentReport, ExtentSparkReporter
//		String path = System.getProperty("user.dir") + "\\reports\\index.html";
//		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
//		reporter.config().setReportName("Test Automation Report");
//		reporter.config().setDocumentTitle("Bizup Test");
//
//		extent = new ExtentReports();
//		extent.attachReporter(reporter);
//		extent.setSystemInfo("TestAutomation", "BuyerApp");
//
//		return extent;
//
//	}

	public static ExtentReports getReporterObject() {
		// Generate a timestamp for the report file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
		String timestamp = sdf.format(new Date());

		// Dynamic report file path with timestamp
		String path = System.getProperty("user.dir") + "\\reports\\TestReport_" + timestamp + ".html";

		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Test Automation Report");
		reporter.config().setDocumentTitle("Bizup Test Report");

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Test Automation", "BuyerApp");
		extent.setSystemInfo("Generated On", new Date().toString()); // Adds a timestamp to the report's system info

		return extent;
	}

}
