package buyer.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class EventsPage {

	private static final Logger logger = LogManager.getLogger(EventsPage.class); // Logger instance

	static {
		// Force log4j2 to initialize at class load time
		System.setProperty("log4j2.debug", "true");
		// logger.info("EventsPage class initialized ✅");
	}

	private AndroidDriver driver;
	private String jsonString;

	public EventsPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);

		try {
			String filePath = "src/test/java/Bizupautomation/testData/Events.json";
			jsonString = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
		} catch (Exception e) {
			logger.error("Error loading JSON file: " + e.getMessage());
			jsonString = "{}"; // Default to empty JSON
		}
	}

	private JSONArray getEvents(String page, String category) {
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONObject events = jsonObject.getJSONObject("events");
		JSONObject pageObject = events.getJSONObject(page);

		if (pageObject.has(category)) {
			return pageObject.getJSONArray(category);
		} else {
			throw new IllegalArgumentException("Category not found: " + category);
		}
	}

	public void getAndTriggerEventsFromCategory(String page, String category, String eventType) {
		try {
			JSONArray eventArray = getEvents(page, category);

			if ("individual".equals(eventType)) {
				triggerIndividualEvent(eventArray);
				logger.info("Event " + eventArray.get(0) + " triggered successfully.");
			} else {
				triggerMultipleEvents(eventArray);
				logger.info("Events " + eventArray + " triggered successfully.");
			}
		} catch (Exception e) {
			logger.error("Error triggering events: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void triggerIndividualEvent(JSONArray eventArray) throws Exception {
		JSONObject event = eventArray.getJSONObject(0);
		triggerEvent(event.getString("event"));
	}

	private void triggerMultipleEvents(JSONArray eventArray) throws Exception {
		for (int i = 0; i < eventArray.length(); i++) {
			JSONObject event = eventArray.getJSONObject(i);
			triggerEvent(event.getString("event"));
		}
	}

	public void clickButtonAndTriggerEvents(String page, String id, String eventType) {
		try {
			// Fetch the JSON array for the specified category (page and category are the
			// same here)
			JSONArray eventArray = getEvents(page, page);

			// Find the event object with the specified ID
			JSONObject targetEvent = null;
			for (int i = 0; i < eventArray.length(); i++) {
				JSONObject event = eventArray.getJSONObject(i);
				if (event.getString("id").equals(id)) {
					targetEvent = event;
					break;
				}
			}

			// If the event was not found, throw an exception
			if (targetEvent == null) {
				throw new Exception("Event with ID '" + id + "' not found in category '" + page + "'.");
			}

			// Retrieve the locator from the JSON object
			String locator = targetEvent.getString("locator");
			WebElement button;

			// Determine the locator type (XPath or ID) and find the element
			if (locator.startsWith("//") || locator.startsWith("(")) {
				button = driver.findElement(By.xpath(locator));
			} else {
				button = driver.findElement(By.id(locator));
			}

			// Perform the click
			button.click();

			// Trigger events based on eventType
			getAndTriggerEventsFromCategory(page, page, eventType);

		} catch (Exception e) {
			logger.error("Error clicking button or triggering events: " + e.getMessage());
		}
	}

	public void triggerEvent(String eventName) {
		logger.info("Event " + eventName + " triggered successfully.");
	}
}