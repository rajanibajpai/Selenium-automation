package superAdmin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utility.Constant;

public class SuperAdmin{
	
	static WebDriver  dr;
	static SoftAssert softAssert = new SoftAssert();
	public void login() throws InterruptedException {
		
		//change the browser
		String browser ="Chrome";
		if(browser == "Chrome") {
			System.setProperty("webdriver.chrome.driver", Constant.CHROMEDRIVER);
			dr = new ChromeDriver();
		}
		else {
			System.setProperty("webdriver.gecko.driver", Constant.FIREFOXDRIVER);
			dr = new FirefoxDriver();
		}
		
		//super admin login URL
		dr.get(Constant.URL+"/suadmin");
		//set super admin email
		WebElement username =  dr.findElement(By.name("email"));
		username.sendKeys(Constant.SUADMINEMAIL);
		//set super admin password
		WebElement password = dr.findElement(By.name("password"));
		password.sendKeys(Constant.SUADMINPASSWORD);
		//hit submit button
		WebElement submit  = dr.findElement(By.className("btn-default"));
		submit.submit();
		
		Thread.sleep(3000);
				
	}
	@Test
	public void ChangeTimeZone() throws InterruptedException {
		//login to super admin
		login();
		
		//this will collect all the data with same locator
		List<WebElement> allSchoolLinks =  dr.findElements(By.cssSelector("div#schoolListTable_wrapper table#schoolListTable tbody tr td a.btn-primary"));
		outerloop:
		for(WebElement e:allSchoolLinks) {
			String schoolLink = e.getAttribute("href");
			
			if(schoolLink.contains("/suadmin/edit/school/45")) {
				
				//This will print the full URL of the schools one by one
				System.out.println(schoolLink);
				//This will open the edit form of the particular school with ID 45
				e.click();
				//if we will not come out of the for loop here then it will throw "Stale element error"
				//this happens because we redirect to another page before the loop completes
				break outerloop;
			}
		}
		
		Thread.sleep(6000);
		/*change time zone of the selected school*/
		Select timezone = new Select(dr.findElement(By.id("timeZone")));
		timezone.selectByValue(Constant.TIMEZONE);
		
		WebElement submit = dr.findElement(By.xpath("//input[@value = 'Submit']"));
		submit.click();
		/*End time zone change*/
		//if we get success message compare the web element of the success message
		String successMessage =  dr.findElement(By.cssSelector(".page-content-wrapper .alert-success ul li")).getText();
		//if it matches then the test case script is successful
		Assert.assertTrue(successMessage.contains("successfully updated"));
	}
	
	
}
