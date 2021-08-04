package com.test.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase extends AbstractTestNGCucumberTests {

    public static WebDriver driver;
    static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    static ThreadLocal<AppiumDriver> mobileDriverThreadLocal = new ThreadLocal<>();
    static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    protected static Properties properties;
    protected static String screenShotPath;
//    protected static String scenarioName;
    public static ThreadLocal<Logger> loggerThreadLocal = new ThreadLocal<>();
    public static DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    public static AppiumDriverLocalService service;
    public static AppiumServiceBuilder serviceBuilder ;

    public TestBase(){
        try{
            properties = new Properties();
            FileInputStream fis = new FileInputStream("./src/main/java/com/test/properties/config.properties");
            properties.load(fis);
        }
        catch(FileNotFoundException e){
            e.getMessage();
            System.out.println("Properties File not found");
        }
        catch(IOException e){
            e.getMessage();
            System.out.println("Properties file is not loaded");
        }
    }

     public static void initialization(){
        String browser = properties.getProperty("browser");
        if(properties.getProperty("type").equalsIgnoreCase("Web")){
            if(browser.equalsIgnoreCase("chrome")){
                System.setProperty("webdriver.chrome.driver","./Drivers/chromedriver");
//            driver = new ChromeDriver();
                driverThreadLocal.set(new ChromeDriver());
            }
            else if(browser.equalsIgnoreCase("firefox")){
                System.setProperty("webdriver.gecko.driver","./Drivers/geckodriver");
//            driver = new FirefoxDriver();
                driverThreadLocal.set(new FirefoxDriver());
            }
            wait.set(new WebDriverWait(getDriver(),10));
            getDriver().manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT,TimeUnit.SECONDS);
            getDriver().manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_TIMEOUT,TimeUnit.SECONDS);
            getDriver().manage().deleteAllCookies();
            getDriver().manage().window().maximize();
        }
        else if(properties.getProperty("type").equalsIgnoreCase("Mobile")){
            buildCapabilities();
            serviceBuilder = new AppiumServiceBuilder();
            serviceBuilder.withIPAddress("127.0.0.1");
            serviceBuilder.usingPort(4723);
            serviceBuilder.withCapabilities(desiredCapabilities);
            serviceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
            serviceBuilder.withAppiumJS(new File("/Users/503000218/node_modules/appium/build/lib/main.js"));
            service = AppiumDriverLocalService.buildService(serviceBuilder);
            service.start();
            mobileDriverThreadLocal.set(new AppiumDriver(service.getUrl(),desiredCapabilities));
            wait.set(new WebDriverWait(getMobileDriver(),10));
        }
        else{
            System.out.println("Please provide valid platform type-----");
        }


    }

    public static String getScreenShot(WebDriver driver, String screenShotName) throws IOException{
        String dateName =  new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceScreenShot = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destinationScreenShot = System.getProperty("user.dir")+"/test-output/screeshot/"+screenShotName+dateName+".png";
        File destinationFile  = new File(destinationScreenShot);
        FileUtils.copyFile(sourceScreenShot,destinationFile);
        return destinationScreenShot;
    }

    public static synchronized WebDriver getDriver(){
            return driverThreadLocal.get();
    }

    public static synchronized AppiumDriver getMobileDriver(){
        return mobileDriverThreadLocal.get();
    }

    public static synchronized  WebDriverWait getWait(){ return wait.get(); }

    public static synchronized  Logger getLogger(){
        return loggerThreadLocal.get();
    }

    public static  void buildCapabilities(){
        desiredCapabilities.setCapability("app",System.getProperty("user.dir")+properties.getProperty("app"));
        desiredCapabilities.setCapability("platformName",properties.getProperty("platform"));
        desiredCapabilities.setCapability("platformVersion",properties.getProperty("platformVersion"));
        desiredCapabilities.setCapability("deviceName",properties.getProperty("deviceName"));
        desiredCapabilities.setCapability("automationName",properties.getProperty("automationName"));
        desiredCapabilities.setCapability("appPackage",properties.getProperty("appPackage"));
        desiredCapabilities.setCapability("appActivity",properties.getProperty("appActivity"));
        desiredCapabilities.setCapability("fullReset",true);
        desiredCapabilities.setCapability("appiumVersion",properties.getProperty("appiumVersion"));

    }


}
