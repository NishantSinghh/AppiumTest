package com.capgemini.script;

import java.io.IOException;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.cg.utilities.utilities;
import com.adb.command.InstallApk;
public class NewTest {
	boolean blResult;
	AndroidDriver driver;
	String DeviceID = "4107c61b606a8f45";
	InstallApk ia = new InstallApk();
	utilities util = new utilities();
	
  @Test
  public void SearchBook() {
	  driver.findElement(By.id("android:id/button2")).click();
	  try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  driver.findElementById("com.home.omgandroid:id/main_edittext").sendKeys("harry");
	  driver.findElement(By.id("com.home.omgandroid:id/main_Button")).click();
	  WebDriverWait ww = new WebDriverWait(driver,60);
	  ww.until(ExpectedConditions.invisibilityOfElementLocated(By.id("android:id/progress")));
	  driver.hideKeyboard();
	  if((driver.findElementsById("com.home.omgandroid:id/text_title")).size()!=0){
		  String strTitle = driver.findElementById("com.home.omgandroid:id/text_title").getText();
		  if(strTitle.contains("Harry")){
			  Assert.assertTrue(true);
		  }
		  else{
			  Assert.assertTrue(false);
		  }
	  }
	  
	  
	  
  }
  @BeforeTest
  public void beforeTest() {
	  
	  blResult = ia.installMain();
	  String DeviceName = util.getCellValue("Test1", "DeviceId");
	  String PlatformName= util.getCellValue("Test1", "Mobile_OS");
	  String PackageName = util.getCellValue("Test1", "PackageName");
	  String ActivityName = util.getCellValue("Test1", "ActivityName");
	  System.out.println(DeviceName + " " + PlatformName + " " + PackageName + " " + ActivityName);
	  if(blResult == true){
		  try {
			util.StartAppium();
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("platformName", PlatformName);
			cap.setCapability("deviceName",DeviceName);	
			cap.setCapability("appPackage",PackageName);
			cap.setCapability("appActivity",ActivityName);
			driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), cap);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  else{
		  System.exit(0);
	  }
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
	  util.StopAppium();
  }

}
