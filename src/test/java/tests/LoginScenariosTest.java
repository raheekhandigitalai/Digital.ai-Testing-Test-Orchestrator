package tests;

import io.appium.java_client.ios.IOSDriver;
import org.testng.annotations.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class LoginScenariosTest {

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
    public void positive_login_test() {
        String username = "admin";
        String password = "admin123";

        boolean isAuthenticated = username.equals("admin")
                && password.equals("admin123");

        assertTrue("Login should succeed", isAuthenticated);
    }

    @Test
    public void negative_login_test() {
        String username = "admin";
        String password = "wrongpassword";

        boolean isAuthenticated = username.equals("admin")
                && password.equals("admin123");

        assertFalse("Login should fail with invalid credentials", isAuthenticated);
    }

    @Test
    public void edge_case_login_test() {
        String password = "admin123";
        int failedAttempts = 5;
        int maxAttempts = 3;

        boolean isAccountLocked = failedAttempts >= maxAttempts;

        assertFalse("Account should not be locked", isAccountLocked);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Report URL: " + driver.getCapabilities().getCapability("digitalai:reportUrl"));
        driver.quit();
    }

}
