package Bizupautomation.testCases.version_148;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Bizupautomation.testUtils.RetryAnalyzer;
import Bizupautomation.testUtils.Base;
import buyer.pageObjects.SearchPage;

public class TestSearchSellerChat extends Base {

    private static final Logger logger = LogManager.getLogger(TestSearchSellerChat.class); // Logger instance

    static {
        // Force log4j2 to initialize at class load time
        System.setProperty("log4j2.debug", "true");
        logger.info("TestSearchSellerChat class initialized ✅");
    }

    @BeforeMethod
    public void setupMethod() {
        // Reset app state before each test method
        if (driver != null) {
            try {
                // Restart the app to ensure a clean state for each test
                driver.terminateApp("com.sot.bizup.debug");
                driver.activateApp("com.sot.bizup.debug");
                Thread.sleep(2000); // Give app time to stabilize
                logger.info("App restarted before test method ✅");
            } catch (Exception e) {
                logger.error("Error resetting app state: " + e.getMessage());
            }
        }
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify enquiry functionality through search flow [Steps:- Search >> Seller Tab >> Seller Page >> Chat Button Click >> Chat Page]")
    public void SearchSellerChatFlow() throws InterruptedException {
        SearchPage search = new SearchPage(driver);

        logger.info("✨✨------------ Search Seller Chat Flow start ------------✨✨");

        // Click on the search icon
        search.SearchProduct("Saree");

        search.SellerTab();

        // Seller Card
        search.SellerCard();

        // Catalog
        search.CatalogSelect();

        // Seller Enquiry
        search.SellerEnquiry();

        logger.info("✨✨------------ Search Seller Chat Flow End ------------✨✨");

    }

}
