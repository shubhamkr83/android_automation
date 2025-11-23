package Bizupautomation.testCases.version_148;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Bizupautomation.testUtils.RetryAnalyzer;
import Bizupautomation.testUtils.Base;
import buyer.pageObjects.HomePage;
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

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify catalog add-to-cart functionality through video flow [Steps:- Home >> Video >> Collection >> Add to cart]")
    public void HomeVideoCartFlow() throws InterruptedException {
        HomePage home = new HomePage(driver);

        logger.info("✨✨------------ Home Video Cart Flow Start ------------✨✨");

        // Product Filter
        home.HomeProductSelect("Cotton Saree");

        // Home to seller page
        home.HomeToVideoFeed();

        // Video to collection
        home.VideoToCollection();

        // Catalog Enquiry
        home.CatalogAddToCart();

        // Pay Cart
        home.PayCartPage();

        logger.info("✨✨------------ Home Video Cart Flow End ------------✨✨");
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verify catalog add-to-cart functionality through search flow [Steps:- Search >> Video Tab >> VideoFeed >> Collection >> Add to cart || Search >> Sample Tab >> Collection >> Add to cart || Search>>Seller Tab>> Seller Card >>Seller Page >> Add to cart]")
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
        search.VideoToCollection();

        // Catalog Enquiry
        search.CatalogAddToCart();

        // Pay Cart
        search.PayCartPage();

        // Back to Search
        search.Back();
        search.Back();
        search.Back();

        // Sample Tab
        search.SampleTab();

        // Sample Enquiry
        search.SampleToCollection();

        // Catalog Enquiry
        search.CatalogAddToCart();

        // Pay Cart
        search.PayCartPage();

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

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Verify Homepage cart functionality [Steps:- Homepage >> CartPage]")
    public void HomepageCartFlow() {
        HomePage home = new HomePage(driver);

        logger.info("✨✨------------ HomePage Cart Flow Start ------------✨✨");

        home.HomeWait();

        home.CartButton();

        home.CartPageCheck();

        home.PayCartPage();

        logger.info("✨✨------------ HomePage Cart Flow End ------------✨✨");
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify Homepage cart functionality [Steps:- Homepage >> OrderTab >> CartPage]")
    public void OrderpageCartFlow() {
        HomePage home = new HomePage(driver);

        logger.info("✨✨------------ OrderTab Cart Flow Start ------------✨✨");

        home.HomeWait();

        home.OrderTab();

        home.OrderTabCartButton();

        home.CartPageCheck();

        home.PayCartPage();

        logger.info("✨✨------------ OrderTab Cart Flow End ------------✨✨");
    }

}
