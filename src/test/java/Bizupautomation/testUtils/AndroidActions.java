package Bizupautomation.testUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.nio.file.Paths;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.android.AndroidDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;

import java.util.ArrayList;

public class AndroidActions {

	private static final Logger logger = LogManager.getLogger(AndroidActions.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("AndroidActions class initialized ✅");
	}

	// Use static driver reference to match Base class
	static AndroidDriver driver;

	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {

		// Covert json file content to json string
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	public String getScreenshotPath(String testCaseName, AndroidDriver driver) throws IOException {
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
		// 1. capture and place in folder
		// 2. extent report pick file and attach to report
	}

	public void Terminate(AndroidDriver driver) {
		driver.terminateApp("com.sot.bizup.debug");
	}

	public void RemoveApp(AndroidDriver driver) {
		driver.removeApp("com.sot.bizup.debug");
	}

	public void Restart(AndroidDriver driver) throws InterruptedException {
		driver.terminateApp("com.sot.bizup.debug");
		driver.activateApp("com.sot.bizup.debug");
		Thread.sleep(3000);
	}

	public static void sendEmailWithAttachment(String toEmail, String subject, String body, String filePath)
			throws IOException {
		// Load properties file dynamically for cross-platform support
		String propertiesPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "java" + File.separator + "buyer" + File.separator + "resources" + File.separator
				+ "data.properties";

		Properties prop = new Properties();
		try (FileInputStream fis = new FileInputStream(propertiesPath)) {
			prop.load(fis);
		}

		String fromEmail = prop.getProperty("EMAIL_USERNAME");
		String password = prop.getProperty("EMAIL_PASSWORD");

		if (fromEmail == null || password == null) {
			throw new IllegalArgumentException("EMAIL_USERNAME or EMAIL_PASSWORD environment variables are not set.");
		}

		if (toEmail == null || toEmail.isEmpty()) {
			throw new IllegalArgumentException("Recipient email address (toEmail) cannot be null or empty.");
		}

		// SMTP server configuration
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Create session
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});

		try {
			// Create email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);

			// Create email body part with HTML content
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html; charset=utf-8"); // ✅ Ensures HTML is rendered properly

			// Add attachment
			MimeBodyPart attachmentPart = new MimeBodyPart();
			File file = new File(filePath);
			if (!file.exists()) {
				throw new Exception("Attachment file not found: " + filePath);
			}
			attachmentPart.attachFile(filePath);

			// Combine parts
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentPart);

			// Set content
			message.setContent(multipart);

			// Send email
			Transport.send(message);
			logger.info("✨✨ Email with attachment sent successfully! ✅");
		} catch (AuthenticationFailedException e) {
			logger.error("❌ Authentication failed! Check your email credentials or enable App Passwords.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("❌ Error occurred while sending email: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Properties loadProperties() {
		try {
			String propertiesPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
					+ File.separator + "java" + File.separator + "buyer" + File.separator + "resources" + File.separator
					+ "data.properties";
			Properties prop = new Properties();
			try (FileInputStream fis = new FileInputStream(propertiesPath)) {
				prop.load(fis);
			}
			return prop;
		} catch (Exception e) {
			logger.error("Error loading properties: " + e.getMessage());
			e.printStackTrace();
			return new Properties(); // Return empty properties as fallback
		}
	}

	public static class TestStatistics {
		public int totalTests;
		public int passedTests;
		public int failedTests;
		public int skippedTests;

		public TestStatistics(int totalTests, int passedTests, int failedTests, int skippedTests) {
			this.totalTests = totalTests;
			this.passedTests = passedTests;
			this.failedTests = failedTests;
			this.skippedTests = skippedTests;
		}
	}

	public static TestStatistics getTestStatistics(ITestContext context) {
		int totalTests = context.getAllTestMethods().length;
		int passedTests = context.getPassedTests().size();
		int failedTests = (int) context.getFailedTests().getAllResults().stream()
				.filter(result -> !result.wasRetried())
				.count();
		int skippedTests = (int) context.getSkippedTests().getAllResults().stream()
				.filter(result -> !result.wasRetried())
				.count();
		return new TestStatistics(totalTests, passedTests, failedTests, skippedTests);
	}

	public static String buildEmailBody(ITestContext context, String appVersionInfo, Properties prop) {
		String testName = context.getCurrentXmlTest().getName();
		if (testName.matches(".*-\\d+$")) {
			testName = testName.replaceAll("-\\d+$", "");
		}

		String BSEmail = prop.getProperty("BROWSERSTACK_EMAIL", "Not Provided");
		String BS_BUILD_ID = prop.getProperty("BROWSERSTACK_BUILD_ID", "Unknown_Build_ID");
		String BS_DASHBOARD_LINK = "https://app-automate.browserstack.com/dashboard/v2/builds/" + BS_BUILD_ID;

		if (BS_BUILD_ID.equals("Unknown_Build_ID")) {
			logger.error("Warning: BrowserStack Build ID is missing. Check data.properties!");
		}

		TestStatistics stats = getTestStatistics(context);

		StringBuilder body = new StringBuilder();
		body.append("<html><body style='font-family: Arial, sans-serif;'>");
		body.append("<h2 style='color: #2E86C1; text-align: center;'>🔹🔹 Test Automation Summary 🔹🔹</h2>");
		body.append("<h3 style='color: #1F618D; text-align: center;'>📱 " + appVersionInfo + " 📱</h3>");
		body.append("<h2 style='color: #2E86C1; text-align: center;'>🚀 Test Name : <b>" + testName
				+ "</b> 🚀</h2>");

		body.append(
				"<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%; max-width: 600px;'>");
		body.append("<tr style='background-color: #2E86C1; color: white;'>");
		body.append("<th>📌 Metric</th><th>📊 Count</th>");
		body.append("</tr>");
		body.append("<tr><td>Total Tests</td><td><b>" + stats.totalTests + "</b></td></tr>");
		body.append("<tr><td style='color: green;'>✅ Passed</td><td><b>" + stats.passedTests + "</b></td></tr>");
		body.append("<tr><td style='color: red;'>❌ Failed</td><td><b>" + stats.failedTests + "</b></td></tr>");
		body.append("<tr><td style='color: orange;'>⚠️ Skipped</td><td><b>" + stats.skippedTests + "</b></td></tr>");
		body.append("</table><br>");

		body.append("<h3>📋 Detailed Test Case Report 📋</h3>");
		body.append(
				"<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%; max-width: 800px;'>");
		body.append("<tr style='background-color: #2E86C1; color: white;'>");
		body.append("<th>🔢 S No. </th><th>📝 Test Case Name</th><th>📄 Description</th><th>📌 Status</th>");
		body.append("</tr>");

		int index = 1;
		for (ITestNGMethod method : context.getAllTestMethods()) {
			String testCaseName = method.getMethodName();
			String description = method.getDescription();
			String status = "✅ Passed";
			if (context.getFailedTests().getResults(method).stream().anyMatch(result -> !result.wasRetried())) {
				status = "❌ Failed";
			} else if (context.getSkippedTests().getResults(method).stream()
					.anyMatch(result -> !result.wasRetried())) {
				status = "⚠️ Skipped";
			}

			body.append("<tr>");
			body.append("<td>" + index++ + "</td>");
			body.append("<td><b>" + testCaseName + "</b></td>");
			body.append(
					"<td>" + (description == null || description.isEmpty() ? "No description provided" : description)
							+ "</td>");
			body.append("<td style='font-weight: bold; color: "
					+ (status.contains("Failed") ? "red" : status.contains("Skipped") ? "orange" : "green") + ";'>"
					+ status + "</td>");
			body.append("</tr>");
		}
		body.append("</table><br>");

		if (stats.failedTests > 0) {
			body.append("<h3 style='color: red;'>🚨🚨 Some test cases failed 🚨🚨</h3>");
		} else if (stats.skippedTests > 0) {
			body.append("<h3 style='color: orange;'>⚠️⚠️ All test cases completed, but some were skipped! ⚠️⚠️</h3>");
		} else if (stats.passedTests == stats.totalTests) {
			body.append("<h3 style='color: green;'>🎉🎉 All test cases passed successfully 🎉🎉</h3>");
		}

		body.append("<h3><b>🌐 BrowserStack Build Video Link:</b> <a href='" + BS_DASHBOARD_LINK
				+ "' target='_blank'>Click Here</a></h3>");
		body.append("<h3><b>🔑 Login Email: </b>" + BSEmail + "</h3>");
		body.append("<p>📎 Please find the attached test report and log file for detailed results.</p>");
		body.append("<p>Regards,<br><b>QA Automation Team</b></p>");
		body.append("</body></html>");

		return body.toString();
	}

	public static String generateReportFilePath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
		String timestamp = sdf.format(new Date());

		return Paths.get(System.getProperty("user.dir"), "reports", "TestReport_" + timestamp + ".html").toString();
	}

	public static void sendReportEmail(String toEmail, String subject, String body, String attachmentPath) {
		try {
			// Assuming sendEmailWithAttachment is defined elsewhere (e.g., in Listeners or
			// another util)
			Listeners.sendEmailWithAttachment(toEmail, subject, body, attachmentPath);
		} catch (Exception e) {
			logger.error("Error sending email: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void sendEmailWithMultipleAttachments(String toEmail, String subject, String body,
			List<String> filePaths)
			throws IOException {
		// Load properties file dynamically for cross-platform support
		String propertiesPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "java" + File.separator + "buyer" + File.separator + "resources" + File.separator
				+ "data.properties";

		Properties prop = new Properties();
		try (FileInputStream fis = new FileInputStream(propertiesPath)) {
			prop.load(fis);
		}

		String fromEmail = prop.getProperty("EMAIL_USERNAME");
		String password = prop.getProperty("EMAIL_PASSWORD");

		if (fromEmail == null || password == null) {
			throw new IllegalArgumentException("EMAIL_USERNAME or EMAIL_PASSWORD environment variables are not set.");
		}

		if (toEmail == null || toEmail.isEmpty()) {
			throw new IllegalArgumentException("Recipient email address (toEmail) cannot be null or empty.");
		}

		// SMTP server configuration
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Create session
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});

		try {
			// Create email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);

			// Create email body part with HTML content
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html; charset=utf-8"); // ✅ Ensures HTML is rendered properly

			// Combine parts
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Add all attachments
			for (String filePath : filePaths) {
				File file = new File(filePath);
				if (file.exists()) {
					MimeBodyPart attachmentPart = new MimeBodyPart();
					attachmentPart.attachFile(filePath);
					multipart.addBodyPart(attachmentPart);
					logger.info("Added attachment: " + filePath);
				} else {
					logger.error("Attachment file not found: " + filePath);
				}
			}

			// Set content
			message.setContent(multipart);

			// Send email
			Transport.send(message);
			logger.info("✨✨ Email with multiple attachments sent successfully! ✅");
		} catch (AuthenticationFailedException e) {
			logger.error("❌ Authentication failed! Check your email credentials or enable App Passwords.");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("❌ Error occurred while sending email: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static String generateCurrentLogFilePath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
		String timestamp = sdf.format(new Date());

		return Paths.get(System.getProperty("user.dir"), "logs", "TestLogs_" + timestamp + ".log").toString();
	}

	public static void sendReportEmailWithLogs(String toEmail, String subject, String body, String reportPath) {
		try {
			// Get the current log file
			String logFilePath = generateCurrentLogFilePath();

			// Create a list of attachments
			List<String> attachments = new ArrayList<>();
			attachments.add(reportPath);
			attachments.add(logFilePath);

			// Send email with multiple attachments
			sendEmailWithMultipleAttachments(toEmail, subject, body, attachments);
			logger.info("✅ Email sent with test report and logs!");
		} catch (Exception e) {
			logger.error("❌ Error sending email with logs: " + e.getMessage());
			e.printStackTrace();
		}
	}

}