package testClasses;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import baseClasses.BaseClass1;
import listenerClasses.ListenerClass1;
import pomClasses.HomePage;
import pomClasses.LoginPage;

@Listeners(ListenerClass1.class)
public class VerifyUserCanLogin {
	
static WebDriver driver;
	
	LoginPage lp;
	HomePage hp;
	ExtentHtmlReporter htmlReporter;
	ExtentReports reports;
	ExtentTest extentTest;
	
	@BeforeClass
	@Parameters("browser")
	
	public void beforeClass(String browser) throws IOException
	{
		htmlReporter=BaseClass1.getHtmlReporter();
		reports=BaseClass1.getExtentReports();
		extentTest=BaseClass1.getExtentTest("VerifyUserCanLogin");
		
		driver=BaseClass1.getdriver(browser);
	}
	
	@BeforeMethod
	public void beforeMethod() 
	{
		
		lp=new LoginPage(driver);
		hp=new HomePage(driver);
	}
	
	@Test
	public void verifyUserCanLogIn() throws InterruptedException, IOException
	{
		lp.enterEmailId();
		lp.enterPassword();
		lp.clickSubmit();
		
		 
		String profilename=hp.getProfileName();
		
		Assert.assertNotEquals(profilename,"Pranali","Profile name is not matching");
	}
	
		
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException
	{
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			extentTest.log(Status.PASS,"Test: "+result.getName());
		}else if(result.getStatus()==ITestResult.FAILURE)
		{
			extentTest.log(Status.FAIL,"Test: "+result.getName());
			String path=lp.getScreenshot(driver,result.getName());
			extentTest.log(Status.FAIL,"Test: "+result.getName(),MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			extentTest.log(Status.SKIP,"Test: "+result.getName());
		}
			
		
	}
	
	@AfterClass
	public void afterClass()
	{
		reports.flush();
	}
	

}
