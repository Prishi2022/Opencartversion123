package testBase;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

//this class Test Base  contains only, reuseable methods
public class BaseClass {
	
public WebDriver driver;
	public Logger logger;
	public ResourceBundle rb; //to read config.properties
	
	@BeforeClass(groups= {"master","sanity","regression"})
	@Parameters({"browser"})    //In the testng.xml file, whatever parameter we have given same we have to specify.
	//now the setup method is receiving parameter
	public void setup(String br) //here defining the variable called br, so that value of the browser will be stored in the br variable. based on this value launch the browser
	{

		//Load config.properties
		rb= ResourceBundle.getBundle("config");  //rb is representing the properties file.
		
		//logging
/* We have predefined class called LogManager, static method called getLogger 
    Define logger variable 
    pass in the bracket that is name of the class specify but should not hardcore, because we will multiple classes, 
    so dynimaically it will take the class name, which are the class we run at the run time, it will take class name so that same class name should appear in the log file
    ex: If we have 100 testcases, for all 100 testcases we will maintailn in the single logs, in the log file how will be recognize, where it got failed, 
    testcase  name or class name, testcase id, something should be there in the log file
    That is why we Pass "this.getClass()"             */
	logger=LogManager.getLogger(this.getClass()); //pass in the bracket that is name of the class specify but should not hardcore,
		
		
		//Driver
	//	WebDriverManager.chromedriver().setup();
		//driver=new ChromeDriver();
	
	if(br.equals("chrome"))
	{
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		logger.info("Launched Chrome Browser");
	}
	else if(br.equals("edge"))
	{
		WebDriverManager.edgedriver().setup();
		driver=new EdgeDriver();
		logger.info("Launched Edge Browser");
	
	}
	else if(br.equals("firefox"))
	{	
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		logger.info("Launched Firefox Browser");
	
	}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	/* creating method to generate a random data for the email id, RandomStringUtils is a pre defined class, in the
	   In the RandomStringUtils class there is a static method called randomAlphabetic, this method will take number as parameter.
	   This method will generate a string which contains a 5 random characters, and those 5 characters can be between A to Z,so every time it will take random characters.
	   Returning the generated string. When we call this method this will return random string.
	   If want to to call number and alphabet then concatenate both the methods */
		public String randomestring() 
		{
			String generatedString = RandomStringUtils.randomAlphabetic(8);
			return (generatedString); 
		}
		
		//For generating random number
		public int randomeNumber() 
		{
			String generatedString2 = RandomStringUtils.randomNumeric(8);
			return (Integer.parseInt(generatedString2)); 
		}
	//Captures screenshot here,  tname is a test method name, screenshot will get saved with the test method name
	// ex: For test case  TC_001_AccountRegistration, test case method name is test_account_registration()
	// captureScreen is method name
		// we will call this method from the test case, whenever the failure happen
		public void captureScreen(WebDriver driver, String tname) throws IOException
		{
			TakesScreenshot ts = (TakesScreenshot) driver; // convert TakesScreenshot to TakesScreenshot object
			File source = ts.getScreenshotAs(OutputType.FILE); //get the screenshot in the source
			File target = new File(System.getProperty("user.dir") + "\\screenshots\\" + tname + ".png"); // copy into the target
			FileUtils.copyFile(source, target);
		}
		
		@AfterClass(groups= {"master","sanity","regression"})
	public void tearDown()
	{
	driver.quit();      
	}
}
 
