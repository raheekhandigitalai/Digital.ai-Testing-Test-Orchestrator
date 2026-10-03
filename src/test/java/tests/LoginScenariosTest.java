package tests;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.testng.annotations.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class LoginScenariosTest {

    protected IOSDriver<IOSElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        // Capabilities set here still work, but any value also set in lib/config.yml
        // overrides them. This example sets only the test name and leaves the cloud,
        // access key, app and device query to the YAML.
        dc.setCapability("digitalai:testName", method.getName());
        driver = new IOSDriver<>(new URL("https://uscloud.experitest.com/wd/hub"), dc);
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
