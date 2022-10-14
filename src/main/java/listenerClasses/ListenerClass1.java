package listenerClasses;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClasses.BaseClass1;

public class ListenerClass1 implements ITestListener{
	
	ExtentTest extentTest;

	 public void onTestStart(ITestResult result) {
		 extentTest=BaseClass1.getAlreadyExistTest();
		 extentTest.log(Status.INFO,"Test started: "+result.getName());
		    System.out.println("Test is started "+result.getName()+"by listener");
		    Reporter.log("Test started");
		  }
	 public void onTestSuccess(ITestResult result) {
		 extentTest.log(Status.PASS,"Test passed: "+result.getName());
		    System.out.println("Test is passed "+result.getName()+"by listener");
		    Reporter.log("Test passed");
	  }
	 public void onTestFailure(ITestResult result) {
		 extentTest.log(Status.FAIL,"Test failed: "+result.getName());
		    System.out.println("Test is failed "+result.getName()+"by listener");
		    Reporter.log("Test failed");
		  }
	 public void onTestSkipped(ITestResult result) {
		 extentTest.log(Status.SKIP,"Test skipped: "+result.getName());
		    System.out.println("Test is skipped "+result.getName()+"by listener");
		    Reporter.log("Test skipped");
		  }

}
