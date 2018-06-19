package userManagement;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
//import all constants defined under utility package
import utility.Constant;

public class UpdateProfile{
	
	static WebDriver driver;
	@BeforeClass
	public static void openLoginPage() {
		
		//you can change driver from here
		String browser ="chrome";
		
		if(browser == "chrome") {
			System.setProperty("webdriver.chrome.driver",Constant.CHROMEDRIVER);
			driver = new ChromeDriver();
		}
		else if(browser == "firefox") {
			System.setProperty("webdriver.gecko.driver", Constant.FIREFOXDRIVER);
			driver = new FirefoxDriver();
		}
	}
	
	//this is a global login function
	public static void manualLogin(String userEmail, String userPassword) {
		
		driver.get(Constant.URL+"/login");
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys(userEmail);
		
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys(userPassword);
		
		WebElement submit = driver.findElement(By.name("login-submit"));
		submit.click();
	}

	@Test(priority=3)
	public void updateAdminProfile() throws InterruptedException
	{
		//send parameters to the function and login as an admin
		manualLogin(Constant.ADMINEMAIL,Constant.ADMINPASSWORD);
		Thread.sleep(5000);
		
        //hit the my profile link on admin dashboard
        WebElement editProfile = driver.findElement(By.partialLinkText("My Profile"));
        editProfile.click();
        Thread.sleep(3000);
        //Browse teacher profile pic
        WebElement profilePath = driver.findElement(By.name("upload_image"));
        //set the path of your local drive
        profilePath.sendKeys(Constant.LOGOPATH);
        
        //set admin's title
        Select title = new Select(driver.findElement(By.name("initials")));
        title.selectByVisibleText("Miss.");
        
        //set admin's first name
        WebElement firstName = driver.findElement(By.name("firstname"));
        firstName.clear();
        firstName.sendKeys("Rajani");
        
        //set admin's last name
        WebElement lastName = driver.findElement(By.name("lastname"));
        lastName.clear();
        lastName.sendKeys("Bajpai");
        
        //set the searchable priority to "No". This makes an admin searchable to other users in the system.
        WebElement searchable =driver.findElement(By.cssSelector("input[value='1']"));
        searchable.click();
       
        //set unique pin for the admin
        WebElement teacherPin = driver.findElement(By.name("teacher_pin"));
        teacherPin.clear();
        teacherPin.sendKeys(Constant.ADMINPIN);
        
        //hit submit button to save all the profile details
        WebElement update = driver.findElement(By.cssSelector("div.box-footer button.btn-success"));
        update.submit();
        Thread.sleep(18000);
        //Get the success message
		String successMessage =driver.findElement(By.className("alert-success")).getText();
		
		boolean actualResult=false;
		if(successMessage !="") {
			actualResult = true;
		}
		//Check if test case pass or not
		Assert.assertEquals(true,actualResult);
	}
	
	@Test(priority=2)
	public void updateTeacherProfile() throws InterruptedException
	{
		//send parameters to the function and login as a teacher
		manualLogin(Constant.TEACHEREMAIL,Constant.TEACHERPASSWORD);
		
		Thread.sleep(5000);
		
        //hit the my profile link
        WebElement editProfile = driver.findElement(By.partialLinkText("My Profile"));
        editProfile.click();
        //Browse teacher's profile picture
        WebElement profilePath = driver.findElement(By.name("upload_image"));
        //set the path of your local drive
        profilePath.sendKeys(Constant.PROFILEPIC);
        
        //set teacher's title
        Select title = new Select(driver.findElement(By.name("initials")));
        title.selectByVisibleText("Miss.");
        
        //set teacher's first name
        WebElement firstName = driver.findElement(By.name("firstname"));
        firstName.clear();
        firstName.sendKeys("Rajani");
        
        //set teacher's password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("password");
        
        //set the searchable priority to "No". This will restrict all the other users to search this teacher.
        WebElement searchable =driver.findElement(By.cssSelector("input[value='1']"));
        searchable.click();
        
        //set unique PIN for the teacher 
        WebElement teacherPin = driver.findElement(By.name("teacher_pin"));
        teacherPin.clear();
        teacherPin.sendKeys(Constant.TEACHERPIN);
        
        //hit submit button and save teacher's profile details.
        WebElement save = driver.findElement(By.cssSelector("div.box-footer button.btn-success"));
        save.submit();
        
        Thread.sleep(18000);
        //Get the success message
		String successMessage =driver.findElement(By.className("alert-success")).getText();
		
		boolean actualResult=false;
		if(successMessage !="") {
			actualResult = true;
		}
		//Check if test case pass or not
		Assert.assertEquals(true,actualResult);
		WebElement logout = driver.findElement(By.linkText("Logout"));
		logout.click();
	}
	
	@Test(priority=1)
	public void updateUserProfile() throws InterruptedException
	{
		//send parameters to the function and login as a student
		manualLogin(Constant.STUDENTEMAIL,Constant.STUDENTPASSWORD);
		Thread.sleep(3000);
		
        //hit the my profile link on student's dashboard.
        WebElement editProfile = driver.findElement(By.partialLinkText("Profile"));
        editProfile.click();
        //Set all the values under student's profile
        //set student's mobile number inside the 3 boxes
        WebElement phone1 = driver.findElement(By.id("number1"));
        phone1.clear();
        WebElement phone2 = driver.findElement(By.id("number2"));
        phone2.clear();
        WebElement phone3 = driver.findElement(By.id("number3"));
        phone3.clear();
        
        //set student's mobile number
        phone1.sendKeys("8886315665");
        //select mobile carrier name which will be used to send notificatin to student
        Select carrier = new Select(driver.findElement(By.id("carrier")));
        carrier.selectByVisibleText("General Communications Inc. (number@mobile.gci.net )");
        
        //choose option for text message reminder
        WebElement receiveSMS =driver.findElement(By.xpath("//input[@name='status' and @value='0']"));
        receiveSMS.click();
        
        //hit submit button and save profile detail for the student.
        WebElement save = driver.findElement(By.className("btn-primary"));
        save.click();
        
        Thread.sleep(18000);
        boolean actualResult=false;
        //Get the success message
		String successMessage =driver.findElement(By.className("alert-success")).getText();
		
		//if there is success message then test case passed
		if(successMessage !="") {
			actualResult = true;
		}
		//If actual result is equal to true then test case passed
		Assert.assertEquals(true,actualResult);
		
		//Logout from student so that to proceed to login to teacher and admin
		WebElement logout = driver.findElement(By.linkText("Logout"));
		logout.click();
	}
}