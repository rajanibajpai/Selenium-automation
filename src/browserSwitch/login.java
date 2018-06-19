
package browserSwitch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class login {

	public static void main(String[] args) {
		
		/*This simple login script is to show how you can switch your browser driver while running your script*/
		
		//Include all the browsers driver exe files here
		System.setProperty("webdriver.gecko.driver", "C:/Selenium/geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", "C:/Selenium/chromedriver.exe");
		
		//pass the browser name in which you want to run automation
		String browser = "Chrome";
		
		WebDriver driver = null;
		if(browser.equals("Chrome")){
			driver = new ChromeDriver();
		}
		else if(browser.equals("Firefox")){
			driver = new FirefoxDriver();
		}
		
		driver.get("https://yoururl.com/login");
		
		//set user email as login credential
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("youremail@gmail.com");
		
		//set user password as login credential
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("yourpassword");
		
		//Find and hit the submit button on login page
		WebElement submit = driver.findElement(By.id("login-submit"));
		submit.click();
		try{
			Thread.sleep(5500);
		}
		catch(InterruptedException e){
			System.out.println("there is some error");
		}
		
		//if user is logged in then there surely will be a Logout link.
		String logout = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/a[2]")).getText();
		
		if(logout == "Logout"){
			//If logout button exists then login is successful
			System.out.println("User is logged in");
		}
		else{
			//If logout button doesn't exist then login is unsuccessful
			System.out.println("Error in login");
		}
	}

}






