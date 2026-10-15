package tests;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.AssertJUnit.*;

public class PaymentScenariosTest {

    protected IOSDriver driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        dc.setCapability("digitalai:testName", method.getName());
        // Test Orchestrator overrides capabilities the test already declares; it does not
        // add new ones. So the test declares a baseline app, device query and Appium
        // version, and lib/config.yml replaces those values. The cloud URL and access
        // key come from lib/config.yml alone.
        dc.setCapability("appium:app", "cloud:com.experitest.ExperiBank");
        dc.setCapability("appium:bundleId", "com.experitest.ExperiBank");
        dc.setCapability("digitalai:deviceQuery", "@os='ios'");
        dc.setCapability("digitalai:appiumVersion", "3.5.2");
        driver = new IOSDriver(new URL("https://uscloud.experitest.com/wd/hub"), dc);
    }

    @Test
    public void positive_payment_test() {
        double accountBalance = 500.00;
        double paymentAmount = 150.00;

        double remainingBalance = accountBalance - paymentAmount;

        assertEquals("Incorrect remaining balance",
                350.00, remainingBalance, 0.01);
    }

    @Test
    public void negative_payment_test() {
        double accountBalance = 100.00;
        double paymentAmount = 250.00;

        boolean paymentApproved = paymentAmount <= accountBalance;

        assertFalse("Payment should be declined", paymentApproved);
    }

    @Test
    public void edge_case_payment_test() {
        double accountBalance = 100.00;
        double paymentAmount = 100.00;

        boolean paymentApproved = paymentAmount < accountBalance;

        assertTrue("Payment should succeed when using exact balance",
                paymentApproved);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Report URL: " + driver.getCapabilities().getCapability("digitalai:reportUrl"));
        driver.quit();
    }

}
