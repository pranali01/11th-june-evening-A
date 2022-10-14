package testClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
public class verifyUserCanGetLowestPriceProduct {

static WebDriver driver;
static ExtentHtmlReporter htmlReporter;
static ExtentReports reports;
static ExtentTest extentTest;
	
	LoginPage lp;
	HomePage hp;
	
	@BeforeClass
	@Parameters("browser")
	public void beforeClass(String browser) throws IOException {
		
		htmlReporter=BaseClass1.getHtmlReporter();
		reports=BaseClass1.getExtentReports();
		extentTest=BaseClass1.getExtentTest("verifyUserCanGetLowestPriceProduct");
		
		driver=BaseClass1.getdriver(browser);
		
	}
	
	@BeforeMethod
	public void beforeMethod() {
		
		lp=new LoginPage(driver);
		hp=new HomePage(driver);
		
	}
	
	/*DataProvider(name="testdata")
	 public String[] getData() {
		
		String[] pName= {"realme","apple"};
		return pName;
		
	}*/

	@Test(priority=1)
	public void verifyUserCanSearchProduct() {
		
		hp.searchProduct();
		
		hp=new HomePage(driver);
		
		String actualText=hp.getValidText();
		
		Assert.assertTrue(actualText.contains("Showing 1 –"));	
		
	}
	
	@Test(priority=2)
	public void verifyUserCanGetLowestProductOnEachPage()  {
		Map<Integer,String> lowPriceMapExpected=new HashMap<>();
		lowPriceMapExpected.put(1,"10000");
		lowPriceMapExpected.put(2,"10000");
		lowPriceMapExpected.put(3,"10000");
		lowPriceMapExpected.put(4,"10000");
		lowPriceMapExpected.put(5,"10000");
		
		//List<String> lowestPriceFrom5Pages=new ArrayList<>();
		
		Map<Integer,String> lowPriceMapActual=new HashMap<>();
		for(int i=1;i<=5;i++)
		{
			if(i!=1) {
				hp.switchToPage(i);
			}
			
			//lowestPriceFrom5Pages.add(hp.getLowestPriceOfProduct());
			lowPriceMapActual.put(i, hp.getLowestPriceOfProduct());
			
		}
		
		Assert.assertNotEquals(lowPriceMapActual, lowPriceMapExpected);
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			extentTest.log(Status.PASS,"Test: "+result.getName());
		}else if(result.getStatus()==ITestResult.FAILURE)
		{
			extentTest.log(Status.FAIL,"Test: "+result.getName());
			String path=hp.getScreenshot(driver,result.getName());
			extentTest.log(Status.FAIL,"Test: "+result.getName(),MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			extentTest.log(Status.SKIP,"Test: "+result.getName());
		}
			
	}
	
	@AfterClass
	public void afterClass() {
		reports.flush();
		
	}
	

}
