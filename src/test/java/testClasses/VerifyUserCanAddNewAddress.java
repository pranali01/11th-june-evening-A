package testClasses;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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
import pomClasses.ProfilePage;

@Listeners(ListenerClass1.class)
public class VerifyUserCanAddNewAddress {

static WebDriver driver;
static ExtentHtmlReporter htmlReporter;
static ExtentReports reports;
static ExtentTest extentTest;
	
	LoginPage lp;
	HomePage hp;
	ProfilePage pp;
	
	String oldAddressCount;
	String newAddressCount;
	
	@BeforeClass
	@Parameters("browser")
	public void beforeClass(String browser) throws IOException {
		
		htmlReporter=BaseClass1.getHtmlReporter();
		reports=BaseClass1.getExtentReports();
		extentTest=BaseClass1.getExtentTest("VerifyUserCanAddNewAddress");
				
		driver=BaseClass1.getdriver(browser);
		
	}
	
	@BeforeMethod
	public void beforeMethod() {
		
		lp=new LoginPage(driver);
		hp=new HomePage(driver);
		pp=new ProfilePage(driver);
		
	}
	
	
	
	@Test(priority=1)
	public void verifyUserCanOpenProfilePage() {
	
		//hover on profile name
		hp.hoverProfileName();
	
		//click on profile
		hp.clickOnMyProfile();
		
		
		//check page is opened
		
		boolean onPage=pp.userOnProfilePage();
		Assert.assertTrue(onPage);
		
		oldAddressCount=String.valueOf(pp.getAddressCount());
	}
	
	@DataProvider(name="addressdata")
	public Object getdtata() {
		Object[][] k= {{"nilesh","7845122356","431008","mhasoba mandir","shanti van"},{"sweety","7845125657","431003","mhadev colony","khushnuma bahvan"}};
		return k;
	}
	
	@Test(priority=2,dataProvider="addressdata")
	public void addNewAddress(String Name,String Phone,String pincode,String locality,String fullAddress) {
		pp.clickOnManageAddress();
		List<String> addressDetails=Arrays.asList(Name,Phone,pincode,locality,fullAddress);
		pp.addNewAddress(addressDetails);
		extentTest.log(Status.INFO, "successfully added new address");
		 Reporter.log("Added new address");
		
		newAddressCount=String.valueOf(pp.getAddressCount());
		
		boolean isCountMatching=newAddressCount.equals(oldAddressCount);
		Assert.assertFalse(isCountMatching);
		}
	
	
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		
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
	public void afterClass() {
		reports.flush();
		BaseClass1.unloadDriver();
	}
	

}
