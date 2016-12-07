package Examples;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

public class FindingElements {

	AndroidDriver<WebElement> dr;

	@BeforeTest
	public void init() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Redmi");
		capabilities.setCapability("platformVersion", "5.0.2");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("app", "C:\\Automation\\Workspace\\Appium\\apk\\FamilyFinder.apk");
		dr = new AndroidDriver<WebElement>(new URL("http://192.168.1.42:4723/wd/hub/"), capabilities);
		dr.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}

	@Test
	public void firstTest() throws MalformedURLException {
		loginApp();
		checkIn();

	}

	@AfterTest
	public void AfterTest() throws InterruptedException {
//		Thread.sleep(10000);
		if (dr != null) {
			dr.quit();
		}

	}

	public void loginApp() {
		WebElement emailField = dr.findElement(By.id("com.family.finderapp:id/login_enter_fields_email_edit_text"));
		emailField.sendKeys("zevs@mailinator.com");
		dr.hideKeyboard();
		WebElement continueButton = dr
				.findElement(By.id("com.family.finderapp:id/login_enter_fields_continue_text_view"));
		continueButton.click();
		dr.findElementById("com.family.finderapp:id/login_sign_in_password_edit_text").sendKeys("654321");
		dr.hideKeyboard();
		dr.findElementById("com.family.finderapp:id/login_sing_in_get_started_text_view").click();
		if (tryFindElementById("com.family.finderapp:id/alert_button_negative") != null) {
			tryFindElementById("com.family.finderapp:id/alert_button_negative").click();
		}
		if (tryFindElementById("com.family.finderapp:id/alert_button_negative") != null) {
			tryFindElementById("com.family.finderapp:id/alert_button_negative").click();
		}
		Assert.assertNotNull(tryFindElementById("com.family.finderapp:id/toolbar_title"));
	}

	public void checkIn() {
		dr.findElementById("com.family.finderapp:id/toolbar_right_view_second_ic").click();
		int times = 0;
		while (true) {

			try {
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (tryFindElementById("com.family.finderapp:id/check_in_dialog_address") != null) {
				break;
			}

			times++;
			if (times > 25) {
				break;
			}

		}
		
		Assert.assertNotNull(tryFindElementById("com.family.finderapp:id/check_in_dialog_address"));
	}
	
	private WebElement tryFindElementById(String name) {
		WebElement foundElement = null;
		try {
			foundElement = dr.findElementById(name);
		} catch(Exception e) {
			
		}
		return foundElement;
	}
}
