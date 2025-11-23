package Bizupautomation.testCases.version_143;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Bizupautomation.testUtils.RetryAnalyzer;
import Bizupautomation.testUtils.Base;
import buyer.pageObjects.SearchPage;

public class TestCartFlow extends Base {

    private static final Logger logger = LogManager.getLogger(TestCartFlow.class); // Logger instance

    static {
        // Force log4j2 to initialize at class load time
        System.setProperty("log4j2.debug", "true");
        logger.info("TestCartFlow class initialized ✅");
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

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify catalog add-to-cart functionality through search flow [Steps:- Search >> Video Tab >> VideoFeed >> Collection >> Add to cart || Search>>Seller Tab>> Seller Card >>Seller Page >> Add to cart]")
    public void SearchCartFlow() throws InterruptedException {
        SearchPage search = new SearchPage(driver);

        logger.info("✨✨------------ Search Cart Flow start ------------✨✨");

        // Click on the search icon
        search.SearchProduct("Fancy Saree");

        // Video Tab
        search.VideoTab();

        // Play Video
        search.PlayVideo();

        // Video Enquiry
        search.VideoToSellerPage();

        search.SellerPageCoachMark();

        search.CatalogTabClick();

        // Catalog Enquiry
        search.CatalogAddToCart();

        // Pay Cart
        search.PayCartPage();

        // Back to Search
        search.Back();
        search.Back();
        search.Back();
        search.Back();

        // Sample Tab
        search.SellerTab();

        search.SellerCard();

        search.SellerPageCoachMark();

        search.CatalogTabClick();

        // Catalog Enquiry
        search.CatalogAddToCart();

        // Pay Cart
        search.PayCartPage();

        logger.info("✨✨------------ Search Cart Flow End ------------✨✨");

    }

}
